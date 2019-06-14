/*
 * Copyright (c) 2018-2019 VMware, Inc. All Rights Reserved.
 * This software is released under MIT license.
 * The full license information can be found in LICENSE in the root directory of this project.
 */

package org.sonar.java.checks;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Rule;
import org.sonar.java.matcher.MethodMatcher;
import org.sonar.java.matcher.TypeCriteria;
import org.sonar.plugins.java.api.tree.*;

import java.util.List;

@Rule(key = "V1003")
public class StringConstructorCheck extends AbstractMethodCharsetDetection {
  private static final String STRING = "java.lang.String";
  private static final String INIT = "<init>";
  private static final String CHARSET = "java.nio.charset.Charset";
  private static final String BYTE_ARRAY = "byte[]";
  private static final String INT = "int";

  @Override
  protected List<MethodMatcher> getMethodInvocationMatchers() {
    TypeCriteria typeString = TypeCriteria.is(STRING);

    return ImmutableList.<MethodMatcher>builder()
        .add(MethodMatcher.create().typeDefinition(typeString).name(INIT).addParameter(BYTE_ARRAY))
        .add(MethodMatcher.create().typeDefinition(typeString).name(INIT).addParameter(BYTE_ARRAY).addParameter(CHARSET))
        .add(MethodMatcher.create().typeDefinition(typeString).name(INIT).addParameter(BYTE_ARRAY).addParameter(STRING))
        .add(MethodMatcher.create().typeDefinition(typeString).name(INIT).addParameter(BYTE_ARRAY).addParameter(INT).addParameter(INT))
        .add(MethodMatcher.create().typeDefinition(typeString).name(INIT).addParameter(BYTE_ARRAY).addParameter(INT).addParameter(INT).addParameter(CHARSET))
        .add(MethodMatcher.create().typeDefinition(typeString).name(INIT).addParameter(BYTE_ARRAY).addParameter(INT).addParameter(INT).addParameter(STRING))
        .add(MethodMatcher.create().typeDefinition(typeString).name(INIT).addParameter(BYTE_ARRAY).addParameter(INT))
        .add(MethodMatcher.create().typeDefinition(typeString).name(INIT).addParameter(BYTE_ARRAY).addParameter(INT).addParameter(INT).addParameter(INT))
        .build();
  }

  @Override
  protected void onConstructorFound(NewClassTree newClassTree) {
    Arguments args = newClassTree.arguments();
    if (args.size() == 1 || (args.size() == 3 && args.get(2).symbolType().is(INT))) {
      reportIssue(newClassTree, "Use non-deprecated String constructors with an explicit, valid charset/encoding parameter.");
      return;
    }
    if ((args.size() == 2 && args.get(1).symbolType().is(INT)) || (args.size() == 4 && args.get(3).symbolType().is(INT))) {
      reportIssue(newClassTree, "This constructor has been deprecated and it also can not accept a charset parameter for product internationalization.");
      return;
    }

    ExpressionTree firstArg = args.get(0);
    if (args.size() == 2 && (isInvalidLiteralStringCharset(args.get(1)) || isInvalidConstVariableCharset(args.get(1)))) {
      reportIssue(args.get(1), "Use an explicit, valid encoding/charset argument with String constructor.");
      return;
    }
    if (args.size() == 4 && (isInvalidLiteralStringCharset(args.get(3)) || isInvalidConstVariableCharset(args.get(3)))) {
      reportIssue(args.get(3), "Use an explicit, valid encoding/charset argument with String constructor.");
      return;
    }

    if (args.size() == 2 && isInvalidStandardCharsetsInstanceCharset(args.get(1))) {
      reportIssue(args.get(1), "Use an explicit, valid encoding/charset argument with String constructor.");
      return;
    }
    if (args.size() == 4 && isInvalidStandardCharsetsInstanceCharset(args.get(3))) {
      reportIssue(args.get(3), "Use an explicit, valid encoding/charset argument with String constructor.");
      return;
    }
  }
}
