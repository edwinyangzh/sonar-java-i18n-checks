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
import org.sonar.java.model.ExpressionUtils;
import org.sonar.plugins.java.api.tree.*;

import java.util.List;

@Rule(key = "V1004")
public class CharsetInstanceCreationCheck extends AbstractMethodCharsetDetection {
  private static final String STRING = "java.lang.String";
  private static final String CHARSET = "java.nio.charset.Charset";
  private static final String DEFAULT_CHARSET = "defaultCharset";
  private static final String FOR_NAME = "forName";

  @Override
  protected List<MethodMatcher> getMethodInvocationMatchers() {
    TypeCriteria charsetType = TypeCriteria.is(CHARSET);

    return ImmutableList.<MethodMatcher>builder()
        .add(MethodMatcher.create().typeDefinition(charsetType).name(DEFAULT_CHARSET).withoutParameter())
        .add(MethodMatcher.create().typeDefinition(charsetType).name(FOR_NAME).addParameter(STRING))
        .build();
  }

  @Override
  protected void onMethodInvocationFound(MethodInvocationTree mit) {
    String methodName = ExpressionUtils.methodName(mit).name();
    if (DEFAULT_CHARSET.equals(methodName)) {
      reportIssue(mit, "Use the Charset.forName method instead with an explicit charset/encoding argument.");
      return;
    }

    if (FOR_NAME.equals(methodName)) {
      Arguments args = mit.arguments();
      if (args.size() == 1 && (isInvalidLiteralStringCharset(args.get(0)) || isInvalidConstVariableCharset(args.get(0)))) {
        reportIssue(args.get(0), "Use an explicit, valid encoding/charset parameter with Charset factory method.");
        return;
      }
    }
  }
}
