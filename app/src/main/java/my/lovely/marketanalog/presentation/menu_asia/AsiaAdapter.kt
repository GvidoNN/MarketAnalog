package my.lovely.marketanalog.presentation.menu_asia

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.lovely.domain.model.Catalog
import my.lovely.domain.model.Dishe
import my.lovely.marketanalog.R

class AsiaAdapter() :
    RecyclerView.Adapter<AsiaAdapter.AsiaViewHolder>() {

    private lateinit var context: Context
    private lateinit var menuListener: OnItemClickListener

    var asianList = mutableListOf<Dishe>()

    fun setAsiaList(dish: List<Dishe>) {
        this.asianList = dish.toMutableList()
        notifyDataSetChanged()
    }

    class AsiaViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val imAsiaMenu: ImageView = itemView.findViewById(R.id.imMenu)
        val tvMenuName: TextView = itemView.findViewById(R.id.tvMenuName)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsiaViewHolder {
        context = parent.context
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_asia_menu, parent, false)
        return AsiaViewHolder(view, menuListener)
    }

    override fun getItemCount(): Int {
        return asianList.size
    }

    override fun onBindViewHolder(holder: AsiaViewHolder, position: Int) {
        val asiaData = asianList[position]
        holder.tvMenuName.text = asiaData.name
        Glide.with(holder.itemView).load(asiaData.image_url).into(holder.imAsiaMenu)
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        menuListener = listener
    }

}