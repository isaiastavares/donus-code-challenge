package br.com.soudonus.configuration

import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.function.Supplier

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BaseIntegrationTest {

    companion object {
        private const val DONUS_CODE_CHALLENGE = "donus-code-challenge"

        @Container
        val container = PostgreSQLContainer<Nothing>("postgres:12").apply {
            withDatabaseName(DONUS_CODE_CHALLENGE)
            withUsername(DONUS_CODE_CHALLENGE)
            withPassword(DONUS_CODE_CHALLENGE)
            start()
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", container::getJdbcUrl)
            registry.add("spring.datasource.password", container::getPassword)
            registry.add("spring.datasource.username", container::getUsername)
            registry.add("spring.flyway.enabled", Supplier { true })
            registry.add("spring.flyway.locations", Supplier { "filesystem:src/main/resources/db/migration,filesystem:src/test/resources/db/migration" })
        }
    }

}