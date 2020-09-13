package br.com.soudonus.model.dto.withdraw

import br.com.soudonus.model.dto.transaction.TransactionCreateDTO
import java.math.BigDecimal
import java.util.UUID
import javax.validation.constraints.Positive

data class WithdrawCreateDTO(
        val accountId: UUID,
        @field:Positive
        val amount: BigDecimal
) : TransactionCreateDTO