package ort.tp3_login.dataclasses

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ServicioService {
    @GET("activities")
    suspend fun getServicios() : Response<Servicios>

    @GET("activities/search")
    suspend fun searchServicios(
             @Query("currentLatitude"
        )currentLatitude:Double,
             @Query("currentLongitude"
        )currentLongitude:Double,
            @Query("maxKm"
        )maxKm :Double,
             @Query("maxResults"
        )maxResults :Int) : Response<Servicios>

    @GET("categories")
    suspend fun getCategories(): Response<Categorias>


}