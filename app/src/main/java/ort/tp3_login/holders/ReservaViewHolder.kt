package ort.tp3_login.holders

import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import ort.tp3_login.R
import ort.tp3_login.dataclasses.Phone

class ReservaViewHolder(v: View): RecyclerView.ViewHolder(v) {
    private var view: View
    init {
        this.view = v
    }
    fun setReservas(nombreTurista: String,tituloActividad:String, telefonoTurista : String, emailTurista: String){
        var textViewActividadTitle : TextView = view.findViewById(R.id.textviewActividadTitle)
        var textViewNombreTurist : TextView = view.findViewById(R.id.textViewNombreTurista)
        var textViewTelefonoTurista : TextView = view.findViewById(R.id.textViewTelefonoTurista)
        var textViewEmailTurista : TextView = view.findViewById(R.id.textViewEmailTurista)

        textViewActividadTitle.setText(tituloActividad)
        textViewNombreTurist.setText(nombreTurista)
        textViewTelefonoTurista.setText(telefonoTurista)
        textViewEmailTurista.setText(emailTurista)

    }
    fun getCardLayout(): CardView {
        return view.findViewById(R.id.cardViewReserva)
    }
    /*fun getPhone(): Phone {
        var phone: Phone = Phone(
            view.findViewById<EditText>(R.id.editTextPhone).text.toString(),
            view.findViewById<EditText>(R.id.editTextDescription).text.toString()
        )
        return phone
    }*/
}