package com.light.sword.ylazy.controller

import java.util.Date

import com.light.sword.ylazy.config.DomainConfig
import com.light.sword.ylazy.dao.LazyTaskDao
import com.light.sword.ylazy.engine.PhantomjsExecutor
import com.light.sword.ylazy.entity.LazyTask
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ui.Model
import org.springframework.web.bind.annotation._
import org.springframework.web.servlet.ModelAndView

@RestController
class LazyTaskController @Autowired()(val lazyTaskDao: LazyTaskDao,
                                      val phantomjsExecutor: PhantomjsExecutor,
                                      val domainConfig: DomainConfig) {

  @RequestMapping(value = {
    Array("/newTask.do")
  })
  def newTask_do() = {
    new ModelAndView("ylazy/newTask")
  }

  @RequestMapping(value = {
    Array("/ylazy/newTask")
  }, method = Array(RequestMethod.POST))
  @ResponseBody
  def newTask(@ModelAttribute lazyTask: LazyTask) = {
    lazyTask.gmtCreate = new Date
    lazyTask.gmtModify = new Date
    lazyTask.executeTimes = 0
    lazyTask.state = -1
    lazyTaskDao.save(lazyTask)
  }

  @RequestMapping(value = {
    Array("/list.do")
  })
  def list_do(model: Model) = {
    model.addAttribute("lazyTaskList", lazyTaskDao.findAll())
    model.addAttribute("domainName", domainConfig.getDomainName)
    model.addAttribute("port", domainConfig.getPort)

    new ModelAndView("ylazy/list")
  }


  /**
    * 获取一条任务记录
    *
    * @param id
    * @return
    */
  @RequestMapping(value = {
    Array("/lazytask")
  }, method = Array(RequestMethod.GET))
  @ResponseBody
  def findOne(@RequestParam(value = "id") id: Integer) = lazyTaskDao.findOne(id)

  /**
    * 获取一条任务记录的返回json
    *
    * @param id
    * @return
    */
  @RequestMapping(value = {
    Array("/result/{id}")
  }, method = Array(RequestMethod.GET))
  @ResponseBody
  def findResultJson(@PathVariable(value = "id") id: Integer) = lazyTaskDao.findOne(id).resultJson


  /**
    * 执行任务
    * @param id
    * @return
    */
  @RequestMapping(value = {
    Array("/ylazy/runTask")
  }, method = Array(RequestMethod.GET))
  @ResponseBody
  def runTask(@RequestParam(value = "id") id: Integer) = {
    phantomjsExecutor.ylazyById(id)
  }


  @RequestMapping(value = {
    Array("/ylazy")
  }, method = Array(RequestMethod.GET))
  @ResponseBody
  def ylazy(@RequestParam(value = "url") url: String) = phantomjsExecutor.ylazy(url)


}
