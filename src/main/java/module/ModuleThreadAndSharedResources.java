package module;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.Expression;
import utils.ParserMetods;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * модель ищет создание потоков и разделяемые ресурсы
 */
public class ModuleThreadAndSharedResources {

    //список проверяемых файлов
    ArrayList<String> files = new ArrayList();

    public ModuleThreadAndSharedResources() {
        //TODO изменить на заполнение из параметров
        files.add("C:\\Users\\sbt-gorlovskiy-ia\\Desktop\\Д\\netBeans\\test\\Reader.java");
        files.add("C:\\Users\\sbt-gorlovskiy-ia\\Desktop\\Д\\netBeans\\test\\Writer.java");
        files.add("C:\\Users\\sbt-gorlovskiy-ia\\Desktop\\Д\\netBeans\\test\\syn.java");
        files.add("C:\\Users\\sbt-gorlovskiy-ia\\Desktop\\Д\\netBeans\\test\\ReaderWriter.java");
    }

    public ModuleThreadAndSharedResources(ArrayList<String> files) {
        this.files = files;
    }

    public void main() throws IOException, ParseException {
        for (String file : files) {

            CompilationUnit cu = parse(file);

            //разделить файл на блоки видимости в которых производить поиск создания потоков и передачи в них параметров
            //Поиск создания потока и параметров потока
            ArrayList<List<Expression>> threadArgs = ParserMetods.getFoundCreatedNewObject(cu, "Thread").getFoundConstructorArgs();//находит - "new  Thread(writer)"
            List<List<String>> classArgs = getClassArgs(cu, threadArgs);
            for (int i = 0; i < classArgs.size(); i++) {
                for (int j = 0; j < classArgs.size(); j++) {
                    if (i == j) continue;
                    if(classArgs.get(i).equals(classArgs.get(j))){
                        System.out.println("Найдена разделяемая переменная " + classArgs.get(i));//TODO добавить тип или номер потока или имя класса отправленного в поток
                    }
                }
            }

            //запомнить в каких файлах создавались потоки
            //в этих же файлах найти создание других потоков или определить откуда взялись параметры для потока
            //проверить заполнение класса который передали в поток(конструктор и все вызываемые методы у класса с параметрами)

            //найти файлы классов которые передавались в параметры потока
            //в этих классах посмотреть как используются конструкторы и методы которые вызывались выше(найти разделяемые переменные)
        }
    }

    /**
     * Метод вернет список переменных переданные в консрукторы классов которые были переданы в потоки
     *
     * @param threadArgs Список классов переданных в аргументы потоков
     * @return
     */
    private static List<List<String>> getClassArgs(CompilationUnit cu, List<List<Expression>> threadArgs) {
        List<List<String>> result = null;
        for (List<Expression> threadArg : threadArgs) {
            if (threadArg.size() > 1) {
                System.out.println("Что то пошло не так. Thread не может иметь больше 1 параметра");
                continue;
            }
            for (Expression expression : threadArg) {
                ArrayList<Expression> objectInit = ParserMetods.getFoundInit(cu, expression).getObjectInit();//чем инициализ. переменные из конструктора
                if (objectInit.size() > 1) {
                    System.out.println("Найдено больше одной инициализации. На данный момент приложение не может обработать такую ситуацию.");
                    continue;
                }
                List<Node> childrenNodes = objectInit.get(0).getChildrenNodes();
                if (childrenNodes == null) continue;
                List<String> strings = new ArrayList<>();
                for (int i = 1; i < childrenNodes.size(); i++) {
                    strings.add(childrenNodes.get(i).toString());
                }
                result.add(strings);
            }
        }
        return result;
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
