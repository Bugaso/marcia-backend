package com.marcia.maciabackapi.controladores

import com.marcia.maciabackapi.configuracion.JwtService
import com.marcia.maciabackapi.dtos.AuthResponse
import com.marcia.maciabackapi.dtos.LoginRequest
import com.marcia.maciabackapi.dtos.UsuarioRequest
import com.marcia.maciabackapi.dtos.toResponse
import com.marcia.maciabackapi.repositorios.UsuarioRepository
import com.marcia.maciabackapi.servicios.CustomUserDetailsService
import com.marcia.maciabackapi.servicios.UsuarioService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val jwtService: JwtService,
    private val usuarioService: UsuarioService,
    private val usuarioRepository: UsuarioRepository
) {

    @PostMapping("/register")
    fun register(@RequestBody request: UsuarioRequest): ResponseEntity<AuthResponse> {
        val creado = usuarioService.crearUsuario(request)
        val userDetails = userDetailsService.loadUserByUsername(creado.email)
        val token = jwtService.generateToken(userDetails)
        return ResponseEntity.status(HttpStatus.CREATED).body(AuthResponse(token, creado))
    }

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<AuthResponse> {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.email, request.password)
        )
        val userDetails = userDetailsService.loadUserByUsername(request.email)
        val usuario = usuarioRepository.findByEmail(request.email)!!.toResponse()
        val token = jwtService.generateToken(userDetails)
        return ResponseEntity.ok(AuthResponse(token, usuario))
    }
}