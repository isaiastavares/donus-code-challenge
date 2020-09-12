package br.com.soudonus.model.dto.transfer

import br.com.soudonus.model.dto.transaction.TransactionCreateDTO
import java.math.BigDecimal
import java.util.UUID

data class TransferCreateDTO(
        val accountId: UUID,
        val accountIdTo: UUID,
        val amount: BigDecimal
) : TransactionCreateDTO