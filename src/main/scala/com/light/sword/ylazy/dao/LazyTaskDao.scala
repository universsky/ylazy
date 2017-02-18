package com.light.sword.ylazy.dao

import java.util.List

import com.light.sword.ylazy.entity.LazyTask
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

// JavaConversions
import scala.language.implicitConversions

trait LazyTaskDao extends CrudRepository[LazyTask, Integer] {
  def findAll(): List[LazyTask]

  def save(t: LazyTask): LazyTask

  def findOne(id: Integer): LazyTask

  @Query(value = "SELECT * FROM lazy_task where url like '%?1%'", nativeQuery = true)
  def listByUrl(url: String): List[LazyTask]

  @Query(value = "SELECT * FROM lazy_task where name like '%?1%'", nativeQuery = true)
  def listByName(name: String): List[LazyTask]


}