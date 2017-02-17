package com.light.sword.ylazy.controller

import com.light.sword.ylazy.engine.OkHttp
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation._

/**
  * Created by jack on 2017/2/18.
  */
@RestController
class HttpApiController {

  @RequestMapping(value = Array("httpapi.do"),
    method = Array(RequestMethod.GET))
  @ResponseBody
  def debugTest(@RequestParam(value = "url") url: String,
                @RequestParam(value = "method") method: String,
                @RequestParam(value = "paramJson") paramJson: String) = {OkHttp.run(url, method, paramJson)}
}
