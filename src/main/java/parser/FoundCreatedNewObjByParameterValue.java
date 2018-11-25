package parser;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Поиск создания нового объекта по входным параметрам
 */
public class FoundCreatedNewObjByParameterValue extends VoidVisitorAdapter {

    private ArrayList<String> typeObject;
    private ArrayList<String> nameScopeObject;
    private ArrayList<Integer> foundIdxArgs;//индекс совпавшего параметра

    public FoundCreatedNewObjByParameterValue() {
        typeObject = new ArrayList<>();
        nameScopeObject = new ArrayList<>();
        foundIdxArgs = new ArrayList<>();
    }

    @Override
    public void visit(ObjectCreationExpr objectCreationExpr, Object args) {
        if (args != null) {
            List<Expression> args1 = objectCreationExpr.getArgs();
            for (int i = 0; i < args1.size(); i++) {
                if (args1.get(i).toString().equals(args.toString())) {
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
                    foundIdxArgs.add(i);
                    typeObject.add(objectCreationExpr.getType().toString());
                }
            }
        }
    }

    public ArrayList<String> getTypeObject() {
        return typeObject;
    }

    public ArrayList<String> getNameScopeObject() {
        return nameScopeObject;
    }

    public ArrayList<Integer> getFoundIdxArgs() {
        return foundIdxArgs;
    }

}