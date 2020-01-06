package main;

import java.util.ArrayList;
import java.util.HashMap;

public class Storage {
    private static HashMap<Integer, Task> tasks = new HashMap<>();
    private static int currentId = 0;

    public static int addTaskToStorage(Task task) {
        currentId++;
        task.setId(currentId);
        tasks.put(currentId, task);
        return currentId;
    }

    public static ArrayList<Task> getList() {
        return new ArrayList<>(tasks.values());
    }

    public static void delTask(Integer id) {
        if (id != null) {
            tasks.remove(id);
        }
    }

    public static void markComplited(Integer id) {
        if (id != null) {
            tasks.get(id).setStatus(true);
        }
    }
}
