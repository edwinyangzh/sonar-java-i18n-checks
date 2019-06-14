/*
 * Copyright (c) 2018-2019 VMware, Inc. All Rights Reserved.
 * This software is released under MIT license.
 * The full license information can be found in LICENSE in the root directory of this project.
 */
package org.sonar.java.checks.methods;

import com.google.common.collect.ImmutableList;
import org.sonar.java.matcher.MethodMatcher;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.NewClassTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.List;

public abstract class AbstractMethodDetection extends IssuableSubscriptionVisitor {

  private List<MethodMatcher> matchers;

  @Override
  public List<Tree.Kind> nodesToVisit() {
    return ImmutableList.of(Tree.Kind.METHOD_INVOCATION, Tree.Kind.NEW_CLASS);
  }

  @Override
  public void visitNode(Tree tree) {
    if (hasSemantic()) {
      for (MethodMatcher invocationMatcher : matchers()) {
        checkInvocation(tree, invocationMatcher);
      }
    }
  }

  // vmw begin
  public MethodMatcher getMatchedMatcher(Tree tree) {
    for (MethodMatcher invocationMatcher : matchers()) {
      if (tree.is(Tree.Kind.METHOD_INVOCATION)) {
        MethodInvocationTree mit = (MethodInvocationTree) tree;
        if (invocationMatcher.matches(mit)) {
          return invocationMatcher;
        }
      } else if (tree.is(Tree.Kind.NEW_CLASS)) {
        NewClassTree newClassTree = (NewClassTree) tree;
        if (invocationMatcher.matches(newClassTree)) {
          return invocationMatcher;
        }
      }
    }
    return null;
  }
  // vmw end

  private void checkInvocation(Tree tree, MethodMatcher invocationMatcher) {
    if (tree.is(Tree.Kind.METHOD_INVOCATION)) {
      MethodInvocationTree mit = (MethodInvocationTree) tree;
      if (invocationMatcher.matches(mit)) {
        onMethodInvocationFound(mit);
      }
    } else if (tree.is(Tree.Kind.NEW_CLASS)) {
      NewClassTree newClassTree = (NewClassTree) tree;
      if (invocationMatcher.matches(newClassTree)) {
        onConstructorFound(newClassTree);
      }
    }
  }

  protected abstract List<MethodMatcher> getMethodInvocationMatchers();

  protected void onMethodInvocationFound(MethodInvocationTree mit) {
    // Do nothing by default
  }

  protected void onConstructorFound(NewClassTree newClassTree) {
    // Do nothing by default
  }

  private List<MethodMatcher> matchers() {
    if (matchers == null) {
      matchers = getMethodInvocationMatchers();
    }
    return matchers;
  }
}
