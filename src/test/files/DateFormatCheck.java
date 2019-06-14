/*
 * Copyright (c) 2018-2019 VMware, Inc. All Rights Reserved.
 * This software is released under MIT license.
 * The full license information can be found in LICENSE in the root directory of this project.
 */

import java.io.UnsupportedEncodingException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.lang.StringBuilder;
import java.text.DateFormat;
import java.util.Date;
import java.lang.StringBuffer;
import java.text.FieldPosition;
import java.text.DateFormat.Field;
import java.text.AttributedCharacterIterator;

public class DateFormatCheck {
  String methodA() {
    DateFormat formatter = DateFormat.getDateInstance();
    Date date = new Date();
    return formatter.format(date);  // Noncompliant {{Use the Unicode ICU DateTimePatternGenerator API instead as it is less error prone.}}
  }

  StringBuffer methodB() {
    DateFormat formatter = DateFormat.getDateInstance();
    Date date = new Date();
    return formatter.format(date, new StringBuffer(), new FieldPosition(Field.MONTH));  // Noncompliant {{Use the Unicode ICU DateTimePatternGenerator API instead as it is less error prone.}}
  }

  AttributedCharacterIterator methodC() {
    DateFormat formatter = DateFormat.getDateInstance();
    Date date = new Date();
    return formatter.formatToCharacterIterator(date);  // Noncompliant {{Use the Unicode ICU DateTimePatternGenerator API instead as it is less error prone.}}
  }
}
