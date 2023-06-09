package my.lovely.marketanalog.presentation.menu_asia

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.lovely.domain.model.Catalog
import my.lovely.domain.model.Dishe
import my.lovely.marketanalog.R

class AsiaAdapter() :
    RecyclerView.Adapter<AsiaAdapter.AsiaViewHolder>() {

    private lateinit var context: Context
//    private lateinit var catalogListener: OnItemClickListener

    private var asiaList = mutableListOf<Dishe>()

    fun setAsiaList(dish: List<Dishe>) {
        this.asiaList = dish.toMutableList()
        notifyDataSetChanged()
    }

    //    , listener: OnItemClickListener
    class AsiaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imAsiaMenu: ImageView = itemView.findViewById(R.id.imMenu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsiaViewHolder {
        context = parent.context
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_asia_menu, parent, false)
        return AsiaViewHolder(view)
//        , catalogListener

    }

    override fun getItemCount(): Int {
        return asiaList.size
    }

    override fun onBindViewHolder(holder: AsiaViewHolder, position: Int) {
        val asiaData = asiaList[position]
        val url = asiaData.image_url
        if(url == null){
            Log.d("MyLog","$position null")
        } else{
            Log.d("MyLog",url)
        }
        Glide.with(holder.itemView).load(asiaData.image_url).into(holder.imAsiaMenu)
    }

    /*interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        catalogListener = listener
    }*/

}