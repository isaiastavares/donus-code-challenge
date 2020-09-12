package br.com.soudonus.service.impl

import br.com.soudonus.converter.TransactionConverter
import br.com.soudonus.model.domain.Transaction
import br.com.soudonus.model.dto.transaction.TransactionCreateDTO
import br.com.soudonus.model.dto.transfer.TransferCreateDTO
import br.com.soudonus.model.enum.TransactionType
import br.com.soudonus.repository.TransactionRepository
import br.com.soudonus.service.AccountService
import br.com.soudonus.service.TransactionStrategy
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TransferStrategyImpl(
        private val repository: TransactionRepository,
        private val accountService: AccountService
) : TransactionStrategy() {

    override fun getType(): TransactionType {
        return TransactionType.TRANSFER
    }

    override fun getTransactionRepository(): JpaRepository<Transaction, UUID> {
        return repository
    }

    override suspend fun updateBalance(transactionCreateDTO: TransactionCreateDTO): Transaction {
        val transferCreateDTO = transactionCreateDTO as TransferCreateDTO
        val (balanceFrom, balanceTo) = accountService.decreaseBalance(transferCreateDTO.accountId, transferCreateDTO.amount)
        accountService.increaseBalance(transferCreateDTO.accountIdTo, transferCreateDTO.amount)
        return TransactionConverter.fromTransferCreateToTransaction(transferCreateDTO, balanceFrom, balanceTo)
    }

}