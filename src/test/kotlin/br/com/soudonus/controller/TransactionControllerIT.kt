package br.com.soudonus.controller

import br.com.soudonus.configuration.BaseIntegrationTest
import br.com.soudonus.configuration.ResourceDataMapper
import br.com.soudonus.model.dto.deposit.DepositCreateDTO
import br.com.soudonus.model.dto.transfer.TransferCreateDTO
import br.com.soudonus.model.dto.withdraw.WithdrawCreateDTO
import br.com.soudonus.model.enum.TransactionType
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import java.math.BigDecimal

class TransactionControllerIT : BaseIntegrationTest() {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    companion object {
        private const val API_PATH = "/v1/transactions"
        private const val API_PATH_DEPOSIT = "$API_PATH/deposit"
        private const val API_PATH_WITHDRAW = "$API_PATH/withdraw"
        private const val API_PATH_TRANSFER = "$API_PATH/transfer"
        private const val PATH_MESSAGE = "$.message"
        private val VALID_DEPOSIT = ResourceDataMapper.getFrom("transactions/valid_deposit.json", DepositCreateDTO::class.java) as DepositCreateDTO
        private val VALID_WITHDRAW = ResourceDataMapper.getFrom("transactions/valid_withdraw.json", WithdrawCreateDTO::class.java) as WithdrawCreateDTO
        private val VALID_TRANSFER = ResourceDataMapper.getFrom("transactions/valid_transfer.json", TransferCreateDTO::class.java) as TransferCreateDTO
        private val WITHDRAW_WITH_INSUFFICIENT_BALANCE = ResourceDataMapper.getFrom("transactions/withdraw_with_insufficient_balance.json", WithdrawCreateDTO::class.java) as WithdrawCreateDTO
        private val INVALID_TRANSFER_TO_ACCOUNT_NONEXISTENT = ResourceDataMapper.getFrom("transactions/invalid_transfer_to_account_nonexistent.json", TransferCreateDTO::class.java) as TransferCreateDTO
    }

    @Test
    fun `given an valid deposit of 20 reais should return success with cashback`() {
        this.webTestClient.post()
                .uri(API_PATH_DEPOSIT)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(VALID_DEPOSIT)
                .exchange()
                .expectStatus().isCreated
                .expectBody()
                .jsonPath("$.id").isNotEmpty
                .jsonPath("$.accountId").isEqualTo(VALID_DEPOSIT.accountId.toString())
                .jsonPath("$.type").isEqualTo(TransactionType.DEPOSIT.name)
                .jsonPath("$.amount").isEqualTo(VALID_DEPOSIT.amount)
                .jsonPath("$.balanceFrom").isEqualTo(BigDecimal.ZERO.toDouble())
                .jsonPath("$.balanceTo").isEqualTo(20.1)
                .jsonPath("$.createdAt").isNotEmpty
    }

    @Test
    fun `given an valid withdraw of 19,9 reais should return success with tax`() {
        this.webTestClient.post()
                .uri(API_PATH_WITHDRAW)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(VALID_WITHDRAW)
                .exchange()
                .expectStatus().isCreated
                .expectBody()
                .jsonPath("$.id").isNotEmpty
                .jsonPath("$.accountId").isEqualTo(VALID_WITHDRAW.accountId.toString())
                .jsonPath("$.type").isEqualTo(TransactionType.WITHDRAW.name)
                .jsonPath("$.amount").isEqualTo(VALID_WITHDRAW.amount)
                .jsonPath("$.balanceFrom").isEqualTo(20.1)
                .jsonPath("$.balanceTo").isEqualTo(BigDecimal.ZERO.toDouble())
                .jsonPath("$.createdAt").isNotEmpty
    }

    @Test
    fun `given an withdraw of 20 reais should return insufficient balance`() {
        this.webTestClient.post()
                .uri(API_PATH_WITHDRAW)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(WITHDRAW_WITH_INSUFFICIENT_BALANCE)
                .exchange()
                .expectStatus().is4xxClientError
                .expectBody()
                .jsonPath("$.status").isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .jsonPath("$.message").isEqualTo("Insufficient balance")
    }

    @Test
    fun `given an valid transfer should return success`() {
        this.webTestClient.post()
                .uri(API_PATH_TRANSFER)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(VALID_TRANSFER)
                .exchange()
                .expectStatus().isCreated
                .expectBody()
                .jsonPath("$.id").isNotEmpty
                .jsonPath("$.accountId").isEqualTo(VALID_TRANSFER.accountId.toString())
                .jsonPath("$.accountIdTo").isEqualTo(VALID_TRANSFER.accountIdTo.toString())
                .jsonPath("$.type").isEqualTo(TransactionType.TRANSFER.name)
                .jsonPath("$.amount").isEqualTo(VALID_TRANSFER.amount)
                .jsonPath("$.balanceFrom").isEqualTo(VALID_TRANSFER.amount)
                .jsonPath("$.balanceTo").isEqualTo(BigDecimal.ZERO.toDouble())
                .jsonPath("$.createdAt").isNotEmpty
    }

    @Test
    fun `given an transfer to account invalid should failed`() {
        this.webTestClient.post()
                .uri(API_PATH_TRANSFER)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(INVALID_TRANSFER_TO_ACCOUNT_NONEXISTENT)
                .exchange()
                .expectStatus().isNotFound
                .expectBody()
                .jsonPath("$.status").isEqualTo(HttpStatus.NOT_FOUND.value())
                .jsonPath("$.message").isEqualTo("Account ${INVALID_TRANSFER_TO_ACCOUNT_NONEXISTENT.accountIdTo} not found")
    }

}