package com.example.bloom.macetasaccesorios

import com.example.bloom.ResponseMessage
import com.example.bloom.base.BaseAPI
import com.example.bloom.compra.Compra
import com.example.bloom.favoritos.Favorito
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MacetaAccesorioService {

    @GET("/macetas_accesorios/{id}")
    suspend fun obtenerMacetaAccesorio(@Path("id") id: Int): MacetaAccesorio

    @GET("/macetas_accesorios/")
    suspend fun obtenerTodasMacetasAccesorios(): List<MacetaAccesorio>

    @POST("/compras/")
    suspend fun crearCompra(@Body compra: Compra): ResponseMessage

    @POST("/favoritos/")
    suspend fun crearFavorito(@Body favorito: Favorito): ResponseMessage
}

class MacetaAccesorioAPI : BaseAPI<MacetaAccesorioService>(
    baseUrl = "https://18.211.200.201"
) {
    override fun getServiceClass(): Class<MacetaAccesorioService> {
        return MacetaAccesorioService::class.java
    }
}