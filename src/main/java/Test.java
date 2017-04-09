import module.ModuleControl;

public  class Test {

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

}