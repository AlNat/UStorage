package dev.alnat.ustorage.web.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by @author AlNat on 26.01.2020.
 * Licensed by Apache License, Version 2.0
 */
@Controller
@RequestMapping("/web/file")
public class FileController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexPage() {
        return "/file/index";
    }

}
