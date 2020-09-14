package br.com.soudonus.service.impl

import br.com.soudonus.model.dto.withdraw.WithdrawCreateDTO
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
class WithdrawStrategyImplTest {

    private val transactionRepository = mockk<TransactionRepository>()
    private val accountService = mockk<AccountService>()
    private val withdrawStrategyImpl = WithdrawStrategyImpl(transactionRepository, accountService, WITHDRAW_PERCENTAGE)

    @Test
    fun `should return get type withdraw`() = runBlocking {
        assertEquals(TransactionType.WITHDRAW, withdrawStrategyImpl.getType())
    }

    @Test
    fun `should return get transaction repository`() = runBlocking {
        assertTrue(withdrawStrategyImpl.getTransactionRepository() is TransactionRepository)
    }

    @Test
    fun `should update balance with success`() = runBlocking {
        val balance = BigDecimal(30.0)
        val withdrawCreateDTO = WithdrawCreateDTO(
                accountId = UUID.randomUUID(),
                amount = BigDecimal(20.0)
        )

        val amountWithTax = withdrawCreateDTO.amount.plus(withdrawCreateDTO.amount.multiply(BigDecimal(WITHDRAW_PERCENTAGE)))
                .setScale(2, RoundingMode.HALF_UP)

        coEvery { accountService.decreaseBalance(any(), any()) } returns Pair(balance, amountWithTax)

        val transaction = withdrawStrategyImpl.updateBalance(withdrawCreateDTO)

        assertNull(transaction.id)
        assertEquals(withdrawCreateDTO.accountId, transaction.accountId)
        assertNull(transaction.accountIdTo)
        assertEquals(withdrawCreateDTO.amount, transaction.amount)
        assertEquals(balance, transaction.balanceFrom)
        assertEquals(amountWithTax, transaction.balanceTo)
        assertEquals(TransactionType.WITHDRAW, transaction.type)
        assertNotNull(transaction.createdAt)
    }

    companion object {
        private const val WITHDRAW_PERCENTAGE = "0.01"
    }

}