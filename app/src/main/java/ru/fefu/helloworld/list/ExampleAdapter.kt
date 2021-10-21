package ru.fefu.helloworld.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.helloworld.R

class ExampleAdapter(
    cats: List<Cat>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ITEM_TYPE_CAT = 1
        private const val ITEM_TYPE_CAT_2 = 2
    }

    override fun getItemViewType(position: Int): Int =
        if (position % 2 == 0) ITEM_TYPE_CAT_2
        else ITEM_TYPE_CAT

    private val mutableCats = cats.toMutableList()

    private var itemClickListener: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE_CAT) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
            ExampleViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.list_item_other_cat, parent, false)
            Cat2ViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == ITEM_TYPE_CAT_2) {
            ((holder as Cat2ViewHolder).bind(mutableCats[position]))
        } else {
            ((holder as ExampleViewHolder).bind(mutableCats[position]))
        }
    }

    fun addCat(cat: Cat) {
        mutableCats.add(cat)
        notifyItemInserted(mutableCats.size - 1)
    }

    fun removeCat(position: Int) {
        if (position in mutableCats.indices) {
            mutableCats.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun setItemClickListener(listener: (Int) -> Unit) {
        itemClickListener = listener
    }

    inner class ExampleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivCat: ImageView = itemView.findViewById(R.id.ivCat)
        private val tvCatName: TextView = itemView.findViewById(R.id.tvCatName)
        private val tvCatDescription: TextView = itemView.findViewById(R.id.tvCatDescription)
        private val tvIndex: TextView = itemView.findViewById(R.id.tvIndex)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) itemClickListener.invoke(position)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(cat: Cat) {
            ivCat.setImageResource(cat.image)
            tvCatName.text = cat.name
            tvCatDescription.text = cat.description
            tvIndex.text = "#${cat.id + 1}"
        }
    }

    inner class Cat2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivCat: ImageView = itemView.findViewById(R.id.ivCat)
        private val tvCatName: TextView = itemView.findViewById(R.id.tvCat)
        private val tvIndex: TextView = itemView.findViewById(R.id.tvIndex)

        @SuppressLint("SetTextI18n")
        fun bind(cat: Cat) {
            ivCat.setImageResource(cat.image)
            tvCatName.text = cat.name
            tvIndex.text = "#${cat.id + 1}"
        }
    }

    override fun getItemCount(): Int = mutableCats.size

}