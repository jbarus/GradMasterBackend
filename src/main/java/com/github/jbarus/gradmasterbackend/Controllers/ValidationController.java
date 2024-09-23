package com.github.jbarus.gradmasterbackend.Controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
public class ValidationController {

    @PostMapping(path = "/university-employee")
    public String handleUniversityEmployeeFile(@RequestParam("universityEmployees") MultipartFile file) {
        if(file.isEmpty()) {
            return "File is empty";
        }else{
            return "File is valid";
        }
    }
    @GetMapping(path = "/siemanko")
    public String helloWorld(){
        return "Hello World";
    }

}
