package br.com.soudonus.service.impl

import br.com.soudonus.converter.TransactionConverter
import br.com.soudonus.model.domain.Transaction
import br.com.soudonus.model.dto.deposit.DepositCreateDTO
import br.com.soudonus.model.dto.transaction.TransactionCreateDTO
import br.com.soudonus.model.enum.TransactionType
import br.com.soudonus.repository.TransactionRepository
import br.com.soudonus.service.AccountService
import br.com.soudonus.service.TransactionStrategy
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.UUID

@Service
class DepositStrategyImpl(
        private val repository: TransactionRepository,
        private val accountService: AccountService
) : TransactionStrategy() {

    @Value("\${deposit.percentage}")
    private lateinit var depositPercentage: String

    override fun getType(): TransactionType {
        return TransactionType.DEPOSIT
    }

    override fun getTransactionRepository(): JpaRepository<Transaction, UUID> {
        return repository
    }

    override suspend fun updateBalance(transactionCreateDTO: TransactionCreateDTO): Transaction {
        val depositCreateDTO = transactionCreateDTO as DepositCreateDTO
        val amount = calculateAmount(depositCreateDTO.amount)
        val (balanceFrom, balanceTo) = accountService.increaseBalance(depositCreateDTO.accountId, amount)
        return TransactionConverter.fromDepositCreateToTransaction(depositCreateDTO, balanceFrom, balanceTo)
    }

    private fun calculateAmount(amount: BigDecimal): BigDecimal {
        return amount.multiply(BigDecimal.ONE.plus(BigDecimal(depositPercentage))).setScale(2, RoundingMode.HALF_UP)
    }

}