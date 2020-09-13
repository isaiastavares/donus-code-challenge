package br.com.soudonus.model.dto.transfer

import br.com.soudonus.model.dto.transaction.TransactionCreateDTO
import java.math.BigDecimal
import java.util.UUID
import javax.validation.constraints.Positive

data class TransferCreateDTO(
        val accountId: UUID,
        val accountIdTo: UUID,
        @field:Positive
        val amount: BigDecimal
) : TransactionCreateDTO