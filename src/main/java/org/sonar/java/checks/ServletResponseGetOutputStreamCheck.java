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

@Rule(key = "V1007")
public class ServletResponseGetOutputStreamCheck extends AbstractMethodCharsetDetection {
  private static final String STRING = "java.lang.String";
  private static final String CHARSET = "java.nio.charset.Charset";
  private static final String GET_OUTPUT_STREAM = "getOutputStream";
  private static final String FOR_NAME = "forName";
  private static final String SERVLET_RESPONSE = "javax.servlet.ServletResponse";

  @Override
  protected List<MethodMatcher> getMethodInvocationMatchers() {
    TypeCriteria servletResponseType = TypeCriteria.subtypeOf(SERVLET_RESPONSE);

    return ImmutableList.<MethodMatcher>builder()
        .add(MethodMatcher.create().typeDefinition(servletResponseType).name(GET_OUTPUT_STREAM).withoutParameter())
        .build();
  }

  @Override
  protected void onMethodInvocationFound(MethodInvocationTree mit) {
      reportIssue(mit, "Use the getWriter instead of getOutputStream to exchange text (not binary) data.");
  }
}
