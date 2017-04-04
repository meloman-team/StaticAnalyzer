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
     * @param nameClassThread      Тип потока
     * @param nameSharedResources  Имя разделяемой переменной(не в конструкторе)
     * @param indexSharedResources Индекс разделяемой переменной в конструкторе класса (nameClassThread)
     * @param typeThread           Тип потока c которым разделяеться переменная
     */
    public void addResult(String nameClassThread, String nameSharedResources, int indexSharedResources, String typeThread) {
        SharedThread sharedThread = this.getSharedThread(nameClassThread);
        if (sharedThread == null) {
            List<SharedResources> sharedResources = new ArrayList<>();
            sharedResources.add(new SharedResources(nameSharedResources, indexSharedResources, typeThread));
            sharedThread = new SharedThread(nameClassThread);
            sharedThread.getSharedResources().addAll(sharedResources);
            sharedThread.setCountRunThread(1);
            result.add(sharedThread);
        } else {
            List<ResultSharedResources.SharedResources> sharedResources = sharedThread.getSharedResources();
            sharedResources.add(new SharedResources(nameSharedResources, indexSharedResources, typeThread));
            sharedThread.setCountRunThread(sharedThread.getCountRunThread() + 1);
        }
    }

    public class SharedThread {
        /**
         * Тип потока
         */
        private String typeThread;
        /**
         * Количество запускаемых потоков данного типа
         */
        private int countRunThread = 0;
        /**
         * Разделяемые ресурсы
         */
        private List<SharedResources> sharedResources = new ArrayList<>();

        public SharedThread (String typeThread){
            this.typeThread = typeThread;
        }

        public String getTypeThread() {
            return typeThread;
        }

        public void setTypeThread(String typeThread) {
            this.typeThread = typeThread;
        }

        public int getCountRunThread() {
            return countRunThread;
        }

        public void setCountRunThread(int countRunThread) {
            this.countRunThread = countRunThread;
        }

        public List<SharedResources> getSharedResources() {
            return sharedResources;
        }

        public void setSharedResources(List<SharedResources> sharedResources) {
            this.sharedResources = sharedResources;
        }
    }

    public class SharedResources {
        /**
         * Имя разделяемой переменной(не в конструкторе)
         */
        private String name;
        /**
         * Индекс разделяемой переменной в конструкторе класса
         */
        private int index;

        /**
         * Тип потока c которым разделяеться переменная
         */
        private String typeThread;

        SharedResources(String name, int index, String typeThread) {
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
