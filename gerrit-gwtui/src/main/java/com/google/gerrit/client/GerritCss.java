// Copyright (C) 2009 The Android Open Source Project
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

package com.google.gerrit.client;

import com.google.gwt.resources.client.CssResource;

public interface GerritCss extends CssResource {
  String greenCheckClass();
  String accountContactOnFile();
  String accountContactPrivacyDetails();
  String accountDashboard();
  String accountInfoBlock();
  String accountName();
  String accountUsername();
  String accountPassword();
  String activeRow();
  String fileCommentBorder();
  String addMemberTextBox();
  String addReviewer();
  String removeReviewer();
  String removeReviewerCell();
  String addSshKeyPanel();
  String addWatchPanel();
  String approvalCategoryList();
  String approvalTable();
  String approvalhint();
  String approvalrole();
  String approvalscore();
  String notVotable();
  String blockHeader();
  String bottomheader();
  String cAPPROVAL();
  String cID();
  String cLastUpdate();
  String cSUBJECT();
  String cOWNER();
  String changeComments();
  String changeInfoBlock();
  String changeInfoTopicPanel();
  String changeScreen();
  String changeScreenDescription();
  String changeScreenStarIcon();
  String changeTable();
  String changeTablePrevNextLinks();
  String changeTypeCell();
  String changeid();
  String closedstate();
  String cellsNextToFileComment();
  String commentedActionDialog();
  String commentedActionMessage();
  String commentCell();
  String commentEditorPanel();
  String commentHolder();
  String commentHolderLeftmost();
  String commentPanel();
  String commentPanelBorder();
  String commentPanelAuthorCell();
  String commentPanelButtons();
  String commentPanelContent();
  String commentPanelDateCell();
  String commentPanelHeader();
  String commentPanelLast();
  String commentPanelMessage();
  String commentPanelMenuBar();
  String commentPanelSummary();
  String commentPanelSummaryCell();
  String complexHeader();
  String content();
  String contributorAgreementAlreadySubmitted();
  String contributorAgreementButton();
  String contributorAgreementLegal();
  String contributorAgreementShortDescription();
  String coverMessage();
  String createGroupLink();
  String createProjectPanel();
  String dataCell();
  String dataHeader();
  String diffLinkCell();
  String diffText();
  String diffTextCONTEXT();
  String diffTextDELETE();
  String diffTextFileHeader();
  String diffTextHunkHeader();
  String diffTextINSERT();
  String diffTextNoLF();
  String downloadLink();
  String downloadLink_Active();
  String downloadLinkListCell();
  String downloadLinkCopyLabel();
  String downloadLinkHeader();
  String downloadLinkHeaderGap();
  String downloadLinkList();
  String drafts();
  String emptySection();
  String errorDialog();
  String errorDialogGlass();
  String errorDialogTitle();
  String errorDialogButtons();
  String errorDialogErrorType();
  String errorDialogText();
  String fileColumnHeader();
  String fileLine();
  String fileLineCONTEXT();
  String fileLineDELETE();
  String fileLineINSERT();
  String fileLineMode();
  String fileLineNone();
  String filePathCell();
  String gerritTopMenu();
  String gerritBody();
  String groupDescriptionPanel();
  String groupExternalNameFilterTextBox();
  String groupIncludesTable();
  String groupMembersTable();
  String groupName();
  String groupNamePanel();
  String groupNameTextBox();
  String groupOptionsPanel();
  String groupOwnerPanel();
  String groupOwnerTextBox();
  String groupTypePanel();
  String groupTypeSelectListBox();
  String groupUUIDPanel();
  String header();
  String hyperlink();
  String iconCell();
  String iconCellOfFileCommentRow();
  String iconHeader();
  String identityUntrustedExternalId();
  String infoBlock();
  String infoTable();
  String inputFieldTypeHint();
  String keyhelp();
  String leftMostCell();
  String lineHeader();
  String lineNumber();
  String linkMenuBar();
  String linkMenuItemNotLast();
  String menuBarUserName();
  String menuItem();
  String menuScreenMenuBar();
  String missingApproval();
  String missingApprovalList();
  String monospace();
  String needsReview();
  String negscore();
  String noLineLineNumber();
  String noborder();
  String outdated();
  String parentsTable();
  String patchBrowserPopup();
  String patchBrowserPopupBody();
  String patchComments();
  String patchContentTable();
  String patchHistoryTable();
  String patchHistoryTablePatchSetHeader();
  String patchNoDifference();
  String patchScreenDisplayControls();
  String reviewedPanelBottom();
  String patchSetActions();
  String patchSetInfoBlock();
  String patchSetLink();
  String patchSetRevision();
  String patchSetUserIdentity();
  String patchSizeCell();
  String pluginsTable();
  String posscore();
  String projectAdminApprovalCategoryRangeLine();
  String projectAdminApprovalCategoryValue();
  String publishCommentsScreen();
  String registerScreenExplain();
  String registerScreenNextLinks();
  String registerScreenSection();
  String rightmost();
  String rightBorder();
  String rpcStatus();
  String rpcStatusLoading();
  String rpcStatusPanel();
  String screen();
  String screenHeader();
  String screenNoHeader();
  String searchPanel();
  String sectionHeader();
  String sideBySideScreenLinkTable();
  String sideBySideScreenSideBySideTable();
  String unifiedTable();
  String unifiedTableHeader();
  String singleLine();
  String skipLine();
  String smallHeading();
  String sourceFilePath();
  String specialBranchDataCell();
  String specialBranchIconCell();
  String sshHostKeyPanel();
  String sshHostKeyPanelFingerprintData();
  String sshHostKeyPanelHeading();
  String sshHostKeyPanelKnownHostEntry();
  String sshKeyPanelEncodedKey();
  String sshKeyPanelInvalid();
  String topmenu();
  String topmenuMenuLeft();
  String topmenuMenuRight();
  String topmenuTDglue();
  String topmenuTDmenu();
  String topmost();
  String topMostCell();
  String useridentity();
  String usernameField();
  String version();
  String watchedProjectFilter();
  String selectPatchSetOldVersion();
  String patchCellReverseDiff();
}
