package main.controller;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DefaultController {
    @GetMapping("/")
    public ModelAndView index(Model model) {

        return new ModelAndView("index");
    }
}
