package com.light.sword.ylazy.controller

import com.light.sword.ylazy.engine.PhantomjsExecutor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, RequestParam, ResponseBody}

@Controller
class PhantomjsController @Autowired()(val phantomjsExecutor: PhantomjsExecutor) {
  @RequestMapping(value = {
    Array("/phantomenv")
  }, method = Array(RequestMethod.GET))
  @ResponseBody
  def phantomenv() = phantomjsExecutor.getPhantomJSEnv



}
