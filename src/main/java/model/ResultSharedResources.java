package model;

import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Результаты модуля поиска разделяемых переменных
 */
public class ResultSharedResources {

    private List<SharedThread> result = new ArrayList<>();

    public List<SharedThread> getSharedThread() {
        return result;
    }

    @Nullable
    public SharedThread getSharedThread(String nameClassThread) {
        if (result.size() == 0) return null;
        for (SharedThread sharedThread : result) {
            if (sharedThread.getTypeThread().equals(nameClassThread)) return sharedThread;
        }
        return null;
    }

}
