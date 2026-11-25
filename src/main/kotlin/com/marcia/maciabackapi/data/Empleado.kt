package com.marcia.maciabackapi.data

import jakarta.persistence.*

@Entity
@Table(name = "empleados")
class Empleado(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", nullable = false)
    var usuarioId: Usuario? = null,

    @Column(nullable = false, length = 50)
    var role: String = "",

    @Column(length = 100)
    var shifts: String = "",

    @Column(length = 20)
    var rating: String = ""
)