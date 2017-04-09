package parser;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;

/**
 * Ищет инициализацию объектов
 */
public class FoundInit extends VoidVisitorAdapter {

    private ArrayList<Expression> objectInit;//чем инициализированны объекты
    private ArrayList<String> foundTypeObject;//найденые типы объектов

    public FoundInit() {
        objectInit = new ArrayList<>();
        foundTypeObject = new ArrayList<>();
    }

    @Override
    public void visit(VariableDeclarator declarator, Object args) {
        if (args != null) {
            if (args.toString().equals(declarator.getId().toString())) {
                objectInit.add(declarator.getInit());
                foundTypeObject.add(declarator.getParentNode().getChildrenNodes().get(0).toString());
            }
        } else {
            objectInit.add(declarator.getInit());
            foundTypeObject.add(declarator.getParentNode().getChildrenNodes().get(0).toString());
        }
    }

    public ArrayList<Expression> getObjectInit() {
        return objectInit;
    }

    public ArrayList<String> getFoundTypeObject() {
        return foundTypeObject;
    }

}