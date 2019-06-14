/*
 * Copyright (c) 2018-2019 VMware, Inc. All Rights Reserved.
 * This software is released under MIT license.
 * The full license information can be found in LICENSE in the root directory of this project.
 */

import java.util.Locale;
import java.text.Collator;

public class LocaleGetDefaultCheck {
  void methodA() {
    Collator collator = Collator.getInstance(Locale.getDefault());  // Noncompliant {{Locale.getDefault is error prone. Use explicit, user-preferred locales instead.}}
  }
}
