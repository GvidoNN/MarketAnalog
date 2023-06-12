package my.lovely.marketanalog.presentation.menu_asia

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import my.lovely.domain.model.Tag
import my.lovely.marketanalog.R
import java.lang.invoke.ConstantCallSite

class TagAdapter() :
    RecyclerView.Adapter<TagAdapter.TagViewHolder>() {

    private lateinit var context: Context
    private lateinit var tagListener: OnItemClickListener

    var tagsList = mutableListOf<Tag>()

    fun setTagList(tag: List<Tag>) {
        this.tagsList = tag.toMutableList()
        notifyDataSetChanged()
    }

    class TagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTagName: TextView = itemView.findViewById(R.id.tvTagName)
        val cardView: ConstraintLayout = itemView.findViewById(R.id.constraintLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        context = parent.context
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_tag, parent, false)
        return TagViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tagsList.size
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        val tagData = tagsList[position]

        holder.tvTagName.text = tagData.tag
        if(tagData.isChoosen){
            holder.cardView.setBackgroundColor(ContextCompat.getColor(context, R.color.blue))
            holder.tvTagName.setTextColor(context.getColor(R.color.white))
        } else {
            holder.cardView.setBackgroundColor(ContextCompat.getColor(context, R.color.white_gray))
            holder.tvTagName.setTextColor(context.getColor(R.color.black))
        }
        holder.tvTagName.setOnClickListener {
            isAnyItemExpanded(position)
            tagData.isChoosen = !tagData.isChoosen
            if(tagData.isChoosen){
                holder.cardView.setBackgroundColor(ContextCompat.getColor(context, R.color.blue))
                holder.tvTagName.setTextColor(context.getColor(R.color.white))
            } else {
                holder.cardView.setBackgroundColor(ContextCompat.getColor(context, R.color.white_gray))
                holder.tvTagName.setTextColor(context.getColor(R.color.black))
            }
            notifyItemChanged(position)
            tagListener.onItemClick(position)
        }
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        tagListener = listener
    }

    private fun isAnyItemExpanded(position: Int) {
        val temp = tagsList.indexOfFirst {
            it.isChoosen
        }
        if (temp >= 0 && temp != position) {
            tagsList[temp].isChoosen = false
            notifyItemChanged(temp, 0)
        }

    }

}