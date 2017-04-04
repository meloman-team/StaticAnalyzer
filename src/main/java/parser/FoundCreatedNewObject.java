package parser;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Ищет создание класса
 * не работает если создание происходит в параметах метода
 */
public class FoundCreatedNewObject extends VoidVisitorAdapter {

    private ArrayList<String> typeObject;
    private ArrayList<String> nameScopeObject;//название переменной в которой храниться создаваемый объект
    private ArrayList<List<Expression>> foundConstructorArgs;//параметры

    public FoundCreatedNewObject() {
        typeObject = new ArrayList<>();
        nameScopeObject = new ArrayList<>();
        foundConstructorArgs = new ArrayList<>();
    }

    @Override
    public void visit(ObjectCreationExpr objectCreationExpr, Object args) {
        if (args == null || objectCreationExpr.getType().toString().equals(args.toString())) {
            Node parentNode = objectCreationExpr.getParentNode();
            List<Node> childrenNodes = parentNode.getChildrenNodes();
            String toString = childrenNodes.get(0).toString();
            String[] split = toString.split("this\\.");
            for (String string : split) {
                if (!string.equals("")) {
                    toString = string;
                }
            }
            nameScopeObject.add(toString);
            foundConstructorArgs.add(objectCreationExpr.getArgs());
            typeObject.add(objectCreationExpr.getType().toString());
        }
    }

    public ArrayList<String> getNameScopeObject() {
        return nameScopeObject;
    }

    public ArrayList<List<Expression>> getFoundConstructorArgs() {
        return foundConstructorArgs;
    }

}