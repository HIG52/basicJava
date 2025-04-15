package com.example.basicsamplesite.presentation;

import com.example.basicsamplesite.presentation.dto.MainResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/main")
    public MainResponse home() {
        return new MainResponse("Hello World");
    }

}
