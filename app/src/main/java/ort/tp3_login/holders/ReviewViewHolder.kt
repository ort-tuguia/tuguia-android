package ort.tp3_login.holders
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import ort.tp3_login.R


class ReviewViewHolder(v:View):RecyclerView.ViewHolder(v) {
    private var view: View
    init {
        this.view = v
    }
    fun setNames(valoracion: Double,contenido:String, fecha: String){
        var textViewValoracion : TextView = view.findViewById(R.id.textviewValoracion)
        var textViewFecha : TextView = view.findViewById(R.id.textviewFecha)
        var textViewContenido : TextView = view.findViewById(R.id.textviewReview)
        textViewValoracion.setText(valoracion.toString())
        textViewFecha.setText(fecha)
        textViewContenido.setText(contenido)
    }
    fun getCardLayout():CardView{
        return view.findViewById(R.id.cardViewReview)
    }
    //fun getPhone():Phone{
        //var phone: Phone = Phone(
           // view.findViewById<EditText>(R.id.editTextPhone).text.toString(),
           // view.findViewById<EditText>(R.id.editTextDescription).text.toString()
       // )
     //   return phone
    //}
}