package br.com.soudonus.model.dto.withdraw

import br.com.soudonus.model.dto.transaction.TransactionCreateDTO
import java.math.BigDecimal
import java.util.UUID

data class WithdrawCreateDTO(
        val accountId: UUID,
        val amount: BigDecimal
) : TransactionCreateDTO