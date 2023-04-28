package com.codebootup.vehicle

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class Boot

fun main(args: Array<String>) {
    runApplication<Boot>(*args)
}