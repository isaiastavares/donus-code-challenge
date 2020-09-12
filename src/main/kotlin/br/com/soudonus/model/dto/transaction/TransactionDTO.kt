package br.com.soudonus.model.dto.transaction

import br.com.soudonus.model.enum.TransactionType
import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

@JsonInclude(JsonInclude.Include.NON_NULL)
data class TransactionDTO(
        val id: UUID,
        val accountId: UUID,
        val accountIdTo: UUID? = null,
        val type: TransactionType,
        val amount: BigDecimal,
        val balanceFrom: BigDecimal,
        val balanceTo: BigDecimal,
        val createdAt: Instant
)