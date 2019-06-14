/*
 * Copyright (c) 2018-2019 VMware, Inc. All Rights Reserved.
 * This software is released under MIT license.
 * The full license information can be found in LICENSE in the root directory of this project.
 */
package org.sonar.java;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.sonar.plugins.java.api.JavaCheck;
import org.sonar.java.checks.ArraysSortCheck;
import org.sonar.java.checks.CharsetInstanceCreationCheck;
import org.sonar.java.checks.CollatorCheck;
import org.sonar.java.checks.CollectionsSortCheck;
import org.sonar.java.checks.DateFormatCheck;
import org.sonar.java.checks.DateTimeFormatterCheck;
import org.sonar.java.checks.InputStreamReaderConstructorCheck;
import org.sonar.java.checks.LegacyEncodingsCheck;
import org.sonar.java.checks.LocaleGetDefaultCheck;
import org.sonar.java.checks.LocaleInstanceFieldCheck;
import org.sonar.java.checks.OutputStreamWriterConstructorCheck;
import org.sonar.java.checks.ServletResponseGetOutputStreamCheck;
import org.sonar.java.checks.SimpleDateFormatCheck;
import org.sonar.java.checks.StringCompareCheck;
import org.sonar.java.checks.StringConstructorCheck;
import org.sonar.java.checks.StringGetBytesCheck;
import org.sonar.java.checks.StringLowerUpperCaseCheck;

public final class RulesList {

  private RulesList() {
  }

  public static List<Class> getChecks() {
    return ImmutableList.<Class>builder().addAll(getJavaChecks()).addAll(getJavaTestChecks()).build();
  }

  public static List<Class<? extends JavaCheck>> getJavaChecks() {
    return ImmutableList.<Class<? extends JavaCheck>>builder()
            .add(ArraysSortCheck.class)
            .add(CharsetInstanceCreationCheck.class)
            .add(CollatorCheck.class)
            .add(CollectionsSortCheck.class)
            .add(DateFormatCheck.class)
            .add(DateTimeFormatterCheck.class)
            .add(InputStreamReaderConstructorCheck.class)
            .add(LegacyEncodingsCheck.class)
            .add(LocaleGetDefaultCheck.class)
            .add(LocaleInstanceFieldCheck.class)
            .add(OutputStreamWriterConstructorCheck.class)
            .add(ServletResponseGetOutputStreamCheck.class)
            .add(SimpleDateFormatCheck.class)
            .add(StringCompareCheck.class)
            .add(StringConstructorCheck.class)
            .add(StringGetBytesCheck.class)
            .add(StringLowerUpperCaseCheck.class)
      .build();
  }

  public static List<Class<? extends JavaCheck>> getJavaTestChecks() {
    return ImmutableList.<Class<? extends JavaCheck>>builder()
      .build();
  }
}
