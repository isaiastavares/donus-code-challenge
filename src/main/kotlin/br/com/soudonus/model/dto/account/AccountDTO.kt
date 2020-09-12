package br.com.soudonus.model.dto.account

import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

data class AccountDTO(
        val id: UUID,
        val name: String,
        val cpf: String,
        val balance: BigDecimal,
        val createdAt: Instant,
        val updatedAt: Instant
)