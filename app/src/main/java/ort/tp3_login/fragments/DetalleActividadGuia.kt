package ort.tp3_login.fragments

import android.app.AlertDialog
import android.content.DialogInterface
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
import ort.tp3_login.viewModels.ViewModelGuia

class DetalleActividadGuia : Fragment() {

    lateinit private var view1 : View
    lateinit private var valoracion : TextView
    lateinit var title: TextView
    lateinit var description: TextView
    private val viewModel: ViewModelGuia by activityViewModels()
    lateinit var buttonEditar : Button
    lateinit var uriPhoto : Uri
    lateinit var foto : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 =inflater.inflate(R.layout.fragment_detalle_actividad_guia, container, false)
        valoracion = view1.findViewById(R.id.textviewValoracion)
        title = view1.findViewById(R.id.textviewTitle)
        description = view1.findViewById(R.id.textviewDescription)
        buttonEditar = view1.findViewById(R.id.buttonEditar)
        foto = view1.findViewById(R.id.imageViewFoto)

        title.text = viewModel.servicioItemSeleccionado?.name
        description.text = viewModel.servicioItemSeleccionado?.description

        if (viewModel.servicioItemSeleccionado?.photos?.isNotEmpty() == true) {
            uriPhoto = viewModel.servicioItemSeleccionado!!.photos[0].photoUrl.toUri()
            Picasso.get().load(uriPhoto).into(foto)
            Log.d("uri foto", uriPhoto.toString())
        }else{
            foto.setImageResource(R.drawable.no_image_available)
        }

        if(viewModel.servicioItemSeleccionado?.reviews!=null){
            valoracion.text = viewModel.servicioItemSeleccionado?.reviews?.avgScore.toString()
        }
        else{
            valoracion.visibility = View.GONE
        }
        return view1
    }

    override fun onStart() {
        super.onStart()
        valoracion.setOnClickListener{
            view1.findNavController().navigate(R.id.action_detalleActividadGuia_to_reviewsActividadGuia)
        }
        buttonEditar.setOnClickListener {
            openDialogImagen()
        }

    }

    private fun openDialogImagen() {
        var alertDialog = AlertDialog.Builder(this.context)
        alertDialog.setTitle("Imagen")
            .setMessage("Querés cambiar la imagen de la actividad?")
            .setPositiveButton("Si",
                DialogInterface.OnClickListener{ dialog: DialogInterface?, which: Int ->
                    view1.findNavController().navigate(R.id.action_detalleActividadGuia_to_fotoAgregarServicio)
                })
            .setNegativeButton("No",
                DialogInterface.OnClickListener{ dialog: DialogInterface?, which: Int ->
                    //TODO implementar viewmodel url imagen
                    view1.findNavController().navigate(R.id.action_detalleActividadGuia_to_agregarServicio)
                })
        alertDialog.create().show()
    }


}