package br.com.soudonus.converter

import br.com.soudonus.model.domain.Account
import br.com.soudonus.model.dto.account.AccountCreateDTO
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

@ExtendWith(MockKExtension::class)
class AccountConverterTest {

    @Test
    fun `converter model to dto`() {
        val model = Account(
                id = UUID.randomUUID(),
                name = "name",
                document = "document",
                balance = BigDecimal.ONE,
                createdAt = Instant.now(),
                updatedAt = Instant.now())

        val dto = AccountConverter.fromModelToDTO(model)

        assertEquals(model.id, dto.id)
        assertEquals(model.name, dto.name)
        assertEquals(model.document, dto.cpf)
        assertEquals(model.balance, dto.balance)
        assertEquals(model.createdAt, dto.createdAt)
        assertEquals(model.updatedAt, dto.updatedAt)
    }

    @Test
    fun `converter dto to model create`() {
        val dto = AccountCreateDTO(
                name = "name",
                cpf = "333.437.938-84")

        val model = AccountConverter.fromDTOToModelCreate(dto)

        assertNull(model.id)
        assertEquals(dto.name, model.name)
        assertEquals(dto.cpf.replace(Regex("[^0-9]"), ""), model.document)
        assertEquals(BigDecimal.ZERO, model.balance)
        assertNotNull(model.createdAt)
        assertNotNull(model.updatedAt)
    }

}