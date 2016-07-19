package parser;

import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclaratorId;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Находит конструктор класса
 * работает только если в классе один конструктор TODO сделать поиск всех конструкторов
 */
public class SearchConstructor extends VoidVisitorAdapter {

    private ArrayList<VariableDeclaratorId> ConstructorParameters;
    private String nameConstructor;
    private BlockStmt block;

    public SearchConstructor() {
        ConstructorParameters = new ArrayList<>();
        block = new BlockStmt();
    }

    @Override
    public void visit(ConstructorDeclaration n, Object arg) {
        nameConstructor = n.getName();
        block = n.getBlock();
        List<Parameter> parameters = n.getParameters();
        for (Parameter parameter : parameters) {
            if (parameter.getId() != null) {
                ConstructorParameters.add(parameter.getId());
            }
        }
    }

    public ArrayList<VariableDeclaratorId> getConstructorParameters() {
        return ConstructorParameters;
    }

    public String getNameConstructor() {
        return nameConstructor;
    }

    public BlockStmt getBlock() {
        return block;
    }
    
    
}
