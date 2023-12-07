package com.example.templatespring.controller;

import com.example.templatespring.response.HelloDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
  @GetMapping("/api/text")
  public String helloText() {
    return "Hello with text!!";
  }

  @GetMapping("/api/echo/{myText}")
  public String mappingPath(@PathVariable("myText") String content) {
    return content;
  }

  @ResponseBody
  @GetMapping("/api/json")
  public HelloDTO helloJson() {
    return new HelloDTO(
        "Hello Title",
        "It's a contents!!",
        5
    );
  }
}
