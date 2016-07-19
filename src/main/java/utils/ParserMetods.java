package utils;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.stmt.BlockStmt;
import parser.*;

import java.util.ArrayList;

/**
 * Created by SBT-Gorlovskiy-IA on 19.07.2016.
 */
public class ParserMetods {

    /**
     * Возвращает все найденные методы в переданном участке кода
     *
     * @param cu класс или метод в котором производиться поиск
     * @return список найденных методов
     */
    public static ArrayList<MethodDeclaration> findAllMethods(CompilationUnit cu) {
        FoundMethod foundMethod = new FoundMethod();
        foundMethod.visit(cu, null);
        return foundMethod.getMethodDeclaration();
    }


    /**
     * Возвращает заполненный FoundWaitAndNotify
     *
     * @param body блок кода (тело метода)
     * @return FoundWaitAndNotify
     */
    public static FoundWaitAndNotify getFoundWaitAndNotify(BlockStmt body) {
        FoundWaitAndNotify foundWaitAndNotify = new FoundWaitAndNotify();
        foundWaitAndNotify.visit(body, null);
        return foundWaitAndNotify;
    }


    /**
     * Возвращает заполненный SearchConstructor
     *
     * @param cu код класса
     * @return SearchConstructor
     */
    public static SearchConstructor getSearchConstructor(CompilationUnit cu) {
        SearchConstructor searchConstructor = new SearchConstructor();
        searchConstructor.visit(cu, null);
        return searchConstructor;
    }

    /**
     *
     *
     * @param blockStmt
     * @param arg
     * @param <A>
     * @return
     */
    public static <A> FoundAssign getFoundAssign(BlockStmt blockStmt, A arg) {
        FoundAssign fa = new FoundAssign();
        fa.visit(blockStmt, arg);
        return fa;
    }

    /**
     * возвращает заполненый FoundInit
     *
     * @param body блок кода (тело метода) где производиться поиск инициализации
     * @param obj объект для которого необходимо найти инициализацию
     * @return FoundInit
     */
    public static FoundInit getFoundInit(BlockStmt body, Object obj){
        FoundInit foundInit = new FoundInit();
        foundInit.visit(body, obj);//TODO НЕ НАХОДИТ ПРИСТВОЕНИЕ В КОНСТРУКТОРЕ
        return foundInit;
    }
}
