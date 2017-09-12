package com.apli.site.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("")
@Controller
public class pageController {

  @RequestMapping("/index")
  public ModelAndView index (){
    System.out.println("----------");
    return new ModelAndView("index");
  }
}
