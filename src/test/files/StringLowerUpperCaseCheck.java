/*
 * Copyright (c) 2018-2019 VMware, Inc. All Rights Reserved.
 * This software is released under MIT license.
 * The full license information can be found in LICENSE in the root directory of this project.
 */

import java.util.Locale;

public class StringLowerUpperCaseCheck {
  void methodA(Locale userLocale) {
    String text = "hello";

    text.toLowerCase();  // Noncompliant {{Use String.toLowerCase() and String.toUpperCase() with an explicit, user-preferred locale argument.}}
    text.toLowerCase(Locale.CHINA);
    text.toLowerCase(userLocale);

    text.toUpperCase();  // Noncompliant {{Use String.toLowerCase() and String.toUpperCase() with an explicit, user-preferred locale argument.}}
    text.toUpperCase(Locale.CHINA);
    text.toUpperCase(userLocale);
  }
}
