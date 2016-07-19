package parser;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import java.util.ArrayList;
import java.util.List;

/**
 * класс находит методы которым в параметры передается искомый объект
 * @author Ilya
 */
public class FoundMethodsCallByParameterValue {

    private final ArrayList<String> foundObject;//найденые объекты у которых вызван метод
    private final ArrayList<String> foundMethod;//найденые вызываемые методы которым в параметры передали искомый объект
    private final ArrayList<Integer> idx;//индекс передаваемого объекта в параметры метода
    
    public FoundMethodsCallByParameterValue(){
        this.foundObject = new ArrayList<>();
        this.foundMethod = new ArrayList<>();
        this.idx = new ArrayList<>();
    }
    
    public<T> void findMethodsByParameterValue(T cu, String nameParameter) {
        //ищем вызовы всех методов
        FoundMethodCall foundMethodCall = new FoundMethodCall();
        foundMethodCall.visit((MethodCallExpr) cu, null);

        //получаем список аргументов методов
        ArrayList<List<Expression>> foundMethodCallArgs = foundMethodCall.getFoundMethodCallArgs();
        //получаем список вызванных методов
        ArrayList<String> nameMethodCall = foundMethodCall.getNameMethodCall();
        //получаем список объектов у которых были вызваны методы
        ArrayList<String> nameScopeObject = foundMethodCall.getNameScopeObject();
        
        //поиск в параметрах метода переданные объекты
        for (int i = 0; i < nameScopeObject.size(); i++) {
            List<Expression> get = foundMethodCallArgs.get(i);
            for (int j = 0; j < get.size(); j++) {
                Expression expr = get.get(j);
                if(nameParameter.equals(expr.toString())){
                    foundObject.add(nameScopeObject.get(i));
                    foundMethod.add(nameMethodCall.get(i));
                    idx.add(j);
                }
            }
//            for (Expression expression1 : get) {
//                if(expression.toString().equals(expression1.toString())){
//                    foundObject.add(nameScopeObject.get(i));
//                    foundMethod.add(nameMethodCall.get(i));
//                }
//            }
        }
    }

    public ArrayList<String> getFoundObject() {
        return foundObject;
    }

    public ArrayList<String> getFoundMethod() {
        return foundMethod;
    }

    public ArrayList<Integer> getIdx() {
        return idx;
    }

    void findMethodsByParameterValue(MethodCallExpr cu, String nameObj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
