// Copyright (C) 2012 The Android Open Source Project
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

package com.google.gerrit.server.update;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.gerrit.server.git.ReceiveCommits;
import com.google.inject.BindingAnnotation;
import java.lang.annotation.Retention;

/**
 * Marker on the global {@link ListeningExecutorService} used by {@link ReceiveCommits} to create or
 * replace changes.
 */
@Retention(RUNTIME)
@BindingAnnotation
public @interface ChangeUpdateExecutor {}
