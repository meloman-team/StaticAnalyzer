package testFiles.test_5;

/**
 * Тест производители-потребители
 */
public class ProducerConsumer {

    public static void main(String[] args) {
        Syn o = new Syn(0);
        Producer producer = new Producer(o);
        Syn o2 = new Syn(0);
        Consumer consumer = new Consumer(o2);
        new Thread(consumer).start();
        new Thread(producer).start();
    }
}
