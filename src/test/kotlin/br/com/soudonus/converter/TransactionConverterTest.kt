package br.com.soudonus.converter

import br.com.soudonus.model.domain.Transaction
import br.com.soudonus.model.dto.deposit.DepositCreateDTO
import br.com.soudonus.model.dto.transfer.TransferCreateDTO
import br.com.soudonus.model.dto.withdraw.WithdrawCreateDTO
import br.com.soudonus.model.enum.TransactionType
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.time.Clock
import java.time.Instant
import java.util.UUID

@ExtendWith(MockKExtension::class)
class TransactionConverterTest {

    @Test
    fun `converter deposit create to transaction`() {
        val balanceFrom = BigDecimal.ZERO
        val balanceTo = BigDecimal.ONE
        val dto = DepositCreateDTO(
                accountId = UUID.randomUUID(),
                amount = balanceTo)

        val transaction = TransactionConverter.fromDepositCreateToTransaction(dto, balanceFrom, balanceTo)

        assertNull(transaction.id)
        assertEquals(dto.accountId, transaction.accountId)
        assertNull(transaction.accountIdTo)
        assertEquals(TransactionType.DEPOSIT, transaction.type)
        assertEquals(dto.amount, transaction.amount)
        assertEquals(balanceFrom, transaction.balanceFrom)
        assertEquals(balanceTo, transaction.balanceTo)
        assertNotNull(transaction.createdAt)
    }

    @Test
    fun `converter withdraw create to transaction`() {
        val balanceFrom = BigDecimal.ONE
        val balanceTo = BigDecimal.ZERO
        val dto = WithdrawCreateDTO(
                accountId = UUID.randomUUID(),
                amount = balanceTo)

        val transaction = TransactionConverter.fromWithdrawCreateToTransaction(dto, balanceFrom, balanceTo)

        assertNull(transaction.id)
        assertEquals(dto.accountId, transaction.accountId)
        assertNull(transaction.accountIdTo)
        assertEquals(TransactionType.WITHDRAW, transaction.type)
        assertEquals(dto.amount, transaction.amount)
        assertEquals(balanceFrom, transaction.balanceFrom)
        assertEquals(balanceTo, transaction.balanceTo)
        assertNotNull(transaction.createdAt)
    }

    @Test
    fun `converter transfer create to transaction`() {
        val balanceFrom = BigDecimal.ONE
        val balanceTo = BigDecimal.ZERO
        val dto = TransferCreateDTO(
                accountId = UUID.randomUUID(),
                accountIdTo = UUID.randomUUID(),
                amount = balanceTo)

        val transaction = TransactionConverter.fromTransferCreateToTransaction(dto, balanceFrom, balanceTo)

        assertNull(transaction.id)
        assertEquals(dto.accountId, transaction.accountId)
        assertEquals(dto.accountIdTo, transaction.accountIdTo)
        assertEquals(TransactionType.TRANSFER, transaction.type)
        assertEquals(dto.amount, transaction.amount)
        assertEquals(balanceFrom, transaction.balanceFrom)
        assertEquals(balanceTo, transaction.balanceTo)
        assertNotNull(transaction.createdAt)
    }

    @Test
    fun `converter model to dto`() {
        val model = Transaction(
                id = UUID.randomUUID(),
                accountId = UUID.randomUUID(),
                accountIdTo = UUID.randomUUID(),
                type = TransactionType.DEPOSIT,
                amount = BigDecimal.ONE,
                balanceFrom = BigDecimal.ZERO,
                balanceTo = BigDecimal.ONE,
                createdAt = Instant.now(Clock.systemUTC())
        )

        val dto = TransactionConverter.fromModelToDto(model)

        assertEquals(model.id, dto.id)
        assertEquals(model.accountId, dto.accountId)
        assertEquals(model.accountIdTo, dto.accountIdTo)
        assertEquals(model.type, dto.type)
        assertEquals(model.amount, dto.amount)
        assertEquals(model.balanceFrom, dto.balanceFrom)
        assertEquals(model.balanceTo, dto.balanceTo)
        assertEquals(model.createdAt, dto.createdAt)
    }

}