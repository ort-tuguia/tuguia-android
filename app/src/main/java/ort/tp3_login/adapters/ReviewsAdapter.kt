package ort.tp3_login.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ort.tp3_login.R
import ort.tp3_login.dataclasses.Review
import ort.tp3_login.entities.ReviewCard
import ort.tp3_login.holders.ReviewViewHolder


class ReviewsAdapter(
    private var reviewsList: MutableList<ReviewCard>,
    val onItemClick: (Int) -> Unit) : RecyclerView.Adapter<ReviewViewHolder>()
{
    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {

        holder.setNames(
            reviewsList[position].valoracion,
            reviewsList[position].review,
            reviewsList[position].fecha
        )
        holder.getCardLayout().setOnClickListener{
            onItemClick(position)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {

        val view : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_review,parent,false)
        return ReviewViewHolder(view)
    }

    override fun getItemCount(): Int {

        return reviewsList.size
    }

    //fun setData(newData : ArrayList<ReviewViewHolder>){
       // this.reviewsList = newData
       // this.notifyDataSetChanged()
   // }


}