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
import org.sonar.java.matcher.NameCriteria;
import org.sonar.java.matcher.TypeCriteria;
import org.sonar.java.model.LiteralUtils;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.Type;

import java.util.List;

import org.sonar.plugins.java.api.tree.*;

@Rule(key = "V1002")
public class StringGetBytesCheck extends AbstractMethodCharsetDetection {

  @Override
  protected List<MethodMatcher> getMethodInvocationMatchers() {
    TypeCriteria typeString = TypeCriteria.is("java.lang.String");
    NameCriteria methodGetBytes = NameCriteria.is("getBytes");

    return ImmutableList.<MethodMatcher>builder()
        .add(MethodMatcher.create().typeDefinition(typeString).name(methodGetBytes).withoutParameter())
        .add(MethodMatcher.create().typeDefinition(typeString).name(methodGetBytes).addParameter("java.nio.charset.Charset"))
        .add(MethodMatcher.create().typeDefinition(typeString).name(methodGetBytes).addParameter("int")
            .addParameter("int").addParameter("byte[]").addParameter("int"))
        .add(MethodMatcher.create().typeDefinition(typeString).name(methodGetBytes).addParameter("java.lang.String"))
        .build();
  }

  @Override
  protected void onMethodInvocationFound(MethodInvocationTree mit) {
    Arguments args = mit.arguments();
    if (args.isEmpty()) {
      reportIssue(mit, "Use an explicit, valid encoding/charset argument with getBytes.");
      return;
    }

    if (args.size() == 4) {
      reportIssue(mit, "This String.getBytes() is a deprecated method and it also can not accept an encoding argument.");
      return;
    }

    if (isInvalidCharset(args.get(0))) {
      reportIssue(args.get(0), "Encoding argument passed to String.getBytes() is not valid.");
      return;
    }
  }
}
