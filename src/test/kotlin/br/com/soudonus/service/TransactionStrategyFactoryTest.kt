package br.com.soudonus.service

import br.com.soudonus.model.enum.TransactionType
import br.com.soudonus.repository.TransactionRepository
import br.com.soudonus.service.impl.DepositStrategyImpl
import br.com.soudonus.service.impl.TransferStrategyImpl
import br.com.soudonus.service.impl.WithdrawStrategyImpl
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.apache.commons.lang3.NotImplementedException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class TransactionStrategyFactoryTest {

    private val transactionRepository = mockk<TransactionRepository>()
    private val accountService = mockk<AccountService>()
    private val depositStrategyImpl = DepositStrategyImpl(transactionRepository, accountService, DEPOSIT_PERCENTAGE)
    private val withdrawStrategyImpl = WithdrawStrategyImpl(transactionRepository, accountService, WITHDRAW_PERCENTAGE)
    private val transferStrategyImpl = TransferStrategyImpl(transactionRepository, accountService)

    @Test
    fun `should return deposit strategy when transaction type is deposit`() = runBlocking {
        val transactionStrategyFactory = TransactionStrategyFactory(listOf(depositStrategyImpl, withdrawStrategyImpl, transferStrategyImpl))

        val strategy = transactionStrategyFactory.getStrategyForType(TransactionType.DEPOSIT)

        assertTrue(strategy is DepositStrategyImpl)
    }

    @Test
    fun `should return withdraw strategy when transaction type is withdraw`() = runBlocking {
        val transactionStrategyFactory = TransactionStrategyFactory(listOf(depositStrategyImpl, withdrawStrategyImpl, transferStrategyImpl))

        val strategy = transactionStrategyFactory.getStrategyForType(TransactionType.WITHDRAW)

        assertTrue(strategy is WithdrawStrategyImpl)
    }

    @Test
    fun `should return transfer strategy when transaction type is transfer`() = runBlocking {
        val transactionStrategyFactory = TransactionStrategyFactory(listOf(depositStrategyImpl, withdrawStrategyImpl, transferStrategyImpl))

        val strategy = transactionStrategyFactory.getStrategyForType(TransactionType.TRANSFER)

        assertTrue(strategy is TransferStrategyImpl)
    }

    @Test
    fun `should throw exception when get strategy invalid`() = runBlocking {
        val transactionStrategyFactory = TransactionStrategyFactory(listOf(depositStrategyImpl))

        val exception = withContext(Dispatchers.Default) { assertThrows<NotImplementedException> { runBlocking { transactionStrategyFactory.getStrategyForType(TransactionType.TRANSFER) } } }
        assertEquals(NotImplementedException::class.java.name, exception.javaClass.name)
    }

    companion object {
        private const val DEPOSIT_PERCENTAGE = "0.005"
        private const val WITHDRAW_PERCENTAGE = "0.01"
    }

}