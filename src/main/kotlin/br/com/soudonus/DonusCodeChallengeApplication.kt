package br.com.soudonus

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
@OpenAPIDefinition(info = Info(title = "Donus Code Challenge", version = "1.0.0", description = "Donus Code Challenge API Documentation"))
class DonusCodeChallengeApplication

fun main(args: Array<String>) {
    runApplication<DonusCodeChallengeApplication>(*args)
}