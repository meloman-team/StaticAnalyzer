package parser;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclaratorId;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * класс ищет объявленные методы в классе если в параметры передан null возвращает все найденные методы
 * при передачи названия метода возвращает методы с таким же именем
 * @author Ilya-pc
 */
public class FoundMethod extends VoidVisitorAdapter {

    private ArrayList<ArrayList<VariableDeclaratorId>> methodParameters;
    private ArrayList<MethodDeclaration> methodDeclaration;

    public FoundMethod() {
        methodParameters = new ArrayList<>();
        methodDeclaration = new ArrayList<>();
    }

    @Override
    public void visit(MethodDeclaration n, Object arg) {
        if (arg == null || arg.toString().equals(n.getName())) {
            methodDeclaration.add(n);
            List<Parameter> parameters = n.getParameters();
            ArrayList<VariableDeclaratorId> arr = new ArrayList<>();
            for (Parameter parameter : parameters) {
                arr.add(parameter.getId());
            }
            methodParameters.add(arr);
        }
    }

    public ArrayList<ArrayList<VariableDeclaratorId>> getMethodParameters() {
        return methodParameters;
    }

    public ArrayList<MethodDeclaration> getMethodDeclaration() {
        return methodDeclaration;
    }
}