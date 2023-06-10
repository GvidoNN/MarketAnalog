package my.lovely.marketanalog.presentation.mainCatalog

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import my.lovely.domain.model.Catalog
import my.lovely.marketanalog.R

class CatalogAdapter() :
    RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder>() {

    private lateinit var context: Context
    private lateinit var catalogListener: OnItemClickListener

    var catalogList = mutableListOf<Catalog>()

    fun setCatalogsList(block: List<Catalog>) {
        this.catalogList = block.toMutableList()
        notifyDataSetChanged()
    }

    class CatalogViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val tvCatalogName: TextView = itemView.findViewById(R.id.tvCatalogName)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        context = parent.context
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_catalog, parent, false)
        return CatalogViewHolder(view, catalogListener)
    }

    override fun getItemCount(): Int {
        return catalogList.size
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        val catalogData = catalogList[position]
        holder.tvCatalogName.text = catalogData.name
        val url = catalogData.image_url
        Glide.with(holder.itemView.context)
            .load(url)
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    holder.cardView.background = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })

    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        catalogListener = listener
    }

}