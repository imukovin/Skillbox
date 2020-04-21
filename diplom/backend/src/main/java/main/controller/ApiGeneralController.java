package main.controller;

import com.fasterxml.jackson.annotation.JsonAlias;
import main.constants.Consts;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiGeneralController {

    @GetMapping("/api/init")
    public JSONObject init() {
        JSONObject json = new JSONObject();
        json.put("title", Consts.TITLE);
        json.put("subtitle", Consts.SUBTITLE);
        json.put("phone", Consts.PHONE);
        json.put("email", Consts.EMAIL);
        json.put("copyright", Consts.COPYRIGHT);
        json.put("copyrightFrom", Consts.COPYRIGHT_FROM);
        return json;
    }
}
