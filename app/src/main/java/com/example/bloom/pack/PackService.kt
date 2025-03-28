package com.example.bloom.pack

import com.example.bloom.ResponseMessage
import com.example.bloom.base.BaseAPI
import com.example.bloom.compra.Compra
import com.example.bloom.favoritos.Favorito
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PackService {

    @GET("/packs/{id}")
    suspend fun obtenerPack(@Path("id") id: Int): Pack

    @GET("/packs/")
    suspend fun obtenerTodosPacks(): List<Pack>

    @POST("/compras/")
    suspend fun crearCompra(@Body compra: Compra): ResponseMessage

    @POST("/favoritos/")
    suspend fun crearFavorito(@Body favorito: Favorito): ResponseMessage
}

class PackAPI : BaseAPI<PackService>(
    baseUrl = "https://18.211.200.201"
) {
    override fun getServiceClass(): Class<PackService> {
        return PackService::class.java
    }
}