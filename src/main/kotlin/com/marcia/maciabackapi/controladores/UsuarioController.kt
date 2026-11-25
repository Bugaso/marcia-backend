package com.marcia.maciabackapi.controladores

import com.marcia.maciabackapi.dtos.UsuarioRequest
import com.marcia.maciabackapi.dtos.UsuarioResponse
import com.marcia.maciabackapi.dtos.UsuarioUpdateRequest
import com.marcia.maciabackapi.servicios.UsuarioService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/usuarios")
class UsuarioController(
    private val usuarioService: UsuarioService
) {

    // Listado de todos los usuarios
    @GetMapping
    fun listar(): ResponseEntity<List<UsuarioResponse>> =
        ResponseEntity.ok(usuarioService.listarUsuarios())

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    fun obtenerPorId(@PathVariable id: Int): ResponseEntity<UsuarioResponse> =
        ResponseEntity.ok(usuarioService.obtenerPorId(id))

    // Alta de usuario (requiere token JWT válido)
    @PostMapping
    fun crear(@RequestBody request: UsuarioRequest): ResponseEntity<UsuarioResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.crearUsuario(request))

    // Modificación de usuario
    @PutMapping("/{id}")
    fun actualizar(
        @PathVariable id: Int,
        @RequestBody request: UsuarioUpdateRequest
    ): ResponseEntity<UsuarioResponse> =
        ResponseEntity.ok(usuarioService.actualizarUsuario(id, request))

    // Baja de usuario
    @DeleteMapping("/{id}")
    fun eliminar(@PathVariable id: Int): ResponseEntity<Void> {
        usuarioService.eliminarUsuario(id)
        return ResponseEntity.noContent().build()
    }
}