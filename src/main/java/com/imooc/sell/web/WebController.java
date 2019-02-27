package com.imooc.sell.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/web")
@Slf4j
public class WebController {

    @GetMapping("/save")
    public void save(@RequestParam("name")String name,
                     HttpSession session){
        log.info("name={}", name);
        session.setAttribute("name", name);
    }

    @GetMapping("/get")
    public String getName(HttpSession session){
        Object name = session.getAttribute("name");
        log.info("name={}", name);
        return (String) name;
    }
}
