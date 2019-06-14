/*
 * Copyright (c) 2018-2019 VMware, Inc. All Rights Reserved.
 * This software is released under MIT license.
 * The full license information can be found in LICENSE in the root directory of this project.
 */

import java.util.Locale;
import java.text.Collator;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class CollectionsSortCheck {
  void methodA() {
    ArrayList<String> names = new ArrayList<String>();
    names.add("Mike");
    names.add("Tom");
    Collections.sort(names);  // Noncompliant {{Use a locale-specific instance of Collator to sort non-English text correctly.}}
    Collections.sort(names, Collator.getInstance(Locale.CHINA));
  }
}
