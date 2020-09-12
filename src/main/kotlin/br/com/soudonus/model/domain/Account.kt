package br.com.soudonus.model.domain

import java.math.BigDecimal
import java.time.Instant
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Account(
        @Id
        @GeneratedValue(generator = "uuid2")
        var id: UUID? = null,
        @Column(length = 60)
        var name: String,
        @Column(length = 11)
        var document: String,
        var balance: BigDecimal,
        var createdAt: Instant,
        var updatedAt: Instant
)