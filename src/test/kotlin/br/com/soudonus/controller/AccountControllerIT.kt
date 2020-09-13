package br.com.soudonus.controller

import br.com.soudonus.configuration.BaseIntegrationTest
import br.com.soudonus.configuration.ResourceDataMapper
import br.com.soudonus.model.dto.account.AccountCreateDTO
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import java.math.BigDecimal

class AccountControllerIT : BaseIntegrationTest() {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    companion object {
        private const val API_PATH = "/v1/accounts"
        private const val PATH_MESSAGE = "$.message"
        private val VALID_ACCOUNT_CREATE = ResourceDataMapper.getFrom("account/valid.json", AccountCreateDTO::class.java) as AccountCreateDTO
        private val VALID_ACCOUNT_ALREADY_EXISTS = ResourceDataMapper.getFromAsText("account/account-already-exists.json")
        private val INVALID_ACCOUNT_CPF_CREATE = ResourceDataMapper.getFromAsText("account/invalid-cpf.json")
        private val INVALID_ACCOUNT_BLANK_NAME_CREATE = ResourceDataMapper.getFromAsText("account/invalid-blank-name.json")
    }

    @Test
    fun `given an invalid cpf of CreateAccountDTO should not create account`() {
        this.webTestClient.post()
                .uri(API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(INVALID_ACCOUNT_CPF_CREATE)
                .exchange()
                .expectStatus().isBadRequest
                .expectBody()
                .jsonPath(PATH_MESSAGE).isEqualTo("cpf has invalid value '12345678901'")
    }

    @Test
    fun `given an blank name of CreateAccountDTO should not create account`() {
        this.webTestClient.post()
                .uri(API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(INVALID_ACCOUNT_BLANK_NAME_CREATE)
                .exchange()
                .expectStatus().isBadRequest
                .expectBody()
                .jsonPath(PATH_MESSAGE).isEqualTo("name has invalid value ''")
    }

    @Test
    fun `given an account already exists of CreateAccountDTO should not create account`() {
        this.webTestClient.post()
                .uri(API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(VALID_ACCOUNT_ALREADY_EXISTS)
                .exchange()
                .expectStatus().is4xxClientError
                .expectBody()
                .jsonPath(PATH_MESSAGE).isEqualTo("Account already exists with this CPF")
    }

    @Test
    fun `given an valid account of CreateAccountDTO should create account`() {
        this.webTestClient.post()
                .uri(API_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(VALID_ACCOUNT_CREATE)
                .exchange()
                .expectStatus().isCreated
                .expectBody()
                .jsonPath("$.id").isNotEmpty
                .jsonPath("$.name").isEqualTo(VALID_ACCOUNT_CREATE.name)
                .jsonPath("$.cpf").isEqualTo(VALID_ACCOUNT_CREATE.cpf)
                .jsonPath("$.balance").isEqualTo(BigDecimal.ZERO)
                .jsonPath("$.createdAt").isNotEmpty
                .jsonPath("$.updatedAt").isNotEmpty
    }

}