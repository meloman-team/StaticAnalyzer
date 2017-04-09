package testFiles.test_8;

/**
 * Тест производители-потребители
 */
public class ProducerConsumer {

    public static void main(String[] args) {
        Syn o = new Syn(0);
        Producer producer = new Producer(o);
        Consumer consumer = new Consumer(o);
        for (int i = 0; i < 10; i++) {
            new Thread(consumer).start();
            new Thread(producer).start();
        }
    }
}
