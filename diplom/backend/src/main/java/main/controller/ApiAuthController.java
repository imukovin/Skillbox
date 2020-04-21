package main.controller;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

@RestController
public class ApiAuthController {

    @GetMapping("/api/auth/captcha")
    public JSONObject captcha() {
        Random random = new Random();
        String codeOnImage = "";
        String codeSecret = "";
        while (codeOnImage.length() < 5){
            int codeSymbol = 65 + random.nextInt(58);
            if (codeSymbol < 91 || codeSymbol > 97) {
                codeOnImage = codeOnImage.concat(Character.toString((char) codeSymbol));
            }
        }
        int width = 100, height = 26;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.OPAQUE);
        Graphics graphics = bufferedImage.createGraphics();
        graphics.setFont(new Font("Arial", Font.BOLD, 18));
        graphics.setColor(new Color(169, 169, 169));
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(new Color(255, 255, 255));
        graphics.drawString(codeOnImage, 18, 19);
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", os);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject json = new JSONObject();
        json.put("secret", codeSecret);
        json.put("image", "data:image/png;base64, " + Base64.getEncoder().encodeToString(os.toByteArray()));
        return json;
    }
}
