package br.com.soudonus.resource

import br.com.soudonus.model.domain.Transaction
import br.com.soudonus.model.enum.TransactionType
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

object TransactionResource {
    fun getTransaction(id: UUID): Transaction {
        return Transaction(
                id = id,
                accountId = UUID.randomUUID(),
                accountIdTo = UUID.randomUUID(),
                amount = BigDecimal.ONE,
                type = TransactionType.DEPOSIT,
                balanceFrom = BigDecimal.ZERO,
                balanceTo = BigDecimal.ONE,
                createdAt = Instant.now()
        )
    }
}