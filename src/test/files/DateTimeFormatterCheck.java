/*
 * Copyright (c) 2018-2019 VMware, Inc. All Rights Reserved.
 * This software is released under MIT license.
 * The full license information can be found in LICENSE in the root directory of this project.
 */

import java.io.UnsupportedEncodingException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.lang.StringBuilder;

public class DateTimeFormatterCheck {
  String methodA() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
    LocalDate date = LocalDate.now();
    return date.format(formatter);  // Noncompliant {{Use the Unicode ICU DateTimePatternGenerator API instead as it is less error prone.}}
  }

  String methodB() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
    LocalDate date = LocalDate.now();
    return formatter.format(date);  // Noncompliant {{Use the Unicode ICU DateTimePatternGenerator API instead as it is less error prone.}}
  }

  String methodC() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MM dd");
    LocalDate date = LocalDate.now();
    return formatter.formatTo(date, new StringBuilder());  // Noncompliant {{Use the Unicode ICU DateTimePatternGenerator API instead as it is less error prone.}}
  }
}
