package ort.tp3_login.fragments
import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.squareup.picasso.Picasso
import ort.tp3_login.R
import ort.tp3_login.viewModels.ViewModelHomeTurista


class DetalleActividad : Fragment() {

    lateinit private var view1 : View
    lateinit private var valoracion : TextView
    lateinit var title: TextView
    lateinit var description: TextView
    private val viewModel: ViewModelHomeTurista by activityViewModels()
    lateinit var buttonReservar : Button
    lateinit var foto : ImageView
    var uriPhoto: Uri = "".toUri()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 =inflater.inflate(R.layout.fragment_detalle_actividad, container, false)
        valoracion = view1.findViewById(R.id.textviewValoracion)
        title = view1.findViewById(R.id.textviewTitle)
        description = view1.findViewById(R.id.textviewDescription)
        buttonReservar = view1.findViewById(R.id.buttonReservar)
        title.text = viewModel.servicioItemSeleccionado.name
        description.text = viewModel.servicioItemSeleccionado.description
        foto = view1.findViewById(R.id.imageViewFoto)

        if (viewModel.servicioItemSeleccionado.photos.isNotEmpty()) {
            uriPhoto = viewModel.servicioItemSeleccionado.photos[0].photoUrl.toUri()
            Picasso.get().load(uriPhoto).into(foto)
            Log.d("uri foto", uriPhoto.toString())
        }else{
            foto.setImageResource(R.drawable.no_image_available)
        }

        return view1
    }

    override fun onStart() {
        super.onStart()
        valoracion.setOnClickListener{
            view1.findNavController().navigate(R.id.action_detalleActividad_to_reviews)
        }
        buttonReservar.setOnClickListener{
            //TODO PEgarle a backend agregando a reservasGuia y a reservasTurista
            //TODO SACAR CONTACTO DE PANTALLA
            showDialog()
        }
    }
    private fun showDialog(){
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle("Contacto")
        dialog.setMessage("Nombre del guia: "+viewModel.servicioItemSeleccionado.guideUsername
                +"\n"+"Telefono: "+viewModel.servicioItemSeleccionado.price
                +"\n"+"Correo: "+viewModel.servicioItemSeleccionado.name)
        dialog.setPositiveButton("Aceptar") { dialog, which ->}
        dialog.show()

    }


}