package ort.tp3_login.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ort.tp3_login.R
import ort.tp3_login.entities.ServicioCard
import ort.tp3_login.holders.ServicioViewHolder

class ServicioAdapter(
    private var servicioList: MutableList<ServicioCard>,
    val onItemClick: (Int) -> Unit) : RecyclerView.Adapter<ServicioViewHolder>()
{
    override fun onBindViewHolder(holder: ServicioViewHolder, position: Int) {

        holder.setNames(
            servicioList[position].titulo,
            servicioList[position].nombreGuia,
            servicioList[position].imagen,
            servicioList[position].valoracion,
            servicioList[position].profilePic,
            servicioList[position].activityId,
            servicioList[position].user,
            servicioList[position].token,
        )
        holder.getCardLayout().setOnClickListener{
            onItemClick(position)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicioViewHolder {

        val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_design,parent,false)
        return ServicioViewHolder(view)
    }



    override fun getItemCount(): Int {

        return servicioList.size
    }

    fun setData(newData : ArrayList<ServicioCard>){
        this.servicioList = newData
        this.notifyDataSetChanged()
    }


}