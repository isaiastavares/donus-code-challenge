package br.com.soudonus.model.dto.deposit

import br.com.soudonus.model.dto.transaction.TransactionCreateDTO
import java.math.BigDecimal
import java.util.UUID

data class DepositCreateDTO(
        val accountId: UUID,
        val amount: BigDecimal
) : TransactionCreateDTO