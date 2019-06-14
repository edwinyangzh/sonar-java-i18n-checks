/*
 * Copyright (c) 2018-2019 VMware, Inc. All Rights Reserved.
 * This software is released under MIT license.
 * The full license information can be found in LICENSE in the root directory of this project.
 */

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

class LegacyEncodingsCheck {
  public static final String UTF_8 = "UTF-8";
  public static final String BIG5 = "Big5";  // Noncompliant

  String methodA1(byte[] bytes) throws UnsupportedEncodingException {
    return new String(bytes, "ISO-8859-1");  // Noncompliant
  }

  String methodA1(byte[] bytes) throws UnsupportedEncodingException {
    return new String(bytes, "UTF-16");
  }
}
