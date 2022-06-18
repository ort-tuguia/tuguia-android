package ort.tp3_login.holders

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import ort.tp3_login.R
import ort.tp3_login.dataclasses.Phone

class PhoneViewHolder(v:View):RecyclerView.ViewHolder(v) {
private var view: View
    private var flagDelete = false
init {
    this.view = v
}
fun setPhones(description: String,phone:String){
    var editTextDescription : EditText = view.findViewById(R.id.editTextDescription)
    var editTextPhone : EditText = view.findViewById(R.id.editTextPhone)
    var delete : ImageView = view.findViewById(R.id.imageViewDelete)

    editTextDescription.setText(description)
    editTextPhone.setText(phone)

    delete.setOnClickListener {
        var cardView: CardView = view.findViewById(R.id.cardViewPhone)
        cardView.visibility = View.GONE
        flagDelete = true
    }
}
    fun getCardLayout():CardView{
        return view.findViewById(R.id.cardViewPhone)
    }
    fun getPhone():Phone{
        var phone: Phone = Phone(
            view.findViewById<EditText>(R.id.editTextPhone).text.toString(),
            view.findViewById<EditText>(R.id.editTextDescription).text.toString()
        )
        return phone
    }
    fun getFlagDelete():Boolean{
        return flagDelete
    }
}