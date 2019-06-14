/*
 * Copyright (c) 2018-2019 VMware, Inc. All Rights Reserved.
 * This software is released under MIT license.
 * The full license information can be found in LICENSE in the root directory of this project.
 */

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

class StringConstructorCheck {

  String methodA1(byte[] bytes) {
    return new String(bytes);  // Noncompliant {{Use non-deprecated String constructors with an explicit, valid charset/encoding parameter.}}
  }

  String methodA2(byte[] bytes) {
    return new String(bytes, 1, 2);  // Noncompliant {{Use non-deprecated String constructors with an explicit, valid charset/encoding parameter.}}
  }

  String methodB1(byte[] bytes) {
    return new String(bytes, 88);  // Noncompliant {{This constructor has been deprecated and it also can not accept a charset parameter for product internationalization.}}
  }

  String methodB2(byte[] bytes) {
    return new String(bytes, 88, 2, 2);  // Noncompliant {{This constructor has been deprecated and it also can not accept a charset parameter for product internationalization.}}
  }

  String methodC1(byte[] bytes) throws UnsupportedEncodingException {
    return new String(bytes,"UTF-8");
  }

  String methodC2(byte[] bytes) throws UnsupportedEncodingException {
    return new String(bytes,"US-ASCII");  // Noncompliant {{Use an explicit, valid encoding/charset argument with String constructor.}}
  }

  String methodC3(byte[] bytes) throws UnsupportedEncodingException {
    return new String(bytes, 1, 2, "UTF-8");
  }

  String methodC4(byte[] bytes) throws UnsupportedEncodingException {
    return new String(bytes, 1, 2, "US-ASCII");  // Noncompliant {{Use an explicit, valid encoding/charset argument with String constructor.}}
  }

  String methodD1(byte[] bytes) {
    return new String(bytes, StandardCharsets.UTF_16);  // Noncompliant {{Use an explicit, valid encoding/charset argument with String constructor.}}
  }

  String methodD2(byte[] bytes) {
    return new String(bytes, StandardCharsets.ISO_8859_1);  // Noncompliant {{Use an explicit, valid encoding/charset argument with String constructor.}}
  }

  String methodD3(byte[] bytes) {
    return new String(bytes, 1, 2, StandardCharsets.UTF_16);  // Noncompliant {{Use an explicit, valid encoding/charset argument with String constructor.}}
  }

  String methodD4(byte[] bytes) {
    return new String(bytes, 1, 2, StandardCharsets.ISO_8859_1);  // Noncompliant {{Use an explicit, valid encoding/charset argument with String constructor.}}
  }
}
