/*
 * Copyright (c) 2018-2019 VMware, Inc. All Rights Reserved.
 * This software is released under MIT license.
 * The full license information can be found in LICENSE in the root directory of this project.
 */

package org.sonar.java.checks;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Rule;
import org.sonar.java.checks.utils.LocaleUtil;
import org.sonar.java.checks.methods.AbstractMethodDetection;
import org.sonar.java.matcher.MethodMatcher;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.*;
import org.sonar.java.checks.utils.PositionMethodMatcher;
import java.util.List;

import static org.sonar.plugins.java.api.tree.Tree.Kind.IDENTIFIER;
import static org.sonar.plugins.java.api.tree.Tree.Kind.MEMBER_SELECT;

@Rule(key = "V1018")
public class StringLowerUpperCaseCheck extends AbstractMethodDetection {
  private static final String LOCALE = "java.util.Locale";
  private static final String STRING = "java.lang.String";

  @Override
  protected List<MethodMatcher> getMethodInvocationMatchers() {
    return ImmutableList.<MethodMatcher>builder()
        .add(PositionMethodMatcher.create().typeDefinition(STRING).name("toLowerCase").withoutParameter())
        //.add(PositionMethodMatcher.create().position(0).typeDefinition(STRING).name("toLowerCase").addParameter(LOCALE))
        .add(PositionMethodMatcher.create().typeDefinition(STRING).name("toUpperCase").withoutParameter())
        //.add(PositionMethodMatcher.create().position(0).typeDefinition(STRING).name("toUpperCase").addParameter(LOCALE))
        .build();
  }

  @Override
  protected void onMethodInvocationFound(MethodInvocationTree mit) {
    PositionMethodMatcher matcher = (PositionMethodMatcher)getMatchedMatcher(mit);
    if (matcher.getPosition() == -1) {
      reportIssue(mit, "Use String.toLowerCase() and String.toUpperCase() with an explicit, user-preferred locale argument.");
      return;
    }

//    ExpressionTree arg = mit.arguments().get(matcher.getPosition());
//    String localeName = LocaleUtil.getNodeValueStaticFieldName(arg, "java.util.Locale");
//    if (localeName != null) {
//      reportIssue(arg, "This constant locale could prevent software from running correctly with other locales.");
//    }
  }
}
