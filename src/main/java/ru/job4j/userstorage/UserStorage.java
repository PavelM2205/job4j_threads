package ru.job4j.userstorage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.function.Predicate;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final HashMap<Integer, User> store = new HashMap<>();

    public synchronized boolean add(User user) {
        return putWithCondition(usr -> !store.containsKey(usr.getId()), user);
    }

    public synchronized boolean update(User user) {
        return putWithCondition(usr -> store.containsKey(usr.getId()), user);
    }

    private synchronized boolean putWithCondition(Predicate<User> condition, User user) {
        boolean result = false;
        if (condition.test(user)) {
            store.put(user.getId(), user);
            result = true;
        }
        return result;
    }

    public synchronized boolean delete(User user) {
        boolean result = false;
        if (store.containsKey(user.getId())) {
            store.remove(user.getId());
            result = true;
        }
        return result;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        if (store.containsKey(fromId) && store.containsKey(toId)) {
            User from = store.get(fromId);
            User to = store.get(toId);
            if (from.getAmount() >= amount) {
                from.setAmount(from.getAmount() - amount);
                to.setAmount(to.getAmount() + amount);
                result = true;
            }
        }
        return result;
    }
}
