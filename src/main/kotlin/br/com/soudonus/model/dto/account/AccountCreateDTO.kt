package br.com.soudonus.model.dto.account

import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.NotBlank

data class AccountCreateDTO(
        @field:NotBlank
        val name: String,
        @field:CPF
        val cpf: String
)