package testFiles.test_4;

/**
 * Буфер (разделяемый ресурс)
 */
public class Syn {

    public static final int MAX_VALUE = 3;
    public static final int MIN_VALUE = 0;
    private volatile int x;

    public Syn(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

}
