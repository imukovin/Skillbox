package main;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Storage {
    private static ConcurrentHashMap<Integer, Task> tasks = new ConcurrentHashMap<>();
    private static AtomicInteger currentId = new AtomicInteger(0);

    public static int addTaskToStorage(Task task) {
        task.setId(currentId.incrementAndGet());
        tasks.putIfAbsent(currentId.get(), task);
        return currentId.get();
    }

    public static ArrayList<Task> getList() {
        return new ArrayList<>(tasks.values());
    }

    public static void delTask(Integer id) {
        if (id != null) {
            if (tasks.get(id) != null) {
                tasks.remove(id);
            }
        }
    }

    public static void markCompleted(Integer id) {
        if (id != null) {
            if (tasks.get(id) != null) {
                tasks.get(id).setStatus(true);
            }
        }
    }
}
