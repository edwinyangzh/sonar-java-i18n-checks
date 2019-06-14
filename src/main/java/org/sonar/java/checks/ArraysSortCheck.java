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
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.java.matcher.TypeCriteria;
import org.sonar.plugins.java.api.tree.TypeTree;

import java.util.List;

@Rule(key = "V1017")
public class ArraysSortCheck extends AbstractMethodDetection {

  @Override
  protected List<MethodMatcher> getMethodInvocationMatchers() {
    return ImmutableList.<MethodMatcher>builder()
        .add(MethodMatcher.create().typeDefinition("java.util.Arrays").name("sort").addParameter("java.lang.Object[]"))
        .add(MethodMatcher.create().typeDefinition("java.util.Arrays").name("sort").addParameter("java.lang.Object[]").addParameter("int").addParameter("int"))
        .build();
  }

  @Override
  protected void onMethodInvocationFound(MethodInvocationTree mit) {
    Type firstArgType = mit.arguments().get(0).symbolType();
    if ((firstArgType instanceof Type.ArrayType) && ((Type.ArrayType)firstArgType).elementType().is("java.lang.String")) {
      reportIssue(mit, "Use a locale-specific instance of Collator to sort non-English text correctly.");
    }
  }
}