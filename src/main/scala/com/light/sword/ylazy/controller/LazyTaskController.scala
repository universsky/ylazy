package com.light.sword.ylazy.controller

import com.light.sword.ylazy.dao.LazyTaskDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation._

@Controller
class LazyTaskController @Autowired()(val lazyTaskDao: LazyTaskDao) {

  @RequestMapping(value = {
    Array("/lazytask.do")
  }, method = Array(RequestMethod.GET))
  @ResponseBody
  def findOne(@RequestParam(value = "id") id: Integer) = lazyTaskDao.findOne(id)

  @RequestMapping(value = {
    Array("/result/{id}")
  }, method = Array(RequestMethod.GET))
  @ResponseBody
  def findResultJson(@PathVariable(value = "id") id: Integer) = lazyTaskDao.findOne(id).resultJson

}
