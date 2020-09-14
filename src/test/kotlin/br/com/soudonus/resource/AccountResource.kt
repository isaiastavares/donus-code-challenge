package br.com.soudonus.resource

import br.com.soudonus.model.domain.Account
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

object AccountResource {
    fun getAccount(id: UUID): Account {
        return Account(
                id = id,
                name = "name",
                document = "document",
                balance = BigDecimal.ZERO,
                createdAt = Instant.now(),
                updatedAt = Instant.now()
        )
    }
}