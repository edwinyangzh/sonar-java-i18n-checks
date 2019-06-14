/*
 * Copyright (c) 2018-2019 VMware, Inc. All Rights Reserved.
 * This software is released under MIT license.
 * The full license information can be found in LICENSE in the root directory of this project.
 */

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

class CharsetInstanceCreationCheck {

  Charset methodA1() {
    return Charset.defaultCharset();  // Noncompliant {{Use the Charset.forName method instead with an explicit charset/encoding argument.}}
  }

  Charset methodB1() {
    return Charset.forName("UTF-8");
  }

  Charset methodB2() {
    return Charset.forName("US-ASCII");  // Noncompliant {{Use an explicit, valid encoding/charset parameter with Charset factory method.}}
  }
}
