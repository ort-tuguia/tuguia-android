package ort.tp3_login.dataclasses

import retrofit2.Response
import retrofit2.http.*

interface ServicioService {
    @GET("activities")
    suspend fun getServicios(@Header("Authorization") token: String) : Response<Servicios>

    @POST("activities/search")
    suspend fun searchServicios(@Body serviciosSearch: ServiciosSearch , @Header("Authorization") token: String) : Response<Servicios>

    @GET("categories")
    suspend fun getCategories( @Header("Authorization") token: String): Response<Categorias>

    @POST("users/login")
    suspend fun getLogin(@Body login: Login)
    : Response<UsuarioLogin>

    @POST("users/register")
    suspend fun postRegister (@Body usuarioRegister: UsuarioRegister)
    : Response<UsuarioLogin>

    @POST("activities")
    suspend fun postCrearServicio (@Body x: CrearServicio, @Header("Authorization") token: String)
            : Response<ServicioItem>


}