package ru.fefu.lesson6.room.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.lesson6.R
import ru.fefu.lesson6.room.db.Cat

class CatsAdapter(
    private val clickListener: (Cat) -> Unit
) : ListAdapter<Cat, CatsAdapter.CatHolder>(CatDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_cat, parent, false)
        return CatHolder(view)
    }

    override fun onBindViewHolder(holder: CatHolder, position: Int) =
        holder.bind(currentList[position])

    inner class CatHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvId: TextView = view.findViewById(R.id.tvId)
        private val tvName: TextView = view.findViewById(R.id.tvName)
        private val tvCreatedAt: TextView = view.findViewById(R.id.tvCreatedAt)

        init {
            view.setOnClickListener {
                val position = adapterPosition
                currentList.getOrNull(position)?.let { clickListener.invoke(it) }
            }
        }

        fun bind(cat: Cat) {
            tvId.text = cat.id.toString()
            tvName.text = cat.name
            tvCreatedAt.text = cat.createdAt.toString()
        }
    }

}

private class CatDiffUtilCallback : DiffUtil.ItemCallback<Cat>() {
    override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean =
        oldItem == newItem
}