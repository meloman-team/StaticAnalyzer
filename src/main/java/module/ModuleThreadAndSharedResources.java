package module;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
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

            //Поиск создания потока и параметров потока
            ArrayList<List<Expression>> thread = ParserMetods.getFoundCreatedNewObject(cu, "Thread").getFoundCreatedNewObjectArgs();
            //запомнить в каких файлах создавались потоки
            //в этих же файлах найти создание других потоков или определить откуда взялись параметры для потока
            //проверить заполнение класса который передали в поток(конструктор и все вызываемые методы у класса с параметрами)

            //найти файлы классов которые передавались в параметры потока
            //в этих классах посмотреть как используются конструкторы и методы которые вызывались выше(найти разделяемые переменные)
        }
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
