package com.eliteams.quick4j.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/page"})
public class PageController
{
  @RequestMapping({"/login"})
  public String login()
  {
    return "login";
  }
  
  @RequestMapping({"/dashboard"})
  public String dashboard()
  {
    return "dashboard";
  }
  
  @RequestMapping({"/404"})
  public String error404()
  {
    return "404";
  }
  
  @RequestMapping({"/401"})
  public String error401()
  {
    return "401";
  }
  
  @RequestMapping({"/500"})
  public String error500()
  {
    return "500";
  }
}
