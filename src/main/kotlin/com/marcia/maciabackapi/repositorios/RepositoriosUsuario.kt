package com.marcia.maciabackapi.repositorios



import com.marcia.maciabackapi.data.Empleado
import com.marcia.maciabackapi.data.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsuarioRepository : JpaRepository<Usuario, Int> {
    fun findByEmail(email: String): Usuario?
    fun existsByEmail(email: String): Boolean
    fun existsByDni(dni: String): Boolean
}

@Repository
interface EmpleadoRepository : JpaRepository<Empleado, Int>