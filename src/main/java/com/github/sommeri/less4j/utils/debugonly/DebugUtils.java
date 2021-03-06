package com.github.sommeri.less4j.utils.debugonly;

import java.util.HashSet;
import java.util.Set;

import com.github.sommeri.less4j.core.ast.ASTCssNode;
import com.github.sommeri.less4j.core.ast.Expression;
import com.github.sommeri.less4j.core.compiler.scopes.IScope;

// marked deprecated so I get a warning if it is referenced somewhere
@Deprecated
public class DebugUtils {

  private Set<ASTCssNode> duplicates = new HashSet<ASTCssNode>();
  
  public DebugUtils() {
  }

  /** 
   * Variable name should contain @ too e.g. "@width"
   */
  public void scopeTest(IScope scope, Object id, String variableName) {
    try {
      System.out.println("--- Scope Test: " +id+ " Scope name: " + scope);
      Expression value = scope.getValue("@width");
      
      String text = variableName + ": " + value;
      if (value==null) {
        text +=" !";
      }
      System.out.println(text);
    } catch (Throwable th) {
      th.printStackTrace();
    }
  }

  public void solveParentChildRelationShips(ASTCssNode node) {
    for (ASTCssNode kid : node.getChilds()) {
      kid.setParent(node);
      solveParentChildRelationShips(kid);
    }
  }

  public void checkParentChildRelationshipsSanity(ASTCssNode node, String prefix) {
    duplicates = new HashSet<ASTCssNode>();
    doCheckParentChildRelationshipsSanity(node, prefix);
  }

  private void doCheckParentChildRelationshipsSanity(ASTCssNode node, String prefix) {
    for (ASTCssNode kid : node.getChilds()) {
      if (duplicates.contains(kid))
        System.out.println("duplicate " + prefix + kid);
      
      duplicates.add(kid);
      
      if (kid.getParent() != node)
        System.out.println("parent " + prefix + kid);

      doCheckParentChildRelationshipsSanity(kid, prefix);
    }
  }


}
