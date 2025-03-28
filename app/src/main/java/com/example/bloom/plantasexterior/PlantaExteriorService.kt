package com.example.bloom.plantasexterior

import com.example.bloom.ResponseMessage
import com.example.bloom.base.BaseAPI
import com.example.bloom.compra.Compra
import com.example.bloom.favoritos.Favorito
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
interface PlantaExteriorService {

    @GET("/plantas_exterior/{id}")
    suspend fun obtenerPlantaExterior(@Path("id") id: Int): PlantaExterior

    @GET("/plantas_exterior/")
    suspend fun obtenerTodasPlantasExterior(): List<PlantaExterior>

    @POST("/compras/")
    suspend fun crearCompra(@Body compra: Compra): ResponseMessage

    @POST("/favoritos/")
    suspend fun crearFavorito(@Body favorito: Favorito): ResponseMessage
}

class PlantaExteriorAPI : BaseAPI<PlantaExteriorService>(
    baseUrl = "https://18.211.200.201"
) {
    override fun getServiceClass(): Class<PlantaExteriorService> {
        return PlantaExteriorService::class.java
    }
}