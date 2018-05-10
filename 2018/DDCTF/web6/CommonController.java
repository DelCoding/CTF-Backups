package com.eliteams.quick4j.web.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonController
{
  @RequestMapping({"index"})
  public String index(HttpServletRequest request)
  {
    return "index";
  }
}
