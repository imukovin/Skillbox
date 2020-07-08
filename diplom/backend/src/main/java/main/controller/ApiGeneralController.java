package main.controller;

import main.model.InitialObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiGeneralController {
    @Autowired
    private InitialObject initialObject;

    @GetMapping("/api/init")
    public InitialObject init() {
        return initialObject;
    }
}
