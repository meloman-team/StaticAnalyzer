package parser;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

public class FoundCreatedObject extends VoidVisitorAdapter {

    /**
     * Список переменных в которых хранятся созданный объект
     */
    private ArrayList<String> foundCreatedObjet;

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