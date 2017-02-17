package com.light.sword.ylazy.controller

import java.util
import java.util.Date

import org.springframework.web.bind.annotation.{RequestMapping, RestController}

/**
  * Created by jack on 16/6/24.
  *
  * 系统内部测试用
  */

@RestController
@RequestMapping(Array("/hello"))
class HelloController {

  @RequestMapping(Array("", "/"))
  def greeting() = {
    val now = new Date
    val content = "Hello, ! Now is: " + now
    val json = new util.HashMap[String, String]
    json.put("content", content)
    json
  }


}
