package ort.tp3_login

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import org.w3c.dom.Text

class ServicioAdapter(
    var nombreGuiaList: ArrayList<String>,
    var tituloList: ArrayList<String>,
    var imagenList: ArrayList<Int>,
    var perfilPicList:ArrayList<Int>,
    var fechaList : ArrayList<String>,
    var valoracionList: ArrayList<String>,
    var context: Context) : RecyclerView.Adapter<ServicioAdapter.ServicioViewHolder>()
{
    class ServicioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var textViewTitulo : TextView = itemView.findViewById(R.id.tituloCard)
        var textViewNombreGuia : TextView = itemView.findViewById(R.id.nombreGuiaCard)
        var imagenView : ImageView = itemView.findViewById(R.id.imageCard)
        var textViewValoracion : TextView = itemView.findViewById(R.id.valoracionCard)
        var perfilPic : CircleImageView = itemView.findViewById(R.id.perfilPicCard)
        var fechaTextView : TextView = itemView.findViewById(R.id.fechaCard)
    }
    override fun onBindViewHolder(holder: ServicioViewHolder, position: Int) {

        holder.textViewTitulo.text = tituloList.get(position)
        holder.textViewNombreGuia.text = nombreGuiaList.get(position)
        holder.imagenView.setImageResource(imagenList.get(position))
        holder.textViewValoracion.text = valoracionList.get(position)
        holder.perfilPic.setImageResource(perfilPicList.get(position))
        holder.fechaTextView.text = fechaList.get(position)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicioViewHolder {

        val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_design,parent,false)
        return ServicioViewHolder(view)
    }



    override fun getItemCount(): Int {

        return tituloList.size
    }

}