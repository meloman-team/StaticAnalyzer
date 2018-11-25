package model;

import java.util.ArrayList;
import java.util.List;

/**
 * разделяемый поток
 */
public class SharedThread {
    /**
     * Тип потока
     */
    private String typeThread;
    /**
     * Количество запускаемых потоков данного типа
     */
    private List<RunSharedThread> runSharedThreads = new ArrayList<>();

    public SharedThread(String typeThread) {
        this.typeThread = typeThread;
    }

    public String getTypeThread() {
        return typeThread;
    }

    public void setTypeThread(String typeThread) {
        this.typeThread = typeThread;
    }

    public List<RunSharedThread> getRunSharedThreads() {
        return runSharedThreads;
    }

    public void setRunSharedThreads(List<RunSharedThread> runSharedThreads) {
        this.runSharedThreads = runSharedThreads;
    }
}
