package com.example.bloom.floreseventos

import com.example.bloom.ResponseMessage
import com.example.bloom.base.BaseAPI
import com.example.bloom.compra.Compra
import com.example.bloom.favoritos.Favorito
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FlorEventoService {

    @GET("/flores_eventos/{id}")
    suspend fun obtenerFloresEventos(@Path("id") id: Int): FlorEvento

    @GET("/flores_eventos/")
    suspend fun obtenerTodasFloresEventos(): List<FlorEvento>

    @POST("/compras/")
    suspend fun crearCompra(@Body compra: Compra): ResponseMessage

    @POST("/favoritos/")
    suspend fun crearFavorito(@Body favorito: Favorito): ResponseMessage
}

class FlorEventoAPI : BaseAPI<FlorEventoService>(
    baseUrl = "https://18.211.200.201"
) {
    override fun getServiceClass(): Class<FlorEventoService> {
        return FlorEventoService::class.java
    }
}