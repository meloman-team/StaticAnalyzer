package module;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclaratorId;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.BlockStmt;
import model.ResultSharedResources;
import parser.FoundWaitAndNotify;
import parser.SearchConstructor;
import utils.ParserMetods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModuleWaitAndNotify {

    //список проверяемых файлов
    private ArrayList<String> files = new ArrayList();

    //список ошибок
    private ArrayList<String> error = new ArrayList();

    public ModuleWaitAndNotify(ArrayList<String> files) {
        this.files = files;
    }

    /**
     * @throws Exception
     */
    public ArrayList<String> main(ResultSharedResources resultSharedResources) throws Exception {
        for (String file : files) {
            CompilationUnit cu = ParserMetods.parse(file);
            //ищем все методы
            for (MethodDeclaration methodDeclaration : ParserMetods.findAllMethods(cu)) {
                //внутри каждого метода ищем вызов wait()
                BlockStmt body = methodDeclaration.getBody();
                FoundWaitAndNotify foundWaitAndNotify = ParserMetods.getFoundWaitAndNotify(body);
                ArrayList<Expression> objectOnWait = foundWaitAndNotify.getObjectOnWait();
                if (!objectOnWait.isEmpty()) {
                    String analyzFile = ParserMetods.splitPathToNameClass(file);
                    //TODO необходимо проверять полный путь до класса (не различает одинаковое название класса в разных пакетах)
                    ResultSharedResources.SharedThread analyzSharedThread = resultSharedResources.getSharedThread(analyzFile);
                    if (analyzSharedThread == null) {
                        error.add("ОШИБКА!!! В классе " + analyzFile + " найден вызов метода wait без вызова notify или notifyAll");
                        for (Expression exception : objectOnWait) {
                            error.add("Вызов wait в строке " + exception.getRange().begin.line);
                        }
                    } else {
                        Map<Expression, Boolean> result = new HashMap<>();
                        for (Expression exception : objectOnWait) {
                            result.put(exception, false);
                        }
                        for (ResultSharedResources.SharedResources sharedResources : analyzSharedThread.getSharedResources()) {
                            for (Expression exception : objectOnWait) {
                                if (sharedResources.getName().equals(exception.toString())) {
                                    String typeThread = sharedResources.getTypeThread();
                                    for (String file2 : files) {
                                        if (ParserMetods.splitPathToNameClass(file2).equals(typeThread)) {
                                            CompilationUnit cu2 = ParserMetods.parse(file2);
                                            for (MethodDeclaration methodDeclaration1 : ParserMetods.findAllMethods(cu2)) {
                                                //внутри каждого метода ищем вызов Notify()
                                                BlockStmt body2 = methodDeclaration1.getBody();
                                                FoundWaitAndNotify foundWaitAndNotify2 = ParserMetods.getFoundWaitAndNotify(body2);
                                                ArrayList<Expression> objectOnNotify = foundWaitAndNotify2.getObjectOnNotify();
                                                ArrayList<Expression> objectOnNotifyAll = foundWaitAndNotify2.getObjectOnNotifyAll();
                                                //TODO работает только если у класса не более одного конструктора
                                                SearchConstructor searchConstructor = ParserMetods.getSearchConstructor(ParserMetods.parse(file2));
                                                VariableDeclaratorId variableDeclaratorId = searchConstructor.getConstructorParameters().get(sharedResources.getIndex());
                                                for (Expression expr : objectOnNotify) {
                                                    if (expr.toString().equals(variableDeclaratorId.getName())) {
                                                        result.put(exception, true);
                                                    }
                                                }
                                                for (Expression expr : objectOnNotifyAll) {
                                                    if (expr.toString().equals(variableDeclaratorId.getName())) {
                                                        result.put(exception, true);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        //Проверка результатов
                        for (Expression exception : objectOnWait) {
                            if (!result.get(exception)) {
                                error.add("ОШИБКА!!! В классе " + analyzFile +
                                        " найден вызов метода wait в строке " + exception.getRange().begin.line +
                                        " без вызова notify или notifyAll");
                            }
                        }
                    }
                }
            }
        }
        return error;
    }

}