/*
 * Copyright (c) 2018-2019 VMware, Inc. All Rights Reserved.
 * This software is released under MIT license.
 * The full license information can be found in LICENSE in the root directory of this project.
 */
package org.sonar.java.checks.utils;

import com.google.common.collect.ImmutableList;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;

import java.util.List;

import static org.sonar.plugins.java.api.tree.Tree.Kind.IDENTIFIER;
import static org.sonar.plugins.java.api.tree.Tree.Kind.MEMBER_SELECT;

public class LocaleUtil {

  private LocaleUtil() {}

  /**
   * get a list of all the java.util.Locale static field instance names.
   * @return
   */
  public static List<String> getStaticLocaleInstanceNames() {
    return ImmutableList.<String>builder()
        .add("CANADA").add("CANADA_FRENCH").add("CHINA").add("CHINESE")
        .add("ENGLISH").add("FRANCE").add("FRENCH").add("GERMAN")
        .add("GERMANY").add("ITALIAN").add("ITALY").add("JAPAN")
        .add("JAPANESE").add("KOREA").add("KOREAN").add("PRC")
        .add("ROOT").add("SIMPLIFIED_CHINESE").add("TAIWAN").add("TRADITIONAL_CHINESE")
        .add("UK").add("US")
        .build();
  }

  /**
   * get name of static field for some type, such as:
   *     Locale: Locale.ENGLISH, ENGLISH(static import)
   *     StandardCharsets: StandardCharsets.US_ASCII, US_ASCII(static import)
   *
   * @param typeName    the type name, possible values are "java.nio.charset.StandardCharsets"
   */
  public static String getNodeValueStaticFieldName(ExpressionTree node, String typeName) {
    IdentifierTree identifier = null;
    if (node.is(MEMBER_SELECT)) {
      identifier = ((MemberSelectExpressionTree)node).identifier();
    } else if (node.is(IDENTIFIER)) {
      identifier = ((IdentifierTree)node);
    } else {
      return null;
    }

    Symbol identifierSymbol = identifier.symbol();
    if (identifierSymbol != null && identifierSymbol.owner() != null & identifierSymbol.owner().type() != null) {
      Type ownerType = identifierSymbol.owner().type();
      if (ownerType.is(typeName)) {
        return identifierSymbol.name();
      }
    }
    return null;
  }
}
