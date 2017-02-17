package com.light.sword.ylazy.dao

import java.util.List

import com.light.sword.ylazy.entity.HttpApi
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

import scala.language.implicitConversions

trait HttpApiDao extends CrudRepository[HttpApi, Integer] {
  def findAll(): List[HttpApi] // JavaConversions

  def save(t: HttpApi): HttpApi

  def findOne(id: Integer): HttpApi

  @Query(value = "SELECT * FROM http_api where http_suite_id = ?1", nativeQuery = true)
  def listByHttpSuiteId(id: Integer): List[HttpApi]

  @Query(value = "SELECT id FROM http_api where http_suite_id = ?1", nativeQuery = true)
  def listTestCaseId(httpSuiteId: Integer): List[Integer] // 隐式转换,直接用scala的List会报错:javax.persistence.NonUniqueResultException: result returns more than one elements] with root cause

  @Query(value = "SELECT * FROM http_api where name like %?1% ", nativeQuery = true) // like '%?%'
  def findByName(name: String): List[HttpApi]

  @Query(value = "select count(*) from http_api where http_suite_id = ?1 and state = 1", nativeQuery = true)
  def countPass(httpSuiteId: Integer): Int

  @Query(value = "select count(*) from http_api where http_suite_id = ?1 and state = 0", nativeQuery = true)
  def countFail(httpSuiteId: Integer): Int

}