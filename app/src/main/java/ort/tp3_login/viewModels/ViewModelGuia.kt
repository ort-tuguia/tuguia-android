package ort.tp3_login.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ort.tp3_login.dataclasses.UsuarioLogin

class ViewModelGuia: ViewModel() {
    var user = MutableLiveData<UsuarioLogin>()
    var token :String = ""



}