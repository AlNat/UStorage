package dev.alnat.ustorage.web.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by @author AlNat on 26.01.2020.
 * Licensed by Apache License, Version 2.0
 */
@Controller
public class BasicUserController {

    /**
     * Корневая страница
     *
     * @return редирект на страницу с логином
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String rootPage() {
        return "redirect:/login";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        ModelMap model) {
        if (error != null) {
            model.addAttribute("error", error);
        }
        if (logout != null) {
            model.addAttribute("logout", logout);
        }

        return "login";
    }

}
