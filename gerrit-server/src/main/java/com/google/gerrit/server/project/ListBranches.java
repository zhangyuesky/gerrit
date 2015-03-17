// Copyright (C) 2013 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.gerrit.server.project;

import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Sets;
import com.google.gerrit.extensions.common.ActionInfo;
import com.google.gerrit.extensions.common.WebLinkInfo;
import com.google.gerrit.extensions.registration.DynamicMap;
import com.google.gerrit.extensions.restapi.BadRequestException;
import com.google.gerrit.extensions.restapi.ResourceNotFoundException;
import com.google.gerrit.extensions.restapi.RestReadView;
import com.google.gerrit.extensions.restapi.RestView;
import com.google.gerrit.extensions.webui.UiAction;
import com.google.gerrit.reviewdb.client.RefNames;
import com.google.gerrit.server.WebLinks;
import com.google.gerrit.server.extensions.webui.UiActions;
import com.google.gerrit.server.git.GitRepositoryManager;
import com.google.inject.Inject;
import com.google.inject.util.Providers;

import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.kohsuke.args4j.Option;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import dk.brics.automaton.RegExp;
import dk.brics.automaton.RunAutomaton;

public class ListBranches implements RestReadView<ProjectResource> {
  private final GitRepositoryManager repoManager;
  private final DynamicMap<RestView<BranchResource>> branchViews;
  private final WebLinks webLinks;

  @Option(name = "--limit", aliases = {"-n"}, metaVar = "CNT", usage = "maximum number of branches to list")
  private int limit;

  @Option(name = "--start", aliases = {"-s"}, metaVar = "CNT", usage = "number of branches to skip")
  private int start;

  @Option(name = "--match", aliases = {"-m"}, metaVar = "MATCH", usage = "match branches substring")
  private String matchSubstring;

  @Option(name = "--regex", aliases = {"-r"}, metaVar = "REGEX", usage = "match branches regex")
  private String matchRegex;

  @Inject
  public ListBranches(GitRepositoryManager repoManager,
      DynamicMap<RestView<BranchResource>> branchViews,
      WebLinks webLinks) {
    this.repoManager = repoManager;
    this.branchViews = branchViews;
    this.webLinks = webLinks;
  }

  @Override
  public List<BranchInfo> apply(ProjectResource rsrc)
      throws ResourceNotFoundException, IOException, BadRequestException {
    List<BranchInfo> branchList;
    BranchInfo headBranch = null;
    BranchInfo configBranch = null;

    try (Repository db = repoManager.openRepository(rsrc.getNameKey())) {
      Collection<Ref> heads =
          db.getRefDatabase().getRefs(Constants.R_HEADS).values();
      List<Ref> refs = new ArrayList<>(heads.size() + 2);
      refs.addAll(heads);
      addRef(db, refs, Constants.HEAD);
      addRef(db, refs, RefNames.REFS_CONFIG);

      Set<String> targets = Sets.newHashSetWithExpectedSize(1);
      for (Ref ref : refs) {
        if (ref.isSymbolic()) {
          targets.add(ref.getTarget().getName());
        }
      }

      branchList = new ArrayList<>(refs.size());
      for (Ref ref : refs) {
        if (ref.isSymbolic()) {
          // A symbolic reference to another branch, instead of
          // showing the resolved value, show the name it references.
          //
          String target = ref.getTarget().getName();
          RefControl targetRefControl = rsrc.getControl().controlForRef(target);
          if (!targetRefControl.isVisible()) {
            continue;
          }
          if (target.startsWith(Constants.R_HEADS)) {
            target = target.substring(Constants.R_HEADS.length());
          }

          BranchInfo b = new BranchInfo(ref.getName(), target, false);

          if (Constants.HEAD.equals(ref.getName())) {
            headBranch = b;
          } else {
            b.setCanDelete(targetRefControl.canDelete());
            branchList.add(b);
          }
          continue;
        }

        RefControl refControl = rsrc.getControl().controlForRef(ref.getName());
        if (refControl.isVisible()) {
          if (RefNames.REFS_CONFIG.equals(ref.getName())) {
            configBranch = createBranchInfo(ref, refControl, targets);
          } else {
            branchList.add(createBranchInfo(ref, refControl, targets));
          }
        }
      }
    } catch (RepositoryNotFoundException noGitRepository) {
      throw new ResourceNotFoundException();
    }
    Collections.sort(branchList, new Comparator<BranchInfo>() {
      @Override
      public int compare(BranchInfo a, BranchInfo b) {
        return a.ref.compareTo(b.ref);
      }
    });
    if (configBranch != null) {
      branchList.add(0, configBranch);
    }
    if (headBranch != null) {
      branchList.add(0, headBranch);
    }

    FluentIterable<BranchInfo> branches = filterBranches(branchList);
    if (start > 0) {
      branches = branches.skip(start);
    }
    if (limit > 0) {
      branches = branches.limit(limit);
    }
    return branches.toList();
  }

