package module;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclaratorId;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.BlockStmt;
import parser.*;
import utils.ParserMetods;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ModuleWaitAndNotify {
//TODO Для нормальной работы необходимо подключить поиск разделяемых переменных
//TODO если поток блокируется без таймера то разбудить его может только другой поток с таким же объектом синхронизации

    //список проверяемых файлов 
    ArrayList<String> files = new ArrayList();

    public ModuleWaitAndNotify(ArrayList<String> files) {
        this.files = files;
    }

    /**
     * @throws Exception
     */
    public void main() throws Exception {
        for (String file : files) {

            CompilationUnit cu = parse(file);

            //ищем все методы
            for (MethodDeclaration methodDeclaration1 : ParserMetods.findAllMethods(cu)) {
                //внутри каждого метода ищем вызов wait()
                BlockStmt body = methodDeclaration1.getBody();
                FoundWaitAndNotify foundWaitAndNotify = ParserMetods.getFoundWaitAndNotify(body);
                ArrayList<Expression> objectOnWait = foundWaitAndNotify.getObjectOnWait();

                if (!objectOnWait.isEmpty()) {

                    for (Expression objectOnWait1 : objectOnWait) {
                        //для кждог объекта у которого вызван wait() ищем инициализацию в конструкторе
                        //TODO работает только если у класса не более одного конструктора
                        SearchConstructor searchConstructor = ParserMetods.getSearchConstructor(cu);

                        ArrayList<Expression> objectAssign = ParserMetods.getFoundAssign(searchConstructor.getBlock(), objectOnWait1).getObjectAssign();

                        //если есть инициализация смотрим наличие в конструкторах класса
                        if (!objectAssign.isEmpty()) {
                            ArrayList<VariableDeclaratorId> constructorParameters = searchConstructor.getConstructorParameters();
                            for (int i = 0; i < constructorParameters.size(); i++) {
                                VariableDeclaratorId get = constructorParameters.get(i);
                                for (Expression expression : objectAssign) {
                                    if (get.toString().equals(expression.toString())) {
                                        //производим поиск классов которые вызывают этот конструктор
                                        //найти not на объекте который передали в конструктор
                                        if (function2(file, methodDeclaration1.getName(), i)) {
                                            System.out.println("Все ок. Нет вызовов вейт либо на все вейт есть нотифай");
                                        } else {
                                            System.out.println("ошибка при поиске конструкторов");
                                        }
                                    }
                                }
                            }
                        }

                        //для каждог объекта у которого вызван wait() ищем инициализацию в методе
                        //TODO НЕ НАХОДИТ ПРИСТВОЕНИЕ В КОНСТРУКТОРЕ
                        ArrayList<Expression> objectInit = ParserMetods.getFoundInit(body, objectOnWait1).getObjectInit();

                        List<Parameter> parameters = methodDeclaration1.getParameters();
                        for (int i = 0; i < parameters.size(); i++) {
                            //ищем в параметрах метода объект который инициализирует объект с вызовом wait()
                            if (!objectInit.isEmpty()) {
                                for (Expression objectInit1 : objectInit) {
                                    if (parameters.get(i).getId().toString().equals(objectInit1.toString())) {
                                        //ищем классы где создаются объекты класса и вызываются методы содержащие wait()
                                        if (function(file, methodDeclaration1.getName(), i)) {
                                            System.out.println("Все ок. Нет вызовов вейт либо на все вейт есть нотифай");
                                        } else {
                                            System.out.println("ошибка при поиске в методах №1");
                                        }
                                    }
                                }
                            }

                            //ищем в параметрах метода объект с вызовом wait()
                            if (parameters.get(i).getId().toString().equals(objectOnWait1.toString())) {
                                if (function(file, methodDeclaration1.getName(), i)) {
                                    System.out.println("Все ок. Нет вызовов вейт либо на все вейт есть нотифай");
                                } else {
                                    System.out.println("ошибка при поиске в методах №2");
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    /**
     * @param pathClass
     * @param nameMethod
     * @param index      индекс параметра конструктора на котором вызван wait
     * @return
     */
    private boolean function2(String pathClass, String nameMethod, int index) {
        //во всех проверяемых классах ищем подлючение данного класса
        ArrayList<String> foundClassImport = foundClassImport(splitPathToNameClass(pathClass));

        for (String allClas : files) {
            if (allClas.equals(pathClass)) continue;
            CompilationUnit cu = null;
            CompilationUnit cu2 = null;

            try {
                cu = parse(allClas);
                cu2 = parse(pathClass);
            } catch (ParseException ex) {
                Logger.getLogger(ModuleWaitAndNotify.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ModuleWaitAndNotify.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (cu.getPackage().toString().equals(cu2.getPackage().toString())) {
                //добавляем все классы из того же пакета
                foundClassImport.add(allClas);
            }
        }

        if (!foundClassImport.isEmpty()) {
            for (String foundClassImport1 : foundClassImport) {

                CompilationUnit cu = null;

                try {
                    cu = parse(foundClassImport1);
                } catch (ParseException ex) {
                    Logger.getLogger(ModuleWaitAndNotify.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ModuleWaitAndNotify.class.getName()).log(Level.SEVERE, null, ex);
                }

                //ищем создание класса в котором есть метод с вызовом wait
                ArrayList<List<Expression>> foundCreatedNewObjectArgs = ParserMetods
                        .getFoundCreatedNewObject(cu, splitPathToNameClass(pathClass))
                        .getFoundConstructorArgs();

                //если нет создания обыекта с вызовом вейт то нет и остановки потока
                if (foundCreatedNewObjectArgs.isEmpty()) {
                    continue;
                }

                //проверяем что на каждый вызов вейт есть нотифай
                for (int i = 0; i < foundCreatedNewObjectArgs.size(); i++) {

                    //проверить тот ли конструктор!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    //найти вызов метода с wait
                    //если вызван
                    //найти not на объекте который передали в конструктор
                    //если нет то наверное пох
                    //получаем параметр на котором вызван wait
                    String nameObj = foundCreatedNewObjectArgs.get(i).get(index).toString();
                    /////////////////////!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    //TODO по идее должен найти куда еще пихается O и в этом методе лили конструкторе искать нотифай
                    //если этот объеект пихается в другой поток то мы можем вызвать нотифай
                    //а если он пихается в другой метод или конструктов в том же потоке то мы не сможем вызвать нотифай
                    //сделать поиск потоков и проверять условия прописанные выше
                    //////////////////////!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

                    ////////////////////////////////
                    //наверное не правильный рек2 в него надо пихнуть индекс параметра конструктора на котором вызывается вейт
                    //проверить это но скорее всего все верно сделано
                    ///////////////////////////////
                    //ищем вызов not в других конструкторах
                    return rec2(cu, nameObj);
                    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    //TODO Если ничего не найдено надо проверить отправку в методы
                    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                }
            }
        }
        //нет подключений нет вызова все хорошо (foundClassImport.isEmpty
        System.out.println("function2 return true");
        return true;
    }

    //сu - класс
    //nameObj - имя параметра который передали в конструктор. (на нем вызван wait в этом же классе нужно найти not)
    //вызовится ли нотифай в том же классе(потоке) где вызван wate
    ////////////////////////////////////////////////////////////////////
    //TODO в том же классе может вызываться notif если метод не синхранизирован и вызывается из другого потока с тем же объектом
    ////////////////////////////////////////////////////////////////////
    private <T> boolean rec2(T cu, String nameObj) {
        //найти вызов notifay в найденом методе у данной переменной
        if (!foundNotify(cu, nameObj)) {
            //поиск отправки объекта в другие конструкторы
            //TODO находит все конструкторы в том числе который нашли выше (создание объекта с вызовом wait поправить + сделать проверку отправки в потоки
            //TODO подумать если создание потока происходит в цикле может ли один и тот же метод снять блокировку и как посчитать созданные потоки
            FoundCreatedNewObjByParameterValue byParameterValue = ParserMetods.getFoundCreatedNewObjByParameterValue((CompilationUnit) cu, nameObj);
            ArrayList<String> foundTypeObject = byParameterValue.getTypeObject();
            ArrayList<String> foundObject = byParameterValue.getNameScopeObject();
            ArrayList<Integer> idx = byParameterValue.getFoundIdxArgs();

            if (foundObject.size() == 0) {
                System.out.println("rec2 return false");
                return false;
            } else {

                //если findTypeObjectInfiles останется false
                //значит нет вызова not значит вернем false
                boolean findTypeObjectInfiles = false;

                //у каждого объекта ищем тип
                for (int k = 0; k < foundObject.size(); k++) {
                    //находим исходники класса объявленной переменной
                    for (String path : files) {
                        String sNameClass = splitPathToNameClass(path);
                        if (foundTypeObject.get(k).equals(sNameClass)) {

                            CompilationUnit cu2 = null;
                            try {
                                cu2 = parse(path);
                            } catch (ParseException ex) {
                                Logger.getLogger(ModuleWaitAndNotify.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(ModuleWaitAndNotify.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            //в классе ищем конструктор
                            //получаем имя объекта в параметрах конструктор на котором должен быть вызван Notify
                            SearchConstructor searchConstructor = ParserMetods.getSearchConstructor(cu2);
                            ArrayList<VariableDeclaratorId> constructorParameters = searchConstructor.getConstructorParameters();
                            VariableDeclaratorId get = constructorParameters.get(idx.get(k));
                            ArrayList<Expression> objectAssign = ParserMetods.getFoundAssign(searchConstructor.getBlock(), get).getObjectAssign();
                            //внутри каждого класса ищем вызов Notify()
                            //ищем передачу в другие конструкторы данного объекта
                            //получаем имя объекта в конструкторе на котором должен быть вызван Notify
                            String parameterName = objectAssign.get(0).toString();
                            if (rec2(cu2, parameterName)) {
                                findTypeObjectInfiles = true;
                            }
                            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                            //TODO Если ничего не найдено надо проверить отправку в методы
                            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        }
                    }
                }
                //если в цикле ничего не найдено то ошибка
                if (!findTypeObjectInfiles) {
                    System.out.println("rec2 return false");
                }
                return findTypeObjectInfiles;
            }
        }
        System.out.println("rec2 return true");
        return true;
    }

    private boolean function(String pathClass, String nameMethod, int index) {
        //во всех проверяемых классах ищем подлючение данного класса
        ArrayList<String> foundClassImport = foundClassImport(splitPathToNameClass(pathClass));
        //добавляем все классы из того же пакета
        for (String allClas : files) {
            CompilationUnit cu = null;
            CompilationUnit cu2 = null;

            try {
                cu = parse(allClas);
                cu2 = parse(pathClass);
            } catch (ParseException ex) {
                Logger.getLogger(ModuleWaitAndNotify.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ModuleWaitAndNotify.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (cu.getPackage().toString().equals(cu2.getPackage().toString())) {
                //добавляем все классы из того же пакета
                foundClassImport.add(allClas);
            }
        }
        if (!foundClassImport.isEmpty()) {
            for (String foundClassImport1 : foundClassImport) {

                CompilationUnit cu = null;

                try {
                    cu = parse(foundClassImport1);
                } catch (ParseException ex) {
                    Logger.getLogger(ModuleWaitAndNotify.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ModuleWaitAndNotify.class.getName()).log(Level.SEVERE, null, ex);
                }

                //ищем вызов известного метода который содержит вызов wait()
                FoundMethodCall foundMethodCall = ParserMetods.getFoundMethodCall(cu, nameMethod);
                //получаем список аргументов методов
                ArrayList<List<Expression>> foundMethodCallArgs = foundMethodCall.getFoundMethodCallArgs();
                ArrayList<String> nameScopeObject = foundMethodCall.getNameScopeObject();

                //ищем создание класса в котором есть метод с вызовом wait
                //не проверяется вызов статических методов!!!!!!!!
                ArrayList<String> foundCreatedObjet = ParserMetods.getFoundCreatedObject(cu, splitPathToNameClass(pathClass)).getFoundCreatedObjet();

                //нет создания значит нет вызова вейт значит все хорошо
                if (foundCreatedObjet.isEmpty()) {
                    System.out.println("function return true");
                    return true;
                }
                for (int i = 0; i < foundCreatedObjet.size(); i++) {
                    for (int j = 0; j < nameScopeObject.size(); j++) {
                        //Проверяем вызван ли этот метод у класса из foundCreatedObject иначе может произойти ошибка
                        if (foundCreatedObjet.get(i).equals(nameScopeObject.get(j))) {
                            //получаем имя переменной на которой вызывается wait()
                            String nameObj = foundMethodCallArgs.get(i).get(index).toString();

                            return rec(cu, nameObj);
                            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                            //TODO Если ничего не найдено надо проверить отправку в конструкторы
                            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        }
                    }
                }
            }
        }
        System.out.println("function return true");
        //нет подключений нет вызова все хорошо (foundClassImport.isEmpty())
        return true;
    }
//

    private <T> boolean rec(T cu, String nameObj) {
        //найти вызов notifay в найденом методе у данной переменной
        if (!foundNotify(cu, nameObj)) {
            //поиск отправки объекта в другие методы
            FoundMethodsCallByParameterValue byParameterValue = new FoundMethodsCallByParameterValue();
            byParameterValue.findMethodsByParameterValue(cu, nameObj);
            ArrayList<String> foundMethod = byParameterValue.getFoundMethod();
            ArrayList<String> foundObject = byParameterValue.getFoundObject();
            ArrayList<Integer> idx = byParameterValue.getIdx();

            if (foundObject.size() == 0) {
                System.out.println("rec return false");
                return false;
            } else {

                //если findTypeObjectInfiles останется false
                //значит нет вызова not значит вернем false
                boolean findTypeObjectInfiles = false;

                //у каждого объекта ищем тип
                for (int k = 0; k < foundObject.size(); k++) {
                    String nameObject = foundObject.get(k);

                    //нашли тип или имя класса 
                    ArrayList<String> foundTypeObject = ParserMetods.getFoundInit((BlockStmt)cu, nameObject).getFoundTypeObject();
                    if (foundTypeObject.size() > 1) {
                        System.out.println("что то не так не должно быть больше 1 типа");
                    }

                    //находим исходники класса объявленной переменной
                    for (String path : files) {
                        String sNameClass = splitPathToNameClass(path);
                        if (foundTypeObject.get(0).equals(sNameClass)) {
                            findTypeObjectInfiles = true;
                            CompilationUnit cu2 = null;
                            try {
                                cu2 = parse(path);
                            } catch (ParseException ex) {
                                Logger.getLogger(ModuleWaitAndNotify.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(ModuleWaitAndNotify.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            //в классе ищем метод
                            //найти метод из foundMethod
                            for (MethodDeclaration md : ParserMetods.getFoundMethod(cu2, foundMethod.get(k)).getMethodDeclaration()) {

                                //внутри каждого метода ищем вызов Notify()
                                //ищем передачу в другие методы данного объекта
                                BlockStmt body = md.getBody();
                                //получаем имя объекта в параметрах метода на котором должен быть вызван Notify
                                String parameterName = md.getParameters().get(idx.get(k)).getId().toString();

                                return rec(body, parameterName);
                                //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                                //TODO Если ничего не найдено надо проверить отправку в конструкторы
                                //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                            }
                        }
                    }
                    //если в цикле ничего не найдено то ошибка
                    if (!findTypeObjectInfiles) {
                        System.out.println("rec return false");
                        return false;
                    }
                }
            }
        }
        System.out.println("rec return true");
        return true;
    }

//    /*
//    * Возвращает все найденные методы в переданном участке кода
//     */
//    private ArrayList<MethodDeclaration> findAllMethods(CompilationUnit cu) {
//        FoundMethod foundMethod = new FoundMethod();
//        foundMethod.visit(cu, null);
//        return foundMethod.getMethodDeclaration();
//    }

    /*
    * Возвращает все найденные методы c заданным значением параметра
     */
//    private void findMethodsByParameterValue(CompilationUnit cu, Expression expression) {
//        ArrayList<String> foundObject = new ArrayList<>();//найденые объекты
//        ArrayList<String> foundMethod = new ArrayList<>();//найденые методы
//
//        //ищем вызовы всех методов
//        parser.FoundMethodCall foundMethodCall = new parser.FoundMethodCall();
//        foundMethodCall.visit(cu, null);
//
//        //получаем список аргументов методов
//        ArrayList<List<Expression>> foundMethodCallArgs = foundMethodCall.getFoundMethodCallArgs();
//        //получаем список вызванных методов
//        ArrayList<String> nameMethodCall = foundMethodCall.getNameMethodCall();
//        //получаем список объектов у которых были вызваны методы
//        ArrayList<String> nameScopeObject = foundMethodCall.getNameScopeObject();
//
//        for (int i = 0; i < nameScopeObject.size(); i++) {
//            List<Expression> get = foundMethodCallArgs.get(i);
//            for (Expression expression1 : get) {
//                if (expression.toString().equals(expression1.toString())) {
//                    foundObject.add(nameScopeObject.get(i));
//                    foundMethod.add(nameMethodCall.get(i));
//                }
//            }
//        }
//    }
    private String splitPathToNameClass(String path) {
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

    private <T> boolean foundNotify(T cu, String nameObject) {
        //найти вызов notifay в данном коде у данной переменной
        FoundWaitAndNotify foundWaitAndNotify = ParserMetods.getFoundWaitAndNotify((CompilationUnit) cu);
        ArrayList<Expression> objectOnNotify = foundWaitAndNotify.getObjectOnNotify();
        ArrayList<Expression> objectOnNotifyAll = foundWaitAndNotify.getObjectOnNotifyAll();
        for (Expression e : objectOnNotify) {
            if (e.toString().equals(nameObject)) {
                return true;
            }
        }
        for (Expression e : objectOnNotifyAll) {
            if (e.toString().equals(nameObject)) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<String> foundClassImport(String nameClass) {
        ArrayList<String> filesTryImport = new ArrayList();
        //пройдемся по всем анализируемым файлам
        for (String file : files) {

            CompilationUnit cu = null;

            try {
                cu = parse(file);

            } catch (ParseException ex) {
                Logger.getLogger(ModuleWaitAndNotify.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (IOException ex) {
                Logger.getLogger(ModuleWaitAndNotify.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

            for (String importName : ParserMetods.getFoundImport(cu)) {
                if (importName.equals(nameClass)) {
                    filesTryImport.add(file);
                }
            }
        }
        return filesTryImport;
    }

    private static CompilationUnit parse(String path) throws FileNotFoundException, ParseException, IOException {
        FileInputStream in = new FileInputStream(path);

        CompilationUnit cu;
        try {
            cu = JavaParser.parse(in);
        } finally {
            in.close();
        }
        return cu;
    }

}