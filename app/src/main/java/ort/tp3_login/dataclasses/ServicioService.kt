package ort.tp3_login.dataclasses

import retrofit2.Response
import retrofit2.http.GET

interface ServicioService {
    @GET("activities")
    suspend fun getServicios() : Response<Servicio>
}