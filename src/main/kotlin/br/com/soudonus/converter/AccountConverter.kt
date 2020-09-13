package br.com.soudonus.converter

import br.com.soudonus.model.domain.Account
import br.com.soudonus.model.dto.account.AccountCreateDTO
import br.com.soudonus.model.dto.account.AccountDTO
import java.math.BigDecimal
import java.time.Clock
import java.time.Instant

object AccountConverter {
    fun fromModelToDTO(model: Account) =
            AccountDTO(
                    id = model.id!!,
                    name = model.name,
                    cpf = model.document,
                    balance = model.balance,
                    createdAt = model.createdAt,
                    updatedAt = model.updatedAt)

    fun fromDTOToModelCreate(dto: AccountCreateDTO): Account {
        val now = Instant.now(Clock.systemUTC())
        return Account(
                name = dto.name,
                document = dto.cpf.replace(Regex("[^0-9]"), ""),
                balance = BigDecimal.ZERO,
                createdAt = now,
                updatedAt = now)
    }
}