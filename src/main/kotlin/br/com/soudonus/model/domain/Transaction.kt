package br.com.soudonus.model.domain

import br.com.soudonus.model.enum.TransactionType
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Transaction(
        @Id
        @GeneratedValue(generator = "uuid2")
        var id: UUID? = null,
        var accountId: UUID,
        var accountIdTo: UUID? = null,
        @Enumerated(EnumType.STRING)
        @Column(length = 15)
        var type: TransactionType,
        var amount: BigDecimal,
        var balanceFrom: BigDecimal,
        var balanceTo: BigDecimal,
        var createdAt: Instant
)