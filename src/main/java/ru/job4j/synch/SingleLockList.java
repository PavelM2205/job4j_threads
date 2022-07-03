package ru.job4j.synch;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.io.*;
import java.util.Iterator;
import java.util.List;

@ThreadSafe
public class SingleLockList<T extends Serializable> implements Iterable<T> {
    @GuardedBy("this")
    private final List<T> list;

    public SingleLockList(List<T> list) {
        this.list = (List<T>) cloneObject(list);
    }

    private Object cloneObject(Object object) {
        Object result = null;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             ObjectOutputStream ous = new ObjectOutputStream(out)) {
            ous.writeObject(object);
            ous.flush();
            try (ByteArrayInputStream input = new ByteArrayInputStream(out.toByteArray());
                  ObjectInputStream ois = new ObjectInputStream(input)) {
                result = ois.readObject();
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        return result;
    }

    public synchronized void add(T value) {
        list.add((T) cloneObject(value));
    }

    public synchronized T get(int index) {
        return (T) cloneObject(list.get(index));
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return ((List<T>) cloneObject(list)).iterator();
    }
}
