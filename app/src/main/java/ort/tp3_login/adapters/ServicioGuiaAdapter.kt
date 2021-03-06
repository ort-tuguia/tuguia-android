package ort.tp3_login.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ort.tp3_login.R
import ort.tp3_login.entities.ServicioCard
import ort.tp3_login.holders.ServicioGuiaViewHolder
import ort.tp3_login.holders.ServicioViewHolder

class ServicioGuiaAdapter(
    private var servicioList: MutableList<ServicioCard>,
    val onItemClick: (Int) -> Unit) : RecyclerView.Adapter<ServicioGuiaViewHolder>()
{
    override fun onBindViewHolder(holder: ServicioGuiaViewHolder, position: Int) {

        holder.setNames(
            servicioList[position].titulo,
            servicioList[position].nombreGuia,
            servicioList[position].imagen,
            servicioList[position].valoracion,
            servicioList[position].profilePic
        )
        holder.getCardLayout().setOnClickListener{
            onItemClick(position)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicioGuiaViewHolder {

        val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_design_home_guia,parent,false)
        return ServicioGuiaViewHolder(view)
    }



    override fun getItemCount(): Int {

        return servicioList.size
    }

    fun setData(newData : ArrayList<ServicioCard>){
        this.servicioList = newData
        this.notifyDataSetChanged()
    }


}