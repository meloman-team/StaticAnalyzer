import model.ResultSharedResources;
import module.ModuleThreadAndSharedResources;
import module.ModuleWaitAndNotify;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<String> files = new ArrayList();
        //TODO изменить на заполнение из параметров
        files.add("src\\main\\java\\testFiles\\Consumer.java");
        files.add("src\\main\\java\\testFiles\\Producer.java");
        files.add("src\\main\\java\\testFiles\\syn.java");
        files.add("src\\main\\java\\testFiles\\ProducerConsumer.java");

        ModuleWaitAndNotify moduleWaitAndNotify = new ModuleWaitAndNotify(files);
        ModuleThreadAndSharedResources mtar = new ModuleThreadAndSharedResources(files);
        try {
            ResultSharedResources resultSharedResources = mtar.main();

            if (resultSharedResources.getSharedThread().size() == 0)
                System.out.println("Разделяемых ресурсов не найдено");
            for (ResultSharedResources.SharedThread sharedThread : resultSharedResources.getSharedThread()) {
                for (ResultSharedResources.SharedResources sharedResources : sharedThread.getSharedResources()) {
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
                    System.out.println(sharedThread.getTypeThread() + "Количество запущеных потоков: " + sharedThread.getCountRunThread());
                    System.out.println();
                }
            }
            ArrayList<String> errors = moduleWaitAndNotify.main(resultSharedResources);
            if (errors.size() == 0) {
                System.out.println("Ошибок не обнаружено");
            } else {
                for (String error : errors) {
                    System.out.println(error);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}