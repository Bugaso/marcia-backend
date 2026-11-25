package com.marcia.maciabackapi.servicios



import com.marcia.maciabackapi.repositorios.UsuarioRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val usuarioRepository: UsuarioRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val usuario = usuarioRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("Usuario no encontrado con email: $email")

        return User.builder()
            .username(usuario.email)
            .password(usuario.password)
            .roles("USER")
            .build()
    }
}