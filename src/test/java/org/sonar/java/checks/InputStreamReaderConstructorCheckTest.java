/*
 * Copyright (c) 2018-2019 VMware, Inc. All Rights Reserved.
 * This software is released under MIT license.
 * The full license information can be found in LICENSE in the root directory of this project.
 */

package org.sonar.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;


public class InputStreamReaderConstructorCheckTest {
  @Test
  public void test() {
    JavaCheckVerifier.verify("src/test/files/InputStreamReaderConstructorCheck.java", new InputStreamReaderConstructorCheck());
  }
}
