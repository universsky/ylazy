package com.light.sword.ylazy.entity

import java.util.Date
import javax.persistence.{Entity, GeneratedValue, GenerationType, Id}

import scala.beans.BeanProperty
import scala.language.implicitConversions

@Entity
class LazyTask {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @BeanProperty
  var id: Integer = _

  @BeanProperty
  var url: String = _
  @BeanProperty
  var name: String = _

  //用例状态: -1未执行 0失败 1成功
  @BeanProperty
  var state: Integer = _

  @BeanProperty
  var owner: String = _

  @BeanProperty
  var resultJson: String = _

  @BeanProperty
  var executeTimes: Integer = _

  @BeanProperty
  var executor: Integer = _

  @BeanProperty
  var gmtCreate: Date = _

  @BeanProperty
  var gmtModify: Date = _



}