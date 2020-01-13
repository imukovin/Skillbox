package main;

import main.model.Task;
import main.model.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ListController {
    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/list/")
    public List<Task> list() {
        Iterable<Task> iterable = taskRepository.findAll();
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task task : iterable) {
            tasks.add(task);
        }
        return tasks;
    }

    @PostMapping("/list/{id}")
    public void del(@PathVariable Integer id) {
        taskRepository.deleteById(id);
    }

    @PostMapping("/listCompl/{id}")
    public void markCompleted(@PathVariable Integer id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            Task t = task.get();
            t.setStatus(true);
            taskRepository.save(t);
        }
    }

    @PostMapping("/list/")
    public int add(Task task) {
        Task t = taskRepository.save(task);
        return t.getId();
    }
}