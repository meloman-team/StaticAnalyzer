package testFiles.test_4;

/**
 * Тест производители-потребители
 */
public class ProducerConsumer {

    public static void main(String[] args) {
        Syn o = new Syn(0);
        Producer producer = new Producer(o);
        Consumer consumer = new Consumer(o);
        new Thread(consumer).start();
        new Thread(producer).start();

    }
}
