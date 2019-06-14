/*
 * Copyright (c) 2018-2019 VMware, Inc. All Rights Reserved.
 * This software is released under MIT license.
 * The full license information can be found in LICENSE in the root directory of this project.
 */

package org.sonar.java.checks.utils;

import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.Arguments;

import org.sonar.plugins.java.api.tree.ExpressionTree;

import static org.sonar.plugins.java.api.tree.Tree.Kind.MEMBER_SELECT;

public class MethodeUtil {

  /**
   * We use it to find Locale or Charset static instance argument in method call, the method usually has only one
   * argument of this kind.
   * @param argsList
   * @param argType
   * @return
   */
  public static ExpressionTree getTypedArgument(Arguments argsList, String argType) {
    for (ExpressionTree arg : argsList) {
      if (arg.symbolType().is(argType)) {
        return arg;
      }
    }
    return null;
  }

//  public static getArgumentLiteral(ExpressionTree arg, String localType) {
//    if (arg.is(MEMBER_SELECT)) {
//
//    }
//    for (ExpressionTree arg : argsList) {
//      if (arg.symbolType().is(localType)) {
//        return true;
//      }
//
//      Symbol identifierSymbol = arg.identifier().symbol();
//      Type ownerType = identifierSymbol.owner().type();
//      if (ownerType.is("java.nio.charset.StandardCharsets") &&
//          !VALID_CHARSET.contains(identifierSymbol.name().replace('_', '-'))) {
//        return true;
//      }
//      if ()
//    }
//  }
}