  private static void addRef(Repository db, List<Ref> refs, String name)
      throws IOException {
    Ref ref = db.getRef(name);
    if (ref != null) {
      refs.add(ref);
    }
  }

  private FluentIterable<BranchInfo> filterBranches(List<BranchInfo> branchList)
      throws BadRequestException {
    FluentIterable<BranchInfo> branches = FluentIterable.from(branchList);
    if (!Strings.isNullOrEmpty(matchSubstring)) {
      branches = branches.filter(new SubstringPredicate(matchSubstring));
    } else if (!Strings.isNullOrEmpty(matchRegex)) {
      branches = branches.filter(new RegexPredicate(matchRegex));
    }
    return branches;
  }

  private static class SubstringPredicate implements Predicate<BranchInfo> {
    private final String substring;

    private SubstringPredicate(String substring) {
      this.substring = substring.toLowerCase(Locale.US);
    }

    @Override
    public boolean apply(BranchInfo in) {
      String ref = in.ref;
      if (ref.startsWith(Constants.R_HEADS)) {
        ref = ref.substring(Constants.R_HEADS.length());
      }
      ref = ref.toLowerCase(Locale.US);
      return ref.contains(substring);
    }
  }

  private static class RegexPredicate implements Predicate<BranchInfo> {
    private final RunAutomaton a;

    private RegexPredicate(String regex) throws BadRequestException {
      if (regex.startsWith("^")) {
        regex = regex.substring(1);
        if (regex.endsWith("$") && !regex.endsWith("\\$")) {
          regex = regex.substring(0, regex.length() - 1);
        }
      }
      try {
        a = new RunAutomaton(new RegExp(regex).toAutomaton());
      } catch (IllegalArgumentException e) {
        throw new BadRequestException(e.getMessage());
      }
    }

    @Override
    public boolean apply(BranchInfo in) {
      if (!in.ref.startsWith(Constants.R_HEADS)){
        return a.run(in.ref);
      } else {
        return a.run(in.ref.substring(Constants.R_HEADS.length()));
      }
    }
  }

  private BranchInfo createBranchInfo(Ref ref, RefControl refControl,
      Set<String> targets) {
    BranchInfo info = new BranchInfo(ref.getName(),
        ref.getObjectId() != null ? ref.getObjectId().name() : null,
        !targets.contains(ref.getName()) && refControl.canDelete());
    for (UiAction.Description d : UiActions.from(
        branchViews,
        new BranchResource(refControl.getProjectControl(), info),
        Providers.of(refControl.getCurrentUser()))) {
      if (info.actions == null) {
        info.actions = new TreeMap<>();
      }
      info.actions.put(d.getId(), new ActionInfo(d));
    }
    FluentIterable<WebLinkInfo> links =
        webLinks.getBranchLinks(
            refControl.getProjectControl().getProject().getName(), ref.getName());
    info.webLinks = links.isEmpty() ? null : links.toList();
    return info;
  }

  public static class BranchInfo {
    public String ref;
    public String revision;
    public Boolean canDelete;
    public Map<String, ActionInfo> actions;
    public List<WebLinkInfo> webLinks;

    public BranchInfo(String ref, String revision, boolean canDelete) {
      this.ref = ref;
      this.revision = revision;
      this.canDelete = canDelete;
    }

    void setCanDelete(boolean canDelete) {
      this.canDelete = canDelete ? true : null;
    }
  }
}
