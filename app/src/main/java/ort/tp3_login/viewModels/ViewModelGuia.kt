package ort.tp3_login.viewModels

import android.location.Location
import android.net.Uri
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ort.tp3_login.dataclasses.UsuarioLogin

class ViewModelGuia: ViewModel() {
    var user = MutableLiveData<UsuarioLogin>()
    var token :String = ""


    // agregarServicio
    //var servicioName: String = ""
    var servicioCategoriaId : String = ""
    //var servicioDescription: String = ""
    var servicioLocationlat: Double = 0.0
    var servicioLocationlon: Double = 0.0
    //var servicioPrice: Double= 0.0
    var servicioUrlFoto: Uri? = null
    var  categorias: Array<CharSequence>? = null



}