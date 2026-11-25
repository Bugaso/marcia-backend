package com.marcia.maciabackapi.dtos

import com.marcia.maciabackapi.data.Usuario


data class UsuarioRequest(
    val nome: String,
    val apellido: String,
    val email: String,
    val tel: String = "",
    val dni: String,
    val password: String
)

data class UsuarioUpdateRequest(
    val nome: String?,
    val apellido: String?,
    val email: String?,
    val tel: String?,
    val dni: String?,
    val password: String?
)

data class UsuarioResponse(
    val id: Int,
    val nome: String,
    val apellido: String,
    val email: String,
    val tel: String,
    val dni: String
)

fun Usuario.toResponse() = UsuarioResponse(
    id = this.id ?: 0,
    nome = this.nome,
    apellido = this.apellido,
    email = this.email,
    tel = this.tel,
    dni = this.dni
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class AuthResponse(
    val token: String,
    val usuario: UsuarioResponse
)