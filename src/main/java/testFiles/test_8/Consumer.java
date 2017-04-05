package testFiles.test_8;

/**
 * Потребитель
 */
public class Consumer implements Runnable {

    private final Syn o;

    public Consumer(Syn o) {
        this.o = o;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (o) {
                try {
                    int x = o.getX();
                    if (x <= Syn.MIN_VALUE) {
                        System.out.println("Consumer wait");
                        o.wait();
                    } else {
                        x--;
                        o.setX(x);
                        System.out.println(x);
                        o.notifyAll();
                    }
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
}
