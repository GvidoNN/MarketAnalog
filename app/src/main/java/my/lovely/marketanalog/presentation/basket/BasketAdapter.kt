package my.lovely.marketanalog.presentation.basket

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import my.lovely.domain.model.Basket
import my.lovely.marketanalog.R

class BasketAdapter : RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {

    private lateinit var context: Context
    private var basketList = mutableListOf<Basket>()
    private lateinit var minusBasketListener: OnItemClickListener
    private lateinit var plusBasketListener: OnItemClickListener

    fun setBasketList(basket: List<Basket>) {
        this.basketList = basket.toMutableList()
        notifyDataSetChanged()
    }

    class BasketViewHolder(
        itemView: View,
        minuslistener: OnItemClickListener,
        plusListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(itemView) {

        val tvDishName: TextView = itemView.findViewById(R.id.tvCartDishName)
        val tvDishPrice: TextView = itemView.findViewById(R.id.tvCartDishPrice)
        val tvDishWeight: TextView = itemView.findViewById(R.id.tvCartDishWeight)
        val tvDishCount: TextView = itemView.findViewById(R.id.tvCartDishCount)
        val imDishImage: ImageView = itemView.findViewById(R.id.imCartDish)
        private val btPlus: ImageButton = itemView.findViewById(R.id.imBtPlus)
        private val btMinus: ImageButton = itemView.findViewById(R.id.imBtMinus)

        init {
            btPlus.setOnClickListener {
                plusListener.onItemClick(adapterPosition)
            }

            btMinus.setOnClickListener {
                minuslistener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_basket, parent, false)
        return BasketViewHolder(view, minusBasketListener, plusBasketListener)

    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        val basketData = basketList[position]
        Glide.with(holder.itemView).load(basketData.image).into(holder.imDishImage)
        holder.tvDishName.text = basketData.name
        holder.tvDishCount.text = basketData.count.toString()
        holder.tvDishWeight.text = basketData.weight.toString()
        holder.tvDishPrice.text = basketData.price.toString()
    }

    override fun getItemCount(): Int {
        return basketList.size
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnMinusClickListener(listener: OnItemClickListener) {
        minusBasketListener = listener
    }

    fun setOnPlusBookListener(listener: OnItemClickListener) {
        plusBasketListener = listener
    }
}