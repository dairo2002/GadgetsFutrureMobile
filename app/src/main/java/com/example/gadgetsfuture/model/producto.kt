package com.example.gadgetsfuture.model

import java.net.URL

data class producto(
    val id: Int,
    val nombre:String,
    val slug: String,
    val precio: Double,
    val descripcion:String,
    val disponible: Boolean,
    val imagen: URL,
    val stock: Int,
    //val categoria: Categoria
)
