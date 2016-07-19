package parser;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;

/**
 * Поиск вызова методов wait, notify, notifyAll и объектов у которых они вызваны
 */
public class FoundWaitAndNotify extends VoidVisitorAdapter {
    
    private ArrayList<Expression> objectOnWait;
    private ArrayList<Expression> objectOnNotify;
    private ArrayList<Expression> objectOnNotifyAll;
    
    public FoundWaitAndNotify() {
        objectOnWait = new ArrayList<>();
        objectOnNotify = new ArrayList<>();
        objectOnNotifyAll = new ArrayList<>();
    }
     
    @Override
        public void visit(MethodCallExpr n, Object arg) {
            if(n.getName().equals("wait")) objectOnWait.add(n.getScope());
            if(n.getName().equals("notify")) objectOnNotify.add(n.getScope());
            if(n.getName().equals("notifyAll")) objectOnNotifyAll.add(n.getScope());
//            System.out.println(n.getName());
//            System.out.println(n.getScope());
//            System.out.println("========");
        }

    public ArrayList<Expression> getObjectOnWait() {
        return objectOnWait;
    }

    public ArrayList<Expression> getObjectOnNotify() {
        return objectOnNotify;
    }

    public ArrayList<Expression> getObjectOnNotifyAll() {
        return objectOnNotifyAll;
    }
    
    
}
