/*
 * Copyright (c) 2018-2019 VMware, Inc. All Rights Reserved.
 * This software is released under MIT license.
 * The full license information can be found in LICENSE in the root directory of this project.
 */

public class StringCompareCheck {
  void methodA() {
    String a = "abc";
    a.compareTo("aaa");  // Noncompliant {{String.compareTo() and String.compareToIgnoreCase() fail to compare non-English text correctly. Use Java Collator instead.}}
    a.compareToIgnoreCase("aaa");  // Noncompliant {{String.compareTo() and String.compareToIgnoreCase() fail to compare non-English text correctly. Use Java Collator instead.}}
  }
}
