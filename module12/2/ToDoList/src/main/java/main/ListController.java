package main;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ListController {
    @GetMapping("/list/")
    public List<Task> list() {
        return Storage.getList();
    }

    @GetMapping("/list/{id}")
    public void del(@PathVariable Integer id) {
        Storage.delTask(id);
    }

    @GetMapping("/listCompl/{id}")
    public void markCompleted(@PathVariable Integer id) {
        Storage.markCompleted(id);
    }

    @PostMapping("/list/")
    public int add(Task task) {
        return Storage.addTaskToStorage(task);
    }
}