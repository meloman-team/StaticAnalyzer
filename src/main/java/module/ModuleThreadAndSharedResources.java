package module;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.Expression;
import model.*;
import utils.ParserMetods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * модуль ищет создание потоков и разделяемые ресурсы
 */
public class ModuleThreadAndSharedResources {

    //список проверяемых файлов
    private List<String> files;

    public ModuleThreadAndSharedResources(List<String> files) {
        this.files = files;
    }

    public ResultSharedResources main() throws IOException, ParseException {
        ResultSharedResources result = new ResultSharedResources();
        for (String file : files) {

            CompilationUnit cu = ParserMetods.parse(file);

            //разделить файл на блоки видимости в которых производить поиск создания потоков и передачи в них параметров
            //Поиск создания потока и параметров потока
            //TODO класс Thread может быть не java.lang.Thread а пользовательским классом данная ошибка не обрабатывается
            ArrayList<List<Expression>> threadArgs = ParserMetods.getFoundCreatedNewObject(cu, "Thread").getFoundConstructorArgs();//находит - "writer"
            if (threadArgs == null) continue;
            List<ThreadObject> classArgs = getClassArgs(cu, threadArgs);
            if (classArgs == null) continue;//TODO проверять не только конструкторы но и сеттеры
            for (int i = 0; i < classArgs.size(); i++) {
                ThreadObject thread1 = classArgs.get(i);
                SharedThread sharedThread = result.getSharedThread(thread1.getVariableType());
                if (sharedThread == null) {
                    sharedThread = new SharedThread(thread1.getVariableType());
                    result.getSharedThread().add(sharedThread);
                }
                RunSharedThread runSharedThread = new RunSharedThread();
                runSharedThread.setNumberThread(sharedThread.getRunSharedThreads().size() + 1);
                sharedThread.getRunSharedThreads().add(runSharedThread);
                for (int j = 0; j < classArgs.size(); j++) {
                    ThreadObject thread2 = classArgs.get(j);
                    if (i == j) continue;
                    List<String> thread1Parameters = thread1.getConstructorParameters();
                    List<String> thread2Parameters = thread2.getConstructorParameters();
                    for (int i2 = 0; i2 < thread1Parameters.size(); i2++) {
                        for (int j2 = 0; j2 < thread2Parameters.size(); j2++) {
                            String s1 = thread1Parameters.get(i2);
                            String s2 = thread2Parameters.get(j2);
                            if (s1.equals(s2)) {
                                runSharedThread.getSharedResources().add(
                                        new SharedResources(s1, i2, thread2.getVariableType())
                                );
                            }
                        }
                    }
                }
            }

            //TODO разделяемые переменные не обязательно являються объектами синхронизации
            //TODO не отслеживаеться путь создания и присвоения разделяемых переменных
            //TODO проверяеться только соответствие имени переменных передаваемых в конструктор (необходимо
            //TODO прослеживать полный путь создания и присваивания разделяемых переменных)
        }
        return result;
    }

    /**
     * Метод вернет список переменных переданные в консрукторы классов которые были переданы в потоки
     *
     * @param threadArgs Список классов переданных в аргументы потоков
     * @return
     */
    private static List<ThreadObject> getClassArgs(CompilationUnit cu, List<List<Expression>> threadArgs) {
        List<ThreadObject> result = new ArrayList<>();
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
                ThreadObject threadObject = new ThreadObject();
                threadObject.setVariableType(childrenNodes.get(0).toString());//Название Класса(потока)
                //TODO добавить поиск полного пути до класса для явного понимания какой класс из какого пакета.
                for (int i = 1; i < childrenNodes.size(); i++) {
                    threadObject.addConstructorParameters(childrenNodes.get(i).toString());
                }
                result.add(threadObject);
            }
        }
        return result;
    }

}