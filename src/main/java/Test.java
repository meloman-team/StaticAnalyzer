import model.ResultSharedResources;
import module.ModuleThreadAndSharedResources;
import module.ModuleWaitAndNotify;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public  class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Тест №1 успешный
        System.out.println("Тест №1");
        System.out.println();

        ArrayList<String> files = new ArrayList();
        files.add("src\\main\\java\\testFiles\\test_1\\Consumer.java");
        files.add("src\\main\\java\\testFiles\\test_1\\Producer.java");
        files.add("src\\main\\java\\testFiles\\test_1\\syn.java");
        files.add("src\\main\\java\\testFiles\\test_1\\ProducerConsumer.java");

        ModuleWaitAndNotify moduleWaitAndNotify = new ModuleWaitAndNotify(files);
        ModuleThreadAndSharedResources mtar = new ModuleThreadAndSharedResources(files);
        try {
            ResultSharedResources resultSharedResources = mtar.main();
            printResult(resultSharedResources);

            ArrayList<String> errors = moduleWaitAndNotify.main(resultSharedResources);
            printResult(errors);

        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Тест №2 успешный
        System.out.println();
        System.out.println("Тест №2");
        System.out.println();

        files = new ArrayList();
        files.add("src\\main\\java\\testFiles\\test_2\\Consumer.java");
        files.add("src\\main\\java\\testFiles\\test_2\\Producer.java");
        files.add("src\\main\\java\\testFiles\\test_2\\syn.java");
        files.add("src\\main\\java\\testFiles\\test_2\\ProducerConsumer.java");

        moduleWaitAndNotify = new ModuleWaitAndNotify(files);
        mtar = new ModuleThreadAndSharedResources(files);
        try {
            ResultSharedResources resultSharedResources = mtar.main();
            printResult(resultSharedResources);

            ArrayList<String> errors = moduleWaitAndNotify.main(resultSharedResources);
            printResult(errors);

        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Тест №3 успешный
        System.out.println();
        System.out.println("Тест №3");
        System.out.println();

        files = new ArrayList();
        files.add("src\\main\\java\\testFiles\\test_3\\Consumer.java");
        files.add("src\\main\\java\\testFiles\\test_3\\Producer.java");
        files.add("src\\main\\java\\testFiles\\test_3\\syn.java");
        files.add("src\\main\\java\\testFiles\\test_3\\ProducerConsumer.java");

        moduleWaitAndNotify = new ModuleWaitAndNotify(files);
        mtar = new ModuleThreadAndSharedResources(files);
        try {
            ResultSharedResources resultSharedResources = mtar.main();
            printResult(resultSharedResources);

            ArrayList<String> errors = moduleWaitAndNotify.main(resultSharedResources);
            printResult(errors);

        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Тест №4 успешный
        System.out.println();
        System.out.println("Тест №4");
        System.out.println();

        files = new ArrayList();
        files.add("src\\main\\java\\testFiles\\test_4\\Consumer.java");
        files.add("src\\main\\java\\testFiles\\test_4\\Producer.java");
        files.add("src\\main\\java\\testFiles\\test_4\\syn.java");
        files.add("src\\main\\java\\testFiles\\test_4\\ProducerConsumer.java");

        moduleWaitAndNotify = new ModuleWaitAndNotify(files);
        mtar = new ModuleThreadAndSharedResources(files);
        try {
            ResultSharedResources resultSharedResources = mtar.main();
            printResult(resultSharedResources);

            ArrayList<String> errors = moduleWaitAndNotify.main(resultSharedResources);
            printResult(errors);

        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Тест №5 не успешный
        //ложное срабатывание из за не полного прослеживания присвоения объектов синхронизации
        System.out.println();
        System.out.println("Тест №5");
        System.out.println();

        files = new ArrayList();
        files.add("src\\main\\java\\testFiles\\test_5\\Consumer.java");
        files.add("src\\main\\java\\testFiles\\test_5\\Producer.java");
        files.add("src\\main\\java\\testFiles\\test_5\\syn.java");
        files.add("src\\main\\java\\testFiles\\test_5\\ProducerConsumer.java");

        moduleWaitAndNotify = new ModuleWaitAndNotify(files);
        mtar = new ModuleThreadAndSharedResources(files);
        try {
            ResultSharedResources resultSharedResources = mtar.main();
            printResult(resultSharedResources);

            ArrayList<String> errors = moduleWaitAndNotify.main(resultSharedResources);
            printResult(errors);

        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Тест №6 не успешный
        // ложное срабатывание на класс Producer поскольку его поток не запускался в программе TODO добавить проверку на запуск класса в потоке
        // не сработало предупреждение что класс Consumer не может пробудить поток (проблема с анализом логики программы)
        System.out.println();
        System.out.println("Тест №6");
        System.out.println();

        files = new ArrayList();
        files.add("src\\main\\java\\testFiles\\test_6\\Consumer.java");
        files.add("src\\main\\java\\testFiles\\test_6\\Producer.java");
        files.add("src\\main\\java\\testFiles\\test_6\\syn.java");
        files.add("src\\main\\java\\testFiles\\test_6\\ProducerConsumer.java");

        moduleWaitAndNotify = new ModuleWaitAndNotify(files);
        mtar = new ModuleThreadAndSharedResources(files);
        try {
            ResultSharedResources resultSharedResources = mtar.main();
            printResult(resultSharedResources);

            ArrayList<String> errors = moduleWaitAndNotify.main(resultSharedResources);
            printResult(errors);

        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Тест №7 успешный
        System.out.println();
        System.out.println("Тест №7");
        System.out.println();

        files = new ArrayList();
        files.add("src\\main\\java\\testFiles\\test_7\\Consumer.java");
        files.add("src\\main\\java\\testFiles\\test_7\\Producer.java");
        files.add("src\\main\\java\\testFiles\\test_7\\syn.java");
        files.add("src\\main\\java\\testFiles\\test_7\\ProducerConsumer.java");
        files.add("src\\main\\java\\testFiles\\test_7\\NotifyClass.java");

        moduleWaitAndNotify = new ModuleWaitAndNotify(files);
        mtar = new ModuleThreadAndSharedResources(files);
        try {
            ResultSharedResources resultSharedResources = mtar.main();
            printResult(resultSharedResources);

            ArrayList<String> errors = moduleWaitAndNotify.main(resultSharedResources);
            printResult(errors);

        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Тест №8 успешный
        System.out.println();
        System.out.println("Тест №8");
        System.out.println();

        files = new ArrayList();
        files.add("src\\main\\java\\testFiles\\test_8\\Consumer.java");
        files.add("src\\main\\java\\testFiles\\test_8\\Producer.java");
        files.add("src\\main\\java\\testFiles\\test_8\\syn.java");
        files.add("src\\main\\java\\testFiles\\test_8\\ProducerConsumer.java");

        moduleWaitAndNotify = new ModuleWaitAndNotify(files);
        mtar = new ModuleThreadAndSharedResources(files);
        try {
            ResultSharedResources resultSharedResources = mtar.main();
            printResult(resultSharedResources);

            ArrayList<String> errors = moduleWaitAndNotify.main(resultSharedResources);
            printResult(errors);

        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }

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