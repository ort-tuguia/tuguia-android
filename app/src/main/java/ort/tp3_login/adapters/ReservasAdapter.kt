package ort.tp3_login.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ort.tp3_login.R
import ort.tp3_login.dataclasses.Phone
import ort.tp3_login.entities.ReservaCard
import ort.tp3_login.holders.ReservaViewHolder


class ReservasAdapter (
    private var reservas: MutableList<ReservaCard>,
    val onItemClick: (Int) -> Unit): RecyclerView.Adapter<ReservaViewHolder>() {

    var reservaViewHolder: MutableList<ReservaViewHolder>? = mutableListOf()
    override fun onBindViewHolder(holder: ReservaViewHolder, position: Int) {
        holder.setReservas(
            reservas[position].nombreActividad,
            reservas[position].nombreUsuario,
            reservas[position].telefono,
            reservas[position].email,
        )
        holder.getCardLayout().setOnClickListener {
            onItemClick(position)
        }
        reservaViewHolder?.add(holder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservaViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.card_reserva, parent, false
        )
        return ReservaViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reservas.size
    }

    fun setData(phones: List<Phone>) {
        this.reservas = reservas as MutableList<ReservaCard>
        this.notifyDataSetChanged()
    }


}