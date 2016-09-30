package parser;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

/**
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