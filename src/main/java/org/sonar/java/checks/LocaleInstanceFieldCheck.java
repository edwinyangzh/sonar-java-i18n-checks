/*
 * Copyright (c) 2018-2019 VMware, Inc. All Rights Reserved.
 * This software is released under MIT license.
 * The full license information can be found in LICENSE in the root directory of this project.
 */
package org.sonar.java.checks;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Rule;
import org.sonar.java.checks.utils.LocaleUtil;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.ArrayList;
import java.util.List;

@Rule(key = "V1019")
public class LocaleInstanceFieldCheck extends IssuableSubscriptionVisitor {
  private static final String LOCALE = "java.util.Locale";

  @Override
  public List<Tree.Kind> nodesToVisit() {
    return ImmutableList.of(Tree.Kind.IDENTIFIER);
  }

  @Override
  public void visitNode(Tree tree) {
    if (hasSemantic()) {
      Symbol identifierSymbol = ((IdentifierTree)tree).symbol();
      if (identifierSymbol.isUnknown() || (identifierSymbol.owner() == null) || (identifierSymbol.owner().type() == null)) {
        return;
      }
      Type ownerType = identifierSymbol.owner().type();
      if (ownerType.is(LOCALE) && getCheckedLocales().contains(identifierSymbol.name())) {
        context.reportIssue(this, tree, "Use explicit, user-preferred Locale settings to insure an ideal international user experience.");
      }
    }
  }

  private static List<String> getCheckedLocales() {
    List<String> localeInstanceNames = new ArrayList<>(LocaleUtil.getStaticLocaleInstanceNames());
    // ENGLISH locale can be used in some situations
    localeInstanceNames.remove("ENGLISH");
    return localeInstanceNames;
  }
}
