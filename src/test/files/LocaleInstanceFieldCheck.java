/*
 * Copyright (c) 2018-2019 VMware, Inc. All Rights Reserved.
 * This software is released under MIT license.
 * The full license information can be found in LICENSE in the root directory of this project.
 */

import java.util.Locale;
import static java.util.Locale.KOREA;

public class LocaleInstanceFieldCheck {
  void methodA() {
    String text = "hello";

    Locale userLocale = Locale.FRENCH;  // Noncompliant {{Use explicit, user-preferred Locale settings to insure an ideal international user experience.}}
    Locale userLocale2 = Locale.ENGLISH;

    text.toLowerCase(Locale.CHINA);  // Noncompliant {{Use explicit, user-preferred Locale settings to insure an ideal international user experience.}}
    text.toLowerCase(userLocale);

    text.toLowerCase(KOREA);  // Noncompliant {{Use explicit, user-preferred Locale settings to insure an ideal international user experience.}}
  }
}
