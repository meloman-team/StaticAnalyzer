package model;

/**
 * Разделяемый ресурс
 */
public class SharedResources {
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
