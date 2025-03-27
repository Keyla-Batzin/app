package com.example.bloom.plantasinterior

import com.example.bloom.ResponseMessage
import com.example.bloom.base.BaseAPI
import com.example.bloom.compra.Compra
import com.example.bloom.favoritos.Favorito
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PlantaInteriorService {

    @GET("/plantas_interior/{id}")
    suspend fun obtenerPlantaInterior(@Path("id") id: Int): PlantaInterior

    @GET("/plantas_interior/")
    suspend fun obtenerTodasPlantasInterior(): List<PlantaInterior>

    @POST("/compras/")
    suspend fun crearCompra(@Body compra: Compra): ResponseMessage

    @POST("/favoritos/")
    suspend fun crearFavorito(@Body favorito: Favorito): ResponseMessage
}

class PlantaInteriorAPI : BaseAPI<PlantaInteriorService>(
    baseUrl = "https://18.211.200.201"
) {
    override fun getServiceClass(): Class<PlantaInteriorService> {
        return PlantaInteriorService::class.java
    }
}