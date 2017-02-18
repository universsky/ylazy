package com.light.sword.ylazy.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod}
import org.springframework.web.servlet.ModelAndView

@Controller
class MainController {
  @RequestMapping(value = { Array("/main.do","/","/index","/home") }, method = Array(RequestMethod.GET))
  def about() = new ModelAndView("ylazy/main")
}
