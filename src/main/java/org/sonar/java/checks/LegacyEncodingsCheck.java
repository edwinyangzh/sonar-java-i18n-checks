/*
 * Copyright (c) 2018-2019 VMware, Inc. All Rights Reserved.
 * This software is released under MIT license.
 * The full license information can be found in LICENSE in the root directory of this project.
 */
package org.sonar.java.checks;

import org.sonar.check.Rule;
import org.sonar.java.model.LiteralUtils;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.LiteralTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Rule(key = "V1008")
public class LegacyEncodingsCheck extends BaseTreeVisitor implements JavaFileScanner {
  private static final String LEGACY_ENCODINGS_FILE = "/org/sonar/data/LegacyEncodings.txt";

  private List<String> legacyEncodings = loadLegacyEncodings();

  private JavaFileScannerContext context;

  @Override
  public void scanFile(final JavaFileScannerContext context) {
    this.context = context;
    scan(context.getTree());
  }

  @Override
  public void visitLiteral(LiteralTree tree) {
    if (tree.is(Tree.Kind.STRING_LITERAL)) {
      String value = LiteralUtils.trimQuotes(tree.value());
      if (legacyEncodings.contains(value.toLowerCase())) {
        context.reportIssue(this, tree, "Wherever possible use Unicode charset/encodings to avoid loss or corruption of text data.");
      }
    }
  }

  private static List<String> loadLegacyEncodings() {
    BufferedReader reader = new BufferedReader(new InputStreamReader(
        LegacyEncodingsCheck.class.getResourceAsStream(LEGACY_ENCODINGS_FILE),
        StandardCharsets.UTF_8));

    return reader.lines()
        .map(item -> item.trim())
        .filter(item -> item.length() > 0)
        .map(item -> item.toLowerCase())
        .collect(Collectors.toList());
  }
}
