package module;

import model.ResultSharedResources;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Управляющий модуль
 */
public class ModuleControl {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Пожалуйста укажите директорию с проектом");
            return;
        }
        String dir = args[0];
        List<String> javaFile = findJavaFile(dir);
        ModuleWaitAndNotify moduleWaitAndNotify = new ModuleWaitAndNotify(javaFile);
        ModuleThreadAndSharedResources mtar = new ModuleThreadAndSharedResources(javaFile);
        ResultSharedResources resultSharedResources = mtar.main();
        printResult(resultSharedResources);

        ArrayList<String> errors = moduleWaitAndNotify.main(resultSharedResources);
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
        for (ResultSharedResources.SharedThread sharedThread : resultSharedResources.getSharedThread()) {
            System.out.println(sharedThread.getTypeThread() + " Количество запущеных потоков: " + sharedThread.getRunSharedThreads().size());
            System.out.println();
        }
        System.out.println("--------------------------");
        for (ResultSharedResources.SharedThread sharedThread : resultSharedResources.getSharedThread()) {
            for (ResultSharedResources.RunSharedThread runSharedThreads : sharedThread.getRunSharedThreads()) {
                System.out.println("Поток " + sharedThread.getTypeThread() + " №" + runSharedThreads.getNumberThread());
                for (ResultSharedResources.SharedResources sharedResources : runSharedThreads.getSharedResources()) {
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
