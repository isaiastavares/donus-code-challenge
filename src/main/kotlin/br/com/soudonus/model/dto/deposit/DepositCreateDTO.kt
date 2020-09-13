package br.com.soudonus.model.dto.deposit

import br.com.soudonus.model.dto.transaction.TransactionCreateDTO
import java.math.BigDecimal
import java.util.UUID
import javax.validation.constraints.Positive

data class DepositCreateDTO(
        val accountId: UUID,
        @field:Positive
        val amount: BigDecimal
) : TransactionCreateDTO