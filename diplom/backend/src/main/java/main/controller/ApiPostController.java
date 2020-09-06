package main.controller;

import main.dto.ResponsePostObject;
import main.dto.Settings;
import main.dto.Tag;
import main.services.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api")
public class ApiPostController {
    @Autowired
    private PostsService postsService;

    @GetMapping(value = "/post")
    public ResponsePostObject getPosts(@RequestParam(name = "offset") int offset,
                                       @RequestParam(name = "limit") int limit,
                                       @RequestParam(name = "mode") String mode) {

        return postsService.getPosts(offset, limit, mode);
    }

    @GetMapping(value = "/tag")
    public ArrayList<Tag> getTag() {
        ArrayList<Tag> list = new ArrayList<>();
        list.add(new Tag("Java", 1));
        return list;
    }

    @GetMapping(value = "/settings")
    public Settings getSetting() {
        return new Settings(false, true, true);
    }

    @GetMapping(value = "/auth/check")
    public String getCheck() {
        return "{'result': false}";
    }
}
