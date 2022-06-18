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

    @GET("activities/myself")
    suspend fun getMyServicios(@Header("Authorization") token: String) : Response<Servicios>

    @POST("user/login")
    suspend fun getLogin(@Body login: Login)
    : Response<UsuarioLogin>

    @POST("user/register")
    suspend fun postRegister (@Body usuarioRegister: UsuarioRegister)
    : Response<UsuarioLogin>

    @POST("activities")
    suspend fun postCrearServicio (@Body x: CrearServicio, @Header("Authorization") token: String)
            : Response<ServicioItem>

    @PUT("activities/{id}")
    suspend fun putActividad (@Path("id")id: String, @Body servicio: CrearServicio, @Header("Authorization") token: String)
            : Response<ServicioItem>

    @PUT("user/phones")
    suspend fun putPhone (@Body phone: ArrayList<Phone>, @Header("Authorization") token: String)
            : Response<UsuarioLogin>

    @PUT("user")
    suspend fun putUsuario (@Body usuario: UsuarioEdit, @Header("Authorization") token: String)
            : Response<UsuarioLogin>

    @PUT("user/fav/categories")
    suspend fun putCategories (@Body categories: ArrayList<String>, @Header("Authorization") token: String)
            : Response<UsuarioLogin>
    @PUT("user/password")
    suspend fun putPassword (@Body changePassword:  ChangePasswordDataClass, @Header("Authorization") token: String)
            : Response<UsuarioLogin>
    @PUT("user/photo")
    suspend fun putPhoto (@Body photo: Photo, @Header("Authorization") token: String)
            : Response<UsuarioLogin>

    @POST("user/fav/activities/{activityId}")
    suspend fun postFavActivity(@Path("activityId") activityId: String, @Header("Authorization") token: String)
            : Response<List<ServicioItem>>

    @POST("bookings/{id}/review")
    suspend fun postReview(@Path("id") activityId: String, @Body x: CrearReview,@Header("Authorization") token: String)
            : Response<Review>

    @DELETE("user/fav/activities/{activityId}")
    suspend fun deleteFavActivity(@Path("activityId") activityId: String, @Header("Authorization") token: String)
            : Response<List<ServicioItem>>

    @POST("bookings")
    suspend fun postReserva(@Body crearReserva: CrearReserva, @Header("Authorization") token: String)
            : Response<Reserva>

    @GET("bookings/myself")
    suspend fun getReservas(@Header("Authorization") token: String) : Response<List<Reserva>>

    @GET("activities/{id}/reviews")
    suspend fun getReviewsActividad(@Path("id") activityId: String, @Header("Authorization") token: String) : Response<List<Review>>


}