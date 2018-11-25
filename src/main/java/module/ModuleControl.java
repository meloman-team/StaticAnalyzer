package module;

import model.ResultSharedResources;
import model.RunSharedThread;
import model.SharedResources;
import model.SharedThread;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Управляющий модуль
 */
public class ModuleControl {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Пожалуйста, укажите директорию с проектом");
            return;
        }
        String dir = args[0];
        List<String> javaFile = findJavaFile(dir);
        ModuleWaitAndNotify moduleWaitAndNotify = new ModuleWaitAndNotify(javaFile);
        ModuleThreadAndSharedResources mtar = new ModuleThreadAndSharedResources(javaFile);
        ResultSharedResources resultSharedResources = mtar.main();
        printResult(resultSharedResources);

        ArrayList<String> errors = moduleWaitAndNotify.analysis(resultSharedResources);
        printResult(errors);

    }

    private static List<String> findJavaFile(String dir) {
        List<String> javaFiles = new ArrayList<>();
        File file = new File(dir);
        File[] files = file.listFiles();
        if(files != null)
        for (File f : files) {
            if (f.isDirectory()) javaFiles.addAll(findJavaFile(f.getPath()));
            else {
                String[] sp = f.getName().split("\\.");
                if (sp[sp.length - 1].equals("java")) javaFiles.add(f.getPath());
            }
        }
        return javaFiles;
    }

    private static void printResult(ResultSharedResources resultSharedResources){
        if (resultSharedResources.getSharedThread().size() == 0)
            System.out.println("Разделяемых ресурсов не найдено");
        for (SharedThread sharedThread : resultSharedResources.getSharedThread()) {
            System.out.println(sharedThread.getTypeThread() + " Количество запущеных потоков: " + sharedThread.getRunSharedThreads().size());
            System.out.println();
        }
        System.out.println("--------------------------");
        for (SharedThread sharedThread : resultSharedResources.getSharedThread()) {
            for (RunSharedThread runSharedThreads : sharedThread.getRunSharedThreads()) {
                System.out.println("Поток " + sharedThread.getTypeThread() + " №" + runSharedThreads.getNumberThread());
                for (SharedResources sharedResources : runSharedThreads.getSharedResources()) {
                    System.out.println();
                    System.out.println(
                            "Потоки " +
                                    sharedThread.getTypeThread() +
                                    " и " +
                                    sharedResources.getTypeThread() +
                                    " разделяют переменную " +
                                    sharedResources.getName()
                    );
                    System.out.println("У класса " +
                            sharedThread.getTypeThread() +
                            " Индекс разделяемого параметра " +
                            sharedResources.getIndex()
                    );
                    System.out.println();
                }
            }
            System.out.println("--------------------------");
        }
    }

    private static void printResult(ArrayList<String> errors){
        if (errors.size() == 0) {
            System.out.println("Ошибок не обнаружено");
        } else {
            for (String error : errors) {
                System.out.println(error);
            }
        }
    }
}
