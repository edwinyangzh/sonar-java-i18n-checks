/*
 * Copyright (c) 2018-2019 VMware, Inc. All Rights Reserved.
 * This software is released under MIT license.
 * The full license information can be found in LICENSE in the root directory of this project.
 */

import java.io.UnsupportedEncodingException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.lang.StringBuilder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.lang.StringBuffer;
import java.text.FieldPosition;
import java.text.DateFormat.Field;
import java.text.AttributedCharacterIterator;

public class SimpleDateFormatCheck {
  String methodA() {
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    Date date = new Date();
    return formatter.format(date);  // Noncompliant {{Use the Unicode ICU DateTimePatternGenerator instead as it is far less error-prone.}}
  }

  StringBuffer methodB() {
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    Date date = new Date();
    return formatter.format(date, new StringBuffer(), new FieldPosition(Field.MONTH));  // Noncompliant {{Use the Unicode ICU DateTimePatternGenerator instead as it is far less error-prone.}}
  }

  AttributedCharacterIterator methodC() {
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
    Date date = new Date();
    return formatter.formatToCharacterIterator(date);  // Noncompliant {{Use the Unicode ICU DateTimePatternGenerator instead as it is far less error-prone.}}
  }
}
