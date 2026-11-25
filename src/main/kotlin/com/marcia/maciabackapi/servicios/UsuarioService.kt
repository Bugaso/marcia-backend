package com.marcia.maciabackapi.servicios

import com.marcia.maciabackapi.data.Usuario
import com.marcia.maciabackapi.dtos.UsuarioRequest
import com.marcia.maciabackapi.dtos.UsuarioResponse
import com.marcia.maciabackapi.dtos.UsuarioUpdateRequest
import com.marcia.maciabackapi.dtos.toResponse
import com.marcia.maciabackapi.repositorios.UsuarioRepository
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

@Service
class UsuarioService(
    private val usuarioRepository: UsuarioRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional(readOnly = true)
    fun listarUsuarios(): List<UsuarioResponse> =
        usuarioRepository.findAll().map { it.toResponse() }

    @Transactional(readOnly = true)
    fun obtenerPorId(id: Int): UsuarioResponse {
        val usuario = usuarioRepository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado con ID: $id") }
        return usuario.toResponse()
    }

    @Transactional
    fun crearUsuario(request: UsuarioRequest): UsuarioResponse {
        if (usuarioRepository.existsByEmail(request.email)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "El email ya está registrado")
        }
        if (usuarioRepository.existsByDni(request.dni)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "El DNI ya está registrado")
        }

        val hashedPassword = requireNotNull(passwordEncoder.encode(request.password)) {
            "Error al encriptar la contraseña"
        }

        val nuevoUsuario = Usuario(
            nome = request.nome,
            apellido = request.apellido,
            email = request.email,
            tel = request.tel,
            dni = request.dni,
            password = hashedPassword
        )

        return usuarioRepository.save(nuevoUsuario).toResponse()
    }

    @Transactional
    fun actualizarUsuario(id: Int, request: UsuarioUpdateRequest): UsuarioResponse {
        val usuario = usuarioRepository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado con ID: $id") }

        request.nome?.let { usuario.nome = it }
        request.apellido?.let { usuario.apellido = it }
        request.tel?.let { usuario.tel = it }

        request.email?.let { nuevoEmail ->
            if (nuevoEmail != usuario.email && usuarioRepository.existsByEmail(nuevoEmail)) {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "El email ya está en uso")
            }
            usuario.email = nuevoEmail
        }

        request.dni?.let { nuevoDni ->
            if (nuevoDni != usuario.dni && usuarioRepository.existsByDni(nuevoDni)) {
                throw ResponseStatusException(HttpStatus.BAD_REQUEST, "El DNI ya está en uso")
            }
            usuario.dni = nuevoDni
        }

        request.password?.takeIf { it.isNotBlank() }?.let { rawPassword ->
            usuario.password = requireNotNull(passwordEncoder.encode(rawPassword)) {
                "Error al encriptar la contraseña"
            }
        }

        return usuarioRepository.save(usuario).toResponse()
    }

    @Transactional
    fun eliminarUsuario(id: Int) {
        if (!usuarioRepository.existsById(id)) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado con ID: $id")
        }
        usuarioRepository.deleteById(id)
    }
}