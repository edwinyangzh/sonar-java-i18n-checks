/*
 * Copyright (c) 2018-2019 VMware, Inc. All Rights Reserved.
 * This software is released under MIT license.
 * The full license information can be found in LICENSE in the root directory of this project.
 */

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class StringGetBytesCheck {
}

class BytesUtil {
  
  byte[] returnBytes(String input) throws UnsupportedEncodingException {
	return input.getBytes("UTF-8");
  }

  byte[] returnBytes2(String input) throws UnsupportedEncodingException {
    return input.getBytes("US-ASCII");  // Noncompliant {{Encoding argument passed to String.getBytes() is not valid.}}
  }

  byte[] returnBytes3(String input) {
    return input.getBytes(StandardCharsets.ISO_8859_1);  // Noncompliant {{Encoding argument passed to String.getBytes() is not valid.}}
  }

  byte[] returnBytes4(String input) {
    return input.getBytes(StandardCharsets.UTF_16);  // Noncompliant {{Encoding argument passed to String.getBytes() is not valid.}}
  }

  byte[] returnBytes5(String input) {
    return input.getBytes();  // Noncompliant {{Use an explicit, valid encoding/charset argument with getBytes.}}
  }

  byte[] returnBytes6(String input) {
    byte[] result = new byte[4];
    input.getBytes(1, 5, result, 0);  // Noncompliant {{This String.getBytes() is a deprecated method and it also can not accept an encoding argument.}}
    return result;
  }

  public static final String CHARSET_ASCII = "US-ASCII";
  byte[] returnBytes7(String input) throws UnsupportedEncodingException {
    return input.getBytes(CHARSET_ASCII);  // Noncompliant {{Encoding argument passed to String.getBytes() is not valid.}}
  }
}
