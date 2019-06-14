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

@Rule(key = "V1006")
public class OutputStreamWriterConstructorCheck extends AbstractMethodCharsetDetection {
  private static final String STRING = "java.lang.String";
  private static final String CHARSET = "java.nio.charset.Charset";
  private static final String OUTPUT_STREAM = "java.io.OutputStream";
  private static final String OUTPUT_STREAM_WRITER = "java.io.OutputStreamWriter";
  private static final String INIT = "<init>";

  @Override
  protected List<MethodMatcher> getMethodInvocationMatchers() {
    TypeCriteria outputStreamSubtype = TypeCriteria.subtypeOf(OUTPUT_STREAM);

    return ImmutableList.<MethodMatcher>builder()
        .add(MethodMatcher.create().typeDefinition(OUTPUT_STREAM_WRITER).name(INIT).addParameter(outputStreamSubtype))
        .add(MethodMatcher.create().typeDefinition(OUTPUT_STREAM_WRITER).name(INIT).addParameter(outputStreamSubtype).addParameter(CHARSET))
        .add(MethodMatcher.create().typeDefinition(OUTPUT_STREAM_WRITER).name(INIT).addParameter(outputStreamSubtype).addParameter(STRING))
        .build();
  }

  @Override
  protected void onConstructorFound(NewClassTree newClassTree) {
    Arguments args = newClassTree.arguments();
    if (args.size() == 1) {
      reportIssue(newClassTree, "Use an explicit, valid encoding/charset argument with OutputStreamWriter.");
      return;
    }

    if (args.size() == 2 && (isInvalidLiteralStringCharset(args.get(1)) || isInvalidConstVariableCharset(args.get(1)))) {
      reportIssue(args.get(1), "Use an explicit, valid encoding/charset argument with OutputStreamWriter.");
      return;
    }

    if (args.size() == 2 && isInvalidStandardCharsetsInstanceCharset(args.get(1))) {
      reportIssue(args.get(1), "Use an explicit, valid encoding/charset argument with OutputStreamWriter.");
      return;
    }
  }
}
