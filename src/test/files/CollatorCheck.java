/*
 * Copyright (c) 2018-2019 VMware, Inc. All Rights Reserved.
 * This software is released under MIT license.
 * The full license information can be found in LICENSE in the root directory of this project.
 */

import java.util.Locale;
import java.text.Collator;

public class CollatorCheck {
  void methodA() {
    Collator collatorOk = Collator.getInstance(Locale.CHINA);
    Collator collatorWarning = Collator.getInstance();  // Noncompliant {{Use a locale-specific instance of Collator to sort non-English text correctly.}}
  }
}
