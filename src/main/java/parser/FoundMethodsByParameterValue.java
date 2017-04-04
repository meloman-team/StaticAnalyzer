package parser;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.Expression;
import java.util.ArrayList;
import java.util.List;

public class FoundMethodsByParameterValue {

    private final ArrayList<String> foundObject;//найденые объекты
    private final ArrayList<String> foundMethod;//найденые методы
    
    FoundMethodsByParameterValue(CompilationUnit cu, Expression expression){
        this.foundObject = new ArrayList<>();
        this.foundMethod = new ArrayList<>();
    }
    
    public void findMethodsByParameterValue(CompilationUnit cu, Expression expression) {
        //ищем вызовы всех методов
        FoundMethodCall foundMethodCall = new FoundMethodCall();
        foundMethodCall.visit(cu, null);

        //получаем список аргументов методов
        ArrayList<List<Expression>> foundMethodCallArgs = foundMethodCall.getFoundMethodCallArgs();
        //получаем список вызванных методов
        ArrayList<String> nameMethodCall = foundMethodCall.getNameMethodCall();
        //получаем список объектов у которых были вызваны методы
        ArrayList<String> nameScopeObject = foundMethodCall.getNameScopeObject();
        
        for (int i = 0; i < nameScopeObject.size(); i++) {
            List<Expression> get = foundMethodCallArgs.get(i);
            for (Expression expression1 : get) {
                if(expression.toString().equals(expression1.toString())){
                    foundObject.add(nameScopeObject.get(i));
                    foundMethod.add(nameMethodCall.get(i));
                }
            }
        }
    }

    public ArrayList<String> getFoundObject() {
        return foundObject;
    }

    public ArrayList<String> getFoundMethod() {
        return foundMethod;
    }

}