package ru.job4j.linked;

import java.io.*;

public final class Node<T extends Serializable> implements Serializable {
    private final Node<T> next;
    private final T value;

    public Node(Node<T> next, T value) {
        this.next = (Node<T>) copyObject(next);
        this.value = (T) copyObject(value);
    }

    public Node<T> getNext() {
        return (Node<T>) copyObject(next);
    }

    public T getValue() {
        return (T) copyObject(value);
    }

    private Object copyObject(Object object) {
        Object result = null;
        String store = "";
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream ous = new ObjectOutputStream(out);
        ByteArrayInputStream input = new ByteArrayInputStream(store.getBytes());
        ObjectInputStream ois = new ObjectInputStream(input)) {
            ous.writeObject(object);
            store = out.toString();
            result = ois.readObject();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return result;
    }
}
