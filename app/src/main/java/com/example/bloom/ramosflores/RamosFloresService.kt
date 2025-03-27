package com.example.bloom.ramosflores

import com.example.bloom.ResponseMessage
import com.example.bloom.base.BaseAPI
import com.example.bloom.compra.Compra
import com.example.bloom.favoritos.Favorito
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface RamoFlorService {
    @GET("/ramos_flores/{id}")
    suspend fun obtenerRamoFlor(@Path("id") id: Int): RamoFlor

    @GET("/ramos_flores/")
    suspend fun obtenerTodosRamosFlores(): List<RamoFlor>

    @POST("/compras/")
    suspend fun crearCompra(@Body compra: Compra): ResponseMessage

    @POST("/favoritos/")
    suspend fun crearFavorito(@Body favorito: Favorito): ResponseMessage
}

class RamoFlorAPI : BaseAPI<RamoFlorService>(
    baseUrl = "https://18.211.200.201"
) {
    override fun getServiceClass(): Class<RamoFlorService> {
        return RamoFlorService::class.java
    }
}