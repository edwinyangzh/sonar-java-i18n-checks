/*
 * Copyright (c) 2018-2019 VMware, Inc. All Rights Reserved.
 * This software is released under MIT license.
 * The full license information can be found in LICENSE in the root directory of this project.
 */
package org.sonar.java.checks;

import com.google.common.collect.ImmutableList;
import org.sonar.java.checks.utils.LocaleUtil;
import org.sonar.java.checks.methods.AbstractMethodDetection;
import org.sonar.java.model.LiteralUtils;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.*;

import java.util.List;

public abstract class AbstractMethodCharsetDetection extends AbstractMethodDetection {
  private static final List<String> VALID_CHARSET = ImmutableList.of("UTF-8", "utf-8");

  protected boolean isInvalidLiteralStringCharset(ExpressionTree argument) {
    if (!argument.is(Tree.Kind.STRING_LITERAL)) {
      return false;
    }
    String charset = LiteralUtils.trimQuotes(((LiteralTree)argument).value());
    if (!isValidCharset(charset)) {
      return true;
    }
    return false;
  }

  protected boolean isInvalidStandardCharsetsInstanceCharset(ExpressionTree argument) {
    String fieldName = LocaleUtil.getNodeValueStaticFieldName(argument, "java.nio.charset.StandardCharsets");
    if (fieldName != null && !isValidCharset(fieldName.replace('_', '-'))) {
      return true;
    }
    return false;
  }

  protected boolean isInvalidConstVariableCharset(ExpressionTree argument) {
    String constantValue = findConstantVariableValue(argument);
    if (constantValue == null) {
      return false;
    }
    if (!isValidCharset(constantValue)) {
      return true;
    }
    return false;
  }

  protected boolean isInvalidCharset(ExpressionTree argument) {
    if (isInvalidLiteralStringCharset(argument) || isInvalidStandardCharsetsInstanceCharset(argument) ||
        isInvalidConstVariableCharset(argument)) {
      return true;
    }
    return false;
  }

  protected boolean isValidCharset(String charset) {
    if (charset != null && VALID_CHARSET.contains(charset)) {
      return true;
    }
    return false;
  }

  /**
   * A class static const field member or method local variable as argument:
   *     String CHARSET = "utf-8";
   * @param argument
   * @return
   */
  protected String findConstantVariableValue(ExpressionTree argument) {
    if (argument.symbolType().is("java.lang.String")) {
      IdentifierTree identifier;
      if (argument.is(Tree.Kind.IDENTIFIER)) {
        identifier = (IdentifierTree)argument;
      } else if (argument.is(Tree.Kind.MEMBER_SELECT)) {
        identifier = ((MemberSelectExpressionTree)argument).identifier();
      } else {
        return null;
      }
      Symbol symbol = identifier.symbol();
      if (symbol != null && symbol.isVariableSymbol()) {
        Symbol.VariableSymbol varSymbol = (Symbol.VariableSymbol)symbol;
        if (varSymbol.declaration() != null && varSymbol.declaration().initializer() != null) {
          ExpressionTree initValue = varSymbol.declaration().initializer();
          if (initValue.is(Tree.Kind.STRING_LITERAL)) {
            String result = LiteralUtils.trimQuotes(((LiteralTree) initValue).value());
            //System.out.println("const string value = " + result);
            return result;
          }
        }
      }
    }
    return null;
  }
}
