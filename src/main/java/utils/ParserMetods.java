package utils;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import parser.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParserMetods {

    public static String splitPathToNameClass(String path) {
        String[] split = path.split(".+\\\\");
        for (String string : split) {
            if (!string.equals("")) {
                path = string;
            }
        }
        String[] sp = path.split("\\.java");
        for (String string : sp) {
            if (!string.equals("")) {
                path = string;
            }
        }
        return path;
    }

    public static CompilationUnit parse(String path) throws ParseException, IOException {
        FileInputStream in = new FileInputStream(path);

        CompilationUnit cu;
        try {
            cu = JavaParser.parse(in);
        } finally {
            in.close();
        }
        return cu;
    }

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
     * Возвращает заполненный FoundWaitAndNotify
     *
     * @param body блок кода (тело метода)
     * @return FoundWaitAndNotify
     */
    public static FoundWaitAndNotify getFoundWaitAndNotify(CompilationUnit body) {
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
     * Присвоение?
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
     * @param obj  объект для которого необходимо найти инициализацию
     * @return FoundInit
     */
    public static FoundInit getFoundInit(BlockStmt body, Object obj) {
        FoundInit foundInit = new FoundInit();
        foundInit.visit(body, obj);//TODO НЕ НАХОДИТ ПРИСТВОЕНИЕ В КОНСТРУКТОРЕ
        return foundInit;
    }

    /**
     * возвращает заполненый FoundInit
     *
     * @param body блок кода (тело файла) где производиться поиск инициализации
     * @param obj  объект для которого необходимо найти инициализацию
     * @return FoundInit
     */
    public static FoundInit getFoundInit(CompilationUnit body, Object obj) {
        FoundInit foundInit = new FoundInit();
        foundInit.visit(body, obj);//TODO НЕ НАХОДИТ ПРИСТВОЕНИЕ В КОНСТРУКТОРЕ
        return foundInit;
    }

    /**
     * возвращает заполненый FoundCreatedNewObject
     *
     * @param cu        класс или метод в котором производиться поиск
     * @param nameClass название искомого класса
     * @return FoundCreatedNewObject
     */
    public static FoundCreatedNewObject getFoundCreatedNewObject(CompilationUnit cu, String nameClass) {
        FoundCreatedNewObject foundCreatedNewObject = new FoundCreatedNewObject();
        foundCreatedNewObject.visit(cu, nameClass);
        return foundCreatedNewObject;
    }

    /**
     * @param cu
     * @param nameObj
     * @return
     */
    public static FoundCreatedNewObjByParameterValue getFoundCreatedNewObjByParameterValue(CompilationUnit cu, String nameObj) {
        FoundCreatedNewObjByParameterValue byParameterValue = new FoundCreatedNewObjByParameterValue();
        byParameterValue.visit(cu, nameObj);
        return byParameterValue;
    }

    public static FoundCreatedObject getFoundCreatedObject(CompilationUnit cu, String nameObj) {
        FoundCreatedObject foundCreatedObject = new FoundCreatedObject();
        foundCreatedObject.visit(cu, nameObj);
        return foundCreatedObject;
    }

    public static FoundMethodCall getFoundMethodCall(CompilationUnit cu, String nameMethod) {
        FoundMethodCall foundMethodCall = new FoundMethodCall();
        foundMethodCall.visit(cu, nameMethod);
        return foundMethodCall;
    }

    public static FoundMethod getFoundMethod(CompilationUnit cu, String nameMethod) {
        FoundMethod fm = new FoundMethod();
        fm.visit(cu, nameMethod);
        return fm;
    }

    public static List<String> getFoundImport(CompilationUnit cu) {
        List<ImportDeclaration> imports = cu.getImports();
        ArrayList<String> ImportNames = new ArrayList<>();
        for (ImportDeclaration importDeclaration : imports) {
            ImportNames.add(importDeclaration.getName().getName());
        }
        return ImportNames;
    }
}