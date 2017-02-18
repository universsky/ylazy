package com.light.sword.ylazy.controller

import com.light.sword.ylazy.dao.LazyTaskDao
import com.light.sword.ylazy.engine.PhantomjsExecutor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, RequestParam, ResponseBody}

@Controller
class LazyTaskController @Autowired()(val lazyTaskDao: LazyTaskDao) {

  @RequestMapping(value = {
    Array("/lazytask.do")
  }, method = Array(RequestMethod.GET))
  @ResponseBody
  def findOne(@RequestParam(value = "id") id:Integer) = lazyTaskDao.findOne(id)

  @RequestMapping(value = {
    Array("/result.do")
  }, method = Array(RequestMethod.GET))
  @ResponseBody
  def findResultJson(@RequestParam(value = "id") id:Integer) = lazyTaskDao.findOne(id).resultJson

}
