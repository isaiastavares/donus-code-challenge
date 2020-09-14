package br.com.soudonus.service.impl

import br.com.soudonus.model.dto.transfer.TransferCreateDTO
import br.com.soudonus.model.enum.TransactionType
import br.com.soudonus.repository.TransactionRepository
import br.com.soudonus.service.AccountService
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.util.UUID

@ExtendWith(MockKExtension::class)
class TransferStrategyImplTest {

    @InjectMockKs
    private lateinit var transferStrategyImpl: TransferStrategyImpl

    @MockK
    private lateinit var transactionRepository: TransactionRepository

    @MockK
    private lateinit var accountService: AccountService

    @Test
    fun `should return get type withdraw`() = runBlocking {
        assertEquals(TransactionType.TRANSFER, transferStrategyImpl.getType())
    }

    @Test
    fun `should return get transaction repository`() = runBlocking {
        assertTrue(transferStrategyImpl.getTransactionRepository() is TransactionRepository)
    }

    @Test
    fun `should update balance with success`() = runBlocking {
        val balanceFrom = BigDecimal(20.0)
        val transferCreateDTO = TransferCreateDTO(
                accountId = UUID.randomUUID(),
                accountIdTo = UUID.randomUUID(),
                amount = BigDecimal(20.0)
        )

        coEvery { accountService.decreaseBalance(any(), any()) } returns Pair(balanceFrom, BigDecimal.ZERO)
        coEvery { accountService.increaseBalance(any(), any()) } returns Pair(BigDecimal.ZERO, transferCreateDTO.amount)

        val transaction = transferStrategyImpl.updateBalance(transferCreateDTO)

        assertNull(transaction.id)
        assertEquals(transferCreateDTO.accountId, transaction.accountId)
        assertEquals(transferCreateDTO.accountIdTo, transaction.accountIdTo)
        assertEquals(transferCreateDTO.amount, transaction.amount)
        assertEquals(balanceFrom, transaction.balanceFrom)
        assertEquals(BigDecimal.ZERO, transaction.balanceTo)
        assertEquals(TransactionType.TRANSFER, transaction.type)
        assertNotNull(transaction.createdAt)
    }

}