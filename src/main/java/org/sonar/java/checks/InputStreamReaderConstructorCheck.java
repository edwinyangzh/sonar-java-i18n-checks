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

@Rule(key = "V1005")
public class InputStreamReaderConstructorCheck extends AbstractMethodCharsetDetection {
  private static final String STRING = "java.lang.String";
  private static final String CHARSET = "java.nio.charset.Charset";
  private static final String INPUT_STREAM = "java.io.InputStream";
  private static final String INPUT_STREAM_READER = "java.io.InputStreamReader";
  private static final String INIT = "<init>";

  @Override
  protected List<MethodMatcher> getMethodInvocationMatchers() {
    TypeCriteria inputStreamSubtype = TypeCriteria.subtypeOf(INPUT_STREAM);

    return ImmutableList.<MethodMatcher>builder()
        .add(MethodMatcher.create().typeDefinition(INPUT_STREAM_READER).name(INIT).addParameter(inputStreamSubtype))
        .add(MethodMatcher.create().typeDefinition(INPUT_STREAM_READER).name(INIT).addParameter(inputStreamSubtype).addParameter(CHARSET))
        .add(MethodMatcher.create().typeDefinition(INPUT_STREAM_READER).name(INIT).addParameter(inputStreamSubtype).addParameter(STRING))
        .build();
  }

  @Override
  protected void onConstructorFound(NewClassTree newClassTree) {
    Arguments args = newClassTree.arguments();
    if (args.size() == 1) {
      reportIssue(newClassTree, "Use an explicit, valid encoding/charset parameter with InputStreamReader.");
      return;
    }

    if (args.size() == 2 && (isInvalidLiteralStringCharset(args.get(1)) || isInvalidConstVariableCharset(args.get(1)))) {
      reportIssue(args.get(1), "Use an explicit, valid encoding/charset parameter with InputStreamReader.");
      return;
    }

    if (args.size() == 2 && isInvalidStandardCharsetsInstanceCharset(args.get(1))) {
      reportIssue(args.get(1), "Use an explicit, valid encoding/charset parameter with InputStreamReader.");
      return;
    }
  }
}
