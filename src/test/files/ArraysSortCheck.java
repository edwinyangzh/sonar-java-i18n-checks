/*
 * Copyright (c) 2018-2019 VMware, Inc. All Rights Reserved.
 * This software is released under MIT license.
 * The full license information can be found in LICENSE in the root directory of this project.
 */
import java.util.Locale;
import java.text.Collator;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class ArraysSortCheck {
  void methodA() {
    String[] names = new String[]{"Mike", "Tom"};
    Arrays.sort(names);  // Noncompliant {{Use a locale-specific instance of Collator to sort non-English text correctly.}}
    Arrays.sort(names, 0, 2);  // Noncompliant {{Use a locale-specific instance of Collator to sort non-English text correctly.}}
    Arrays.sort(names, Collator.getInstance(Locale.CHINA));
    Arrays.sort(names, 0, 2, Collator.getInstance(Locale.CHINA));

    int[] numbers = new int[]{1, 2, 3};
    Arrays.sort(numbers);
  }
}
