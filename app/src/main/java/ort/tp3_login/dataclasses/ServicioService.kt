package ort.tp3_login.dataclasses

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
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

    @GET("login")
    suspend fun getLogin(
        @Query("userLogin")userLogin: String,
        @Query("passLogin")passLogin: String
    ): Response<UsuarioLogin>


}