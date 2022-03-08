package ru.academits.nekrasovgleb.singlylinkedlist;

public class listItem<T> {
    private T data;
    private listItem<T> next;

    public listItem(T data) {
        this.data = data;
    }

    public listItem(T data, listItem<T> next) {
        this.data = data;
        this.next = next;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public listItem<T> getNext() {
        return next;
    }

    public void setNext(listItem<T> next) {
        this.next = next;
    }
}
