package testFiles;

/**
 * Производитель
 */
public class Producer implements Runnable {

    private final Syn o;

    public Producer(Syn o) {
        this.o = o;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (o) {
                try {
                    int x = o.getX();
                    if (x >= Syn.MAX_VALUE) {
                        System.out.println("Producer wait");
                        o.wait();
                    } else {
                        x++;
                        o.setX(x);
                        System.out.println(x);
//                        o.notifyAll();
                    }
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
}