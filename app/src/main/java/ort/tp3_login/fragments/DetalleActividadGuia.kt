package ort.tp3_login.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import ort.tp3_login.R
import ort.tp3_login.viewModels.ViewModelGuia

class DetalleActividadGuia : Fragment() {

    lateinit private var view1 : View
    lateinit private var valoracion : TextView
    lateinit var title: TextView
    lateinit var description: TextView
    private val viewModel: ViewModelGuia by activityViewModels()
    lateinit var buttonEditar : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 =inflater.inflate(R.layout.fragment_detalle_actividad_guia, container, false)
        valoracion = view1.findViewById(R.id.textviewValoracion)
        title = view1.findViewById(R.id.textviewTitle)
        description = view1.findViewById(R.id.textviewDescription)
        buttonEditar = view1.findViewById(R.id.buttonEditar)

        title.text = viewModel.servicioItemSeleccionado?.name
        description.text = viewModel.servicioItemSeleccionado?.description

        return view1
    }

    override fun onStart() {
        super.onStart()
        valoracion.setOnClickListener{
            view1.findNavController().navigate(R.id.action_detalleActividad_to_reviews)
        }
        buttonEditar.setOnClickListener {
            openDialogLocation()
        }

    }

    private fun openDialogLocation() {
        var alertDialog = AlertDialog.Builder(this.context)
        alertDialog.setTitle("Locación")
            .setMessage("Querés cambiar la locación de la actividad?")
            .setPositiveButton("Si",
                DialogInterface.OnClickListener{ dialog: DialogInterface?, which: Int ->
                    view1.findNavController().navigate(R.id.action_detalleActividadGuia_to_mapsAgregarServicio2)
                })
            .setNegativeButton("No",
                DialogInterface.OnClickListener{ dialog: DialogInterface?, which: Int ->
                    viewModel.servicioLocationlat = viewModel.servicioItemSeleccionado!!.locationLatitude!!
                    viewModel.servicioLocationlon = viewModel.servicioItemSeleccionado!!.locationLongitude
                    openDialogImagen()
                })
        alertDialog.create().show()
    }

    private fun openDialogImagen() {
        var alertDialog = AlertDialog.Builder(this.context)
        alertDialog.setTitle("Imagen")
            .setMessage("Querés cambiar el imagen de la actividad?")
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