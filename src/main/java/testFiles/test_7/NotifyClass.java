package testFiles.test_7;

public class NotifyClass implements Runnable {
    private final Syn o;

    public NotifyClass(Syn o) {
        this.o = o;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (o) {
                o.notifyAll();
            }
        }
    }
}