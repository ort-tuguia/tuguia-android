package ort.tp3_login.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ort.tp3_login.R
import ort.tp3_login.dataclasses.Phone
import ort.tp3_login.holders.PhoneViewHolder

class PhonesAdapter (
    private var phones: MutableList<Phone>,
    val onItemClick: (Int) -> Unit): RecyclerView.Adapter<PhoneViewHolder>() {

    var phoneViewHold: PhoneViewHolder? = null
    override fun onBindViewHolder(holder: PhoneViewHolder, position: Int) {
        holder.setPhones(
            phones[position].description,
            phones[position].number
        )
        holder.getCardLayout().setOnClickListener {
            onItemClick(position)
        }
        phoneViewHold = holder
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.phone_design, parent, false
        )
        return PhoneViewHolder(view)
    }

    override fun getItemCount(): Int {
        return phones.size
    }

    fun setData(phones: List<Phone>) {
        this.phones = phones as MutableList<Phone>
        this.notifyDataSetChanged()
    }

    fun getData(): List<Phone> {
        var auxPhones: MutableList<Phone> = ArrayList<Phone>()

        phones.forEach {
            phoneViewHold?.let { it1 -> auxPhones.add(it1.getPhone()) }

        }

        return auxPhones

    }
}

