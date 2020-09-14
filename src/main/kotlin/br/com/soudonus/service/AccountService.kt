package br.com.soudonus.service

import br.com.soudonus.model.dto.account.AccountCreateDTO
import br.com.soudonus.model.dto.account.AccountDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.math.BigDecimal
import java.util.UUID

interface AccountService {

    suspend fun findById(accountId: UUID): AccountDTO
    suspend fun create(accountDTO: AccountCreateDTO): AccountDTO
    suspend fun increaseBalance(accountId: UUID, value: BigDecimal): Pair<BigDecimal, BigDecimal>
    suspend fun decreaseBalance(accountId: UUID, value: BigDecimal): Pair<BigDecimal, BigDecimal>
    suspend fun findAll(page: Pageable): Page<AccountDTO>

}