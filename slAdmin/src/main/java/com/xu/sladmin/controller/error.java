package com.xu.sladmin.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Name error
 * @Author 31671
 * @Date 2023-10-06 23:46
 */
@Controller
public class error implements ErrorController {
    @RequestMapping("/error")
    public String handleError() {
        // Provide your own custom error page or redirect logic here
        return "error"; // Assuming you have an "error.html" template
    }


}
