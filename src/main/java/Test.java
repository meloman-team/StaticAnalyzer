import model.ResultSharedResources;
import module.ModuleControl;

import java.util.ArrayList;

public  class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        String[] strings = new String[1];

        // Тест №1 успешный

        strings[0] = "src\\main\\java\\testFiles\\test_1";
        System.out.println("Тест №1");
        System.out.println();

        ModuleControl.main(strings);

        // Тест №2 успешный

        strings[0] = "src\\main\\java\\testFiles\\test_2";
        System.out.println();
        System.out.println("Тест №2");
        System.out.println();

        ModuleControl.main(strings);

        // Тест №3 успешный

        strings[0] = "src\\main\\java\\testFiles\\test_3";
        System.out.println();
        System.out.println("Тест №3");
        System.out.println();

        ModuleControl.main(strings);

        // Тест №4 успешный

        strings[0] = "src\\main\\java\\testFiles\\test_4";
        System.out.println();
        System.out.println("Тест №4");
        System.out.println();

        ModuleControl.main(strings);

        // Тест №5 успешный

        strings[0] = "src\\main\\java\\testFiles\\test_5";
        System.out.println();
        System.out.println("Тест №5");
        System.out.println();

        ModuleControl.main(strings);

        // Тест №6 не успешный
        // ложное срабатывание на класс Producer поскольку его поток не запускался в программе TODO добавить проверку на запуск класса в потоке
        // не сработало предупреждение что класс Consumer не может пробудить поток (проблема с анализом логики программы)

        strings[0] = "src\\main\\java\\testFiles\\test_6";
        System.out.println();
        System.out.println("Тест №6");
        System.out.println();

        ModuleControl.main(strings);

        // Тест №7 успешный

        strings[0] = "src\\main\\java\\testFiles\\test_7";
        System.out.println();
        System.out.println("Тест №7");
        System.out.println();

        ModuleControl.main(strings);

        // Тест №8 почти успешный
        // неверно считает количество потоков при создании их в цикле


        strings[0] = "src\\main\\java\\testFiles\\test_8";
        System.out.println();
        System.out.println("Тест №8");
        System.out.println();

        ModuleControl.main(strings);

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