package com.light.sword.ylazy

import com.light.sword.ylazy.config.PhantomjsConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@EnableConfigurationProperties(value = Array(classOf[PhantomjsConfig]))
@ComponentScan(basePackages = Array("com.light.sword.ylazy"))
class AppConfig