package model;

import java.util.ArrayList;
import java.util.List;

public class ThreadObject {
    /**
     * Тип переменной в которой храниться созданный объект
     */
    private String variableType;
    /**
     * параметры передаваемые в конструктор при создании объекта
     */
    private List<String> constructorParameters = new ArrayList<>();

    public String getVariableType() {
        return variableType;
    }

    public void setVariableType(String variableType) {
        this.variableType = variableType;
    }

    public List<String> getConstructorParameters() {
        return constructorParameters;
    }

    public void setConstructorParameters(List<String> constructorParameters) {
        this.constructorParameters = constructorParameters;
    }

    public void addConstructorParameters(String parameter){
        this.constructorParameters.add(parameter);
    }
}
