package br.com.soudonus.model.dto.account

import org.apache.commons.lang3.StringUtils
import org.hibernate.validator.constraints.br.CPF
import javax.validation.constraints.NotBlank

data class AccountCreateDTO(
        @field:NotBlank
        val name: String = StringUtils.EMPTY,
        @field:NotBlank
        @field:CPF
        val cpf: String = StringUtils.EMPTY
)