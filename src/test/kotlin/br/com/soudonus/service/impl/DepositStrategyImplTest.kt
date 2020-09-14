package br.com.soudonus.service.impl

import br.com.soudonus.model.dto.deposit.DepositCreateDTO
import br.com.soudonus.model.enum.TransactionType
import br.com.soudonus.repository.TransactionRepository
import br.com.soudonus.service.AccountService
import io.mockk.coEvery
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.UUID

@ExtendWith(MockKExtension::class)
class DepositStrategyImplTest {

    private val transactionRepository = mockk<TransactionRepository>()
    private val accountService = mockk<AccountService>()
    private val depositStrategyImpl = DepositStrategyImpl(transactionRepository, accountService, DEPOSIT_PERCENTAGE)

    @Test
    fun `should return get type deposit`() = runBlocking {
        assertEquals(TransactionType.DEPOSIT, depositStrategyImpl.getType())
    }

    @Test
    fun `should return get transaction repository`() = runBlocking {
        assertTrue(depositStrategyImpl.getTransactionRepository() is TransactionRepository)
    }

    @Test
    fun `should update balance with success`() = runBlocking {
        val depositCreateDTO = DepositCreateDTO(
                accountId = UUID.randomUUID(),
                amount = BigDecimal(20.0)
        )

        val amountWithBonus = depositCreateDTO.amount.multiply(BigDecimal.ONE.plus(BigDecimal(DEPOSIT_PERCENTAGE)))
                .setScale(2, RoundingMode.HALF_UP)

        coEvery { accountService.increaseBalance(any(), any()) } returns Pair(BigDecimal.ZERO, amountWithBonus)

        val transaction = depositStrategyImpl.updateBalance(depositCreateDTO)

        assertNull(transaction.id)
        assertEquals(depositCreateDTO.accountId, transaction.accountId)
        assertNull(transaction.accountIdTo)
        assertEquals(depositCreateDTO.amount, transaction.amount)
        assertEquals(BigDecimal.ZERO, transaction.balanceFrom)
        assertEquals(amountWithBonus, transaction.balanceTo)
        assertEquals(TransactionType.DEPOSIT, transaction.type)
        assertNotNull(transaction.createdAt)
    }

    companion object {
        private const val DEPOSIT_PERCENTAGE = "0.005"
    }

}