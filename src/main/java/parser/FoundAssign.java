package parser;

import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;

/**
 *  в конструкторе ищет присвоение в конструкторе
 */
public class FoundAssign extends VoidVisitorAdapter {
    
    private  ArrayList<Expression> objectAssign;
    
    public FoundAssign(){
        objectAssign = new ArrayList<>();
    }

    @Override
    public void visit(AssignExpr n, Object arg) {
        if (arg != null) {
            if (n.getTarget().toString().equals(arg.toString())
                    || n.getTarget().toString().equals("this." + arg.toString())) {
                objectAssign.add(n.getValue());
            }
        }
//        System.out.println(n);
//        System.out.println(n.getOperator());
//        System.out.println(n.getTarget());
//        System.out.println(n.getValue());
    }

    public ArrayList<Expression> getObjectAssign() {
        return objectAssign;
    }
    
}
