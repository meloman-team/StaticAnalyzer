package parser;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.visitor.ModifierVisitorAdapter;
import java.util.ArrayList;

/**
 * Ищет инициализацию объектов
 *
 * @author Ilya-pc
 */
public class FoundInit extends ModifierVisitorAdapter {

    private ArrayList<Expression> objectInit;//чем инициализированны объекты
    private ArrayList<String> foundTypeObject;//найденые типы объектов

    public FoundInit() {
        objectInit = new ArrayList<>();
        foundTypeObject = new ArrayList<>();
    }

    @Override
    public Node visit(VariableDeclarator declarator, Object args) {
        if (args != null) {
            if (args.toString().equals(declarator.getId().toString())) {
                objectInit.add(declarator.getInit());
                foundTypeObject.add(declarator.getParentNode().getChildrenNodes().get(0).toString());
            }
        } else {
            objectInit.add(declarator.getInit());
            foundTypeObject.add(declarator.getParentNode().getChildrenNodes().get(0).toString());
        }
        return declarator;
    }

    public ArrayList<Expression> getObjectInit() {
        return objectInit;
    }

    public ArrayList<String> getFoundTypeObject() {
        return foundTypeObject;
    }

}
