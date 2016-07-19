package parser;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.body.VariableDeclaratorId;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.ModifierVisitorAdapter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ilya-pc
 */
public class FoundCreatedObject extends VoidVisitorAdapter {
    
    private ArrayList<String> foundCreatedObjet;//переменные в которых храняться созданный объект
    
    public FoundCreatedObject() {
        foundCreatedObjet = new ArrayList<>();
    }
    
    @Override
    public void visit(VariableDeclarationExpr declarator, Object args) {
//        System.out.println(args.toString());
//        System.out.println(declarator.getId().toString());
        if (args.toString().equals(declarator.getType().toString())) {
            List<VariableDeclarator> vars = declarator.getVars();
            for (VariableDeclarator var : vars) {
                foundCreatedObjet.add(var.getId().getName());
            }
        }
    }

    public ArrayList<String> getFoundCreatedObjet() {
        return foundCreatedObjet;
    }
}
