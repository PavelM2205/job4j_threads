package ru.job4j.userstorage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final HashMap<Integer, User> store = new HashMap<>();

    public synchronized boolean add(User user) {
        return store.putIfAbsent(user.getId(), user) == null;
    }

    public synchronized boolean update(User user) {
        return store.replace(user.getId(), user) != null;
    }

    public synchronized boolean delete(User user) {
        return store.remove(user.getId()) != null;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        User from = store.get(fromId);
        User to = store.get(toId);
        if (from != null && to != null && from.getAmount() >= amount) {
            from.setAmount(from.getAmount() - amount);
            to.setAmount(to.getAmount() + amount);
            result = true;
        }
        return result;
    }
}
