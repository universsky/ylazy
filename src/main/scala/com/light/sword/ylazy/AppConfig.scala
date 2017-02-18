package com.light.sword.ylazy

import com.light.sword.ylazy.config.{DomainConfig, PhantomjsConfig}
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@EnableConfigurationProperties(value = Array(classOf[PhantomjsConfig],classOf[DomainConfig]))
@ComponentScan(basePackages = Array("com.light.sword.ylazy"))
class AppConfig