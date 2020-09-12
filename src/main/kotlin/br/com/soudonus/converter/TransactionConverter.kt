package br.com.soudonus.converter

import br.com.soudonus.model.domain.Transaction
import br.com.soudonus.model.dto.deposit.DepositCreateDTO
import br.com.soudonus.model.dto.transaction.TransactionDTO
import br.com.soudonus.model.dto.transfer.TransferCreateDTO
import br.com.soudonus.model.dto.withdraw.WithdrawCreateDTO
import br.com.soudonus.model.enum.TransactionType
import java.math.BigDecimal
import java.time.Clock
import java.time.Instant

class TransactionConverter {
    companion object {
        fun fromDepositCreateToTransaction(dto: DepositCreateDTO, balanceFrom: BigDecimal, balanceTo: BigDecimal) =
                Transaction(
                        accountId = dto.accountId,
                        type = TransactionType.DEPOSIT,
                        amount = dto.amount,
                        balanceFrom = balanceFrom,
                        balanceTo = balanceTo,
                        createdAt = Instant.now(Clock.systemUTC()))

        fun fromWithdrawCreateToTransaction(dto: WithdrawCreateDTO, balanceFrom: BigDecimal, balanceTo: BigDecimal) =
                Transaction(
                        accountId = dto.accountId,
                        type = TransactionType.WITHDRAW,
                        amount = dto.amount,
                        balanceFrom = balanceFrom,
                        balanceTo = balanceTo,
                        createdAt = Instant.now(Clock.systemUTC()))

        fun fromTransferCreateToTransaction(dto: TransferCreateDTO, balanceFrom: BigDecimal, balanceTo: BigDecimal) =
                Transaction(
                        accountId = dto.accountId,
                        accountIdTo = dto.accountIdTo,
                        type = TransactionType.TRANSFER,
                        amount = dto.amount,
                        balanceFrom = balanceFrom,
                        balanceTo = balanceTo,
                        createdAt = Instant.now(Clock.systemUTC()))

        fun fromModelToDto(model: Transaction) =
                TransactionDTO(
                        id = model.id!!,
                        accountId = model.accountId,
                        accountIdTo = model.accountIdTo,
                        type = model.type,
                        amount = model.amount,
                        balanceFrom = model.balanceFrom,
                        balanceTo = model.balanceTo,
                        createdAt = model.createdAt
                )
    }
}