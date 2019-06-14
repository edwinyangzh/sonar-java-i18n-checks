/*
 * Copyright (c) 2018-2019 VMware, Inc. All Rights Reserved.
 * This software is released under MIT license.
 * The full license information can be found in LICENSE in the root directory of this project.
 */

package org.sonar.java.checks.utils;

import org.sonar.java.matcher.MethodMatcher;

public class PositionMethodMatcher extends MethodMatcher {

  // -1 means no checked parameter
  private int position = -1;

  public static PositionMethodMatcher create() {
    return new PositionMethodMatcher();
  }

  public PositionMethodMatcher position(int position) {
    this.position = position;
    return this;
  }

  public int getPosition() {
    return position;
  }
}
