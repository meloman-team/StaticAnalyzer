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

    /**
     * разделяемый поток
     */
    public static class SharedThread {
        /**
         * Тип потока
         */
        private String typeThread;
        /**
         * Количество запускаемых потоков данного типа
         */
        private List<RunSharedThread> runSharedThreads = new ArrayList<>();

        public SharedThread (String typeThread){
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

    /**
     * инстанс класса потока (запускаемый поток)
     */
    public static class RunSharedThread {
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

    public static class SharedResources {
        /**
         * Имя разделяемой переменной(не в конструкторе)
         */
        private String name;
        /**
         * Индекс разделяемой переменной в конструкторе класса
         */
        private Integer index;

        /**
         * Тип потока c которым разделяеться переменная
         */
        private String typeThread;

        public SharedResources(String name, Integer index, String typeThread) {
            this.name = name;
            this.index = index;
            this.typeThread = typeThread;
        }

        public String getName() {
            return name;
        }

        public int getIndex() {
            return index;
        }

        public String getTypeThread() {
            return typeThread;
        }
    }
}
