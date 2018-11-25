package parser;

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * поиск вызова методов
 */
public class FoundMethodCall extends VoidVisitorAdapter {

    private final ArrayList<List<Expression>> foundMethodCallArgs;//параметры методов
    private ArrayList<String> nameMethodCall;//названия методов
    private ArrayList<String> nameScopeObject;//названия объектов у кого вызван метод

    public FoundMethodCall() {
        foundMethodCallArgs = new ArrayList<>();
        nameMethodCall = new ArrayList<>();
        nameScopeObject = new ArrayList<>();
    }

    /**
     * метод ищет объекты у которых вызван метод переданный в параметры метода
     * в случае передачи в параметры null ищет все объекты с вызовом всех методов
     *
     * @param n
     * @param arg
     */
    @Override
    public void visit(MethodCallExpr n, Object arg) {
        if (arg == null || n.getName().equals(arg.toString())) {
            nameMethodCall.add(n.getName());//название метода
            nameScopeObject.add(n.getScope().toString());//название объекта у какого объекта вызван метод
            List<Expression> args = n.getArgs();//аргументы метода
            foundMethodCallArgs.add(args);
        }
    }

    public ArrayList<List<Expression>> getFoundMethodCallArgs() {
        return foundMethodCallArgs;
    }

    public ArrayList<String> getNameScopeObject() {
        return nameScopeObject;
    }

    public ArrayList<String> getNameMethodCall() {
        return nameMethodCall;
    }
}