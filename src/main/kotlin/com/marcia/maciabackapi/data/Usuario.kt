package com.marcia.maciabackapi.data

import jakarta.persistence.*

@Entity
@Table(name = "usuarios")
class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(nullable = false, length = 100)
    var nome: String = "",

    @Column(nullable = false, length = 100)
    var apellido: String = "",

    @Column(nullable = false, unique = true, length = 150)
    var email: String = "",

    @Column(length = 30)
    var tel: String = "",

    @Column(nullable = false, unique = true, length = 20)
    var dni: String = "",

    @Column(nullable = false)
    var password: String = ""
) {
    override fun toString(): String {
        return "Usuario(id=$id, nome='$nome', apellido='$apellido', email='$email')"
    }
}