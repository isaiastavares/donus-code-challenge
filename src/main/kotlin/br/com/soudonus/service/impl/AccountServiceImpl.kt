package br.com.soudonus.service.impl

import br.com.soudonus.converter.AccountConverter
import br.com.soudonus.exception.ConflictException
import br.com.soudonus.exception.EntityNotFoundException
import br.com.soudonus.exception.InsufficientBalanceException
import br.com.soudonus.model.domain.Account
import br.com.soudonus.model.dto.account.AccountCreateDTO
import br.com.soudonus.model.dto.account.AccountDTO
import br.com.soudonus.repository.AccountRepository
import br.com.soudonus.service.AccountService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.UUID

@Service
class AccountServiceImpl(private val repository: AccountRepository) : AccountService {

    override suspend fun findAll(page: Pageable): Page<AccountDTO> {
        return repository.findAll(page)
                .map { AccountConverter.fromModelToDTO(it) }
    }

    override suspend fun findById(accountId: UUID): AccountDTO {
        val account = findByIdDomain(accountId)
        return AccountConverter.fromModelToDTO(account)
    }

    override suspend fun create(accountDTO: AccountCreateDTO): AccountDTO {
        val model = AccountConverter.fromDTOToModelCreate(accountDTO)
        repository.findByDocument(model.document)?.let { throw ConflictException("Account already exists with this CPF") }
        val account = repository.save(model)
        return AccountConverter.fromModelToDTO(account)
    }

    override suspend fun increaseBalance(accountId: UUID, value: BigDecimal): Pair<BigDecimal, BigDecimal> {
        val account = findByIdDomain(accountId)
        val balanceFrom = account.balance
        val balanceTo = account.balance + value

        return updateBalance(account, balanceFrom, balanceTo)
    }

    override suspend fun decreaseBalance(accountId: UUID, value: BigDecimal): Pair<BigDecimal, BigDecimal> {
        val account = findByIdDomain(accountId)
        val balanceFrom = account.balance
        val balanceTo = account.balance - value

        if (balanceTo.compareTo(BigDecimal.ZERO) == -1) {
            throw InsufficientBalanceException()
        }

        return updateBalance(account, balanceFrom, balanceTo)
    }

    private suspend fun findByIdDomain(accountId: UUID): Account {
        return repository.findByIdOrNull(accountId)
                ?: throw EntityNotFoundException("Account $accountId not found")
    }

    private suspend fun updateBalance(account: Account, balanceFrom: BigDecimal, balanceTo: BigDecimal): Pair<BigDecimal, BigDecimal> {
        account.balance = balanceTo
        repository.save(account)
        return Pair(balanceFrom, balanceTo)
    }

}
