package model;

import java.util.ArrayList;
import java.util.List;

/**
 * инстанс класса потока (запускаемый поток)
 */
public class RunSharedThread {
    private int numberThread;

    /**
     * Разделяемые ресурсы
     */
    private List<SharedResources> sharedResources = new ArrayList<>();

    public List<SharedResources> getSharedResources() {
        return sharedResources;
    }

    public void setSharedResources(List<SharedResources> sharedResources) {
        this.sharedResources = sharedResources;
    }

    public int getNumberThread() {
        return numberThread;
    }

    public void setNumberThread(int numberThread) {
        this.numberThread = numberThread;
    }
}
