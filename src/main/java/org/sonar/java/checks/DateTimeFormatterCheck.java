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
import org.sonar.java.matcher.TypeCriteria;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;

import java.util.List;

@Rule(key = "V1009")
public class DateTimeFormatterCheck extends AbstractMethodDetection {

  @Override
  protected List<MethodMatcher> getMethodInvocationMatchers() {
    final String DATE_TIME_FORMATTER = "java.time.format.DateTimeFormatter";
    TypeCriteria temporalAccessorSubtype = TypeCriteria.subtypeOf("java.time.temporal.TemporalAccessor");
    TypeCriteria appendableSubtype = TypeCriteria.subtypeOf("java.lang.Appendable");

    return ImmutableList.<MethodMatcher>builder()
        .add(buildFormatMethodMatcher("java.time.LocalDate"))
        .add(buildFormatMethodMatcher("java.time.LocalDateTime"))
        .add(buildFormatMethodMatcher("java.time.LocalTime"))
        .add(buildFormatMethodMatcher("java.time.MonthDay"))
        .add(buildFormatMethodMatcher("java.time.OffsetDateTime"))
        .add(buildFormatMethodMatcher("java.time.OffsetTime"))
        .add(buildFormatMethodMatcher("java.time.Year"))
        .add(buildFormatMethodMatcher("java.time.YearMonth"))
        .add(buildFormatMethodMatcher("java.time.ZonedDateTime"))
        .add(buildFormatMethodMatcher("java.time.chrono.ChronoLocalDate"))
        .add(buildFormatMethodMatcher("java.time.chrono.ChronoLocalDateTime"))
        .add(buildFormatMethodMatcher("java.time.chrono.ChronoZonedDateTime"))
        .add(MethodMatcher.create().typeDefinition(DATE_TIME_FORMATTER).name("format").addParameter(temporalAccessorSubtype))
        .add(MethodMatcher.create().typeDefinition(DATE_TIME_FORMATTER).name("formatTo").addParameter(temporalAccessorSubtype).addParameter(appendableSubtype))
        .build();
  }

  @Override
  protected void onMethodInvocationFound(MethodInvocationTree mit) {
      reportIssue(mit, "Use the Unicode ICU DateTimePatternGenerator API instead as it is less error prone.");
  }

  private static MethodMatcher buildFormatMethodMatcher(String type) {
    return MethodMatcher.create()
        .typeDefinition(type).name("format").addParameter("java.time.format.DateTimeFormatter");
  }
}