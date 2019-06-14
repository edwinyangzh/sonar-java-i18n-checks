/*
 * Copyright (c) 2018-2019 VMware, Inc. All Rights Reserved.
 * This software is released under MIT license.
 * The full license information can be found in LICENSE in the root directory of this project.
 */

package org.sonar.java.checks;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Rule;
import org.sonar.java.checks.methods.AbstractMethodDetection;
import org.sonar.java.matcher.MethodMatcher;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.java.matcher.TypeCriteria;

import java.util.List;

@Rule(key = "V1011")
public class SimpleDateFormatCheck extends AbstractMethodDetection {

  @Override
  protected List<MethodMatcher> getMethodInvocationMatchers() {
    return ImmutableList.<MethodMatcher>builder()
        .add(buildFormatMethodMatcher("format"))
        .add(buildFormatMethodMatcher("formatToCharacterIterator"))
        .build();
  }

  @Override
  protected void onMethodInvocationFound(MethodInvocationTree mit) {
    reportIssue(mit, "Use the Unicode ICU DateTimePatternGenerator instead as it is far less error-prone.");
  }

  private static MethodMatcher buildFormatMethodMatcher(String method) {
    return MethodMatcher.create()
        .callSite(TypeCriteria.is("java.text.SimpleDateFormat")).name(method).withAnyParameters();
  }
}