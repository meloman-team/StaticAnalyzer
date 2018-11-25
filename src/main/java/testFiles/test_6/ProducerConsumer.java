package testFiles.test_6;

/**
 * Тест производители-потребители
 */
public class ProducerConsumer {

    public static void main(String[] args) {
        Syn o = new Syn(0);
        Consumer consumer = new Consumer(o);
        new Thread(consumer).start();
        new Thread(consumer).start();
    }
}