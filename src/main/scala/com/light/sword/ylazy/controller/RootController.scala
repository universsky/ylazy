package com.light.sword.ylazy.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod}
import org.springframework.web.servlet.ModelAndView

/**
 * Created by jack on 5/26/16.
 */
@Controller
class RootController {

  @RequestMapping(value = { Array("/about") }, method = Array(RequestMethod.GET))
  def about() = new ModelAndView("/about")

  @RequestMapping(value = { Array("", "/") }, method = Array(RequestMethod.GET))
  def index() = new ModelAndView("/home")

}
