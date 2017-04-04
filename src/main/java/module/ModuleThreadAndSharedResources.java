package module;

import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.Expression;
import model.ResultSharedResources;
import model.ThreadObject;
import utils.ParserMetods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * модуль ищет создание потоков и разделяемые ресурсы
 */
public class ModuleThreadAndSharedResources {

    //список проверяемых файлов
    private ArrayList<String> files = new ArrayList();

    public ModuleThreadAndSharedResources(ArrayList<String> files) {
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
                for (int j = i; j < classArgs.size(); j++) {// j = i для исключения дублей
                    ThreadObject thread1 = classArgs.get(i);
                    ThreadObject thread2 = classArgs.get(j);
                    if (thread1.equals(thread2)) continue;
                    List<String> thread1Parameters = thread1.getConstructorParameters();
                    List<String> thread2Parameters = thread2.getConstructorParameters();
                    for (int i2 = 0; i2 < thread1Parameters.size(); i2++) {
                        for (int j2 = 0; j2 < thread2Parameters.size(); j2++) {
                            String s1 = thread1Parameters.get(i2);
                            String s2 = thread2Parameters.get(j2);
                            if (s1.equals(s2)) {
                                result.addResult(thread1.getVariableType(), s1, i2, thread2.getVariableType());
                                result.addResult(thread2.getVariableType(), s2, j2, thread1.getVariableType());
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