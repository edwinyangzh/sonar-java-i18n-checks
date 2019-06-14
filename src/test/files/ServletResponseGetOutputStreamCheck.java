/*
 * Copyright (c) 2018-2019 VMware, Inc. All Rights Reserved.
 * This software is released under MIT license.
 * The full license information can be found in LICENSE in the root directory of this project.
 */

import java.io.*;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;

class ServletResponseGetOutputStreamCheck {

  ServletOutputStream methodA1(ServletResponse servletResponse) {
    return servletResponse.getOutputStream();  // Noncompliant {{Use the getWriter instead of getOutputStream to exchange text (not binary) data.}}
  }
}
