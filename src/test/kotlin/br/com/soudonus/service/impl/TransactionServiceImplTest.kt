package br.com.soudonus.service.impl

import br.com.soudonus.repository.TransactionRepository
import br.com.soudonus.resource.TransactionResource
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.util.UUID

@ExtendWith(MockKExtension::class)
class TransactionServiceImplTest {

    @InjectMockKs
    private lateinit var transactionServiceImpl: TransactionServiceImpl

    @MockK
    private lateinit var transactionRepository: TransactionRepository

    @Test
    fun `should find transactions by account id paginated`() = runBlocking {
        val transaction = TransactionResource.getTransaction(UUID.randomUUID())

        coEvery { transactionRepository.findByAccountId(any(), any()) } returns PageImpl(listOf(transaction))

        val page = transactionServiceImpl.findTransactionsByIdAccountId(UUID.randomUUID(), PageRequest.of(0, 10))
        val transactionDTO = page.get().findFirst().get()

        assertEquals(transaction.id, transactionDTO.id)
        assertEquals(transaction.accountId, transactionDTO.accountId)
        assertEquals(transaction.accountIdTo, transactionDTO.accountIdTo)
        assertEquals(transaction.amount, transactionDTO.amount)
        assertEquals(transaction.type, transactionDTO.type)
        assertEquals(transaction.balanceFrom, transactionDTO.balanceFrom)
        assertEquals(transaction.balanceTo, transactionDTO.balanceTo)
        assertEquals(transaction.createdAt, transactionDTO.createdAt)
    }

}