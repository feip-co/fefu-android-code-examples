package ru.fefu.lesson7.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.lesson7.R

class ActivitiesAdapter(
    private val clickListener: (Activity) -> Unit
) : ListAdapter<ListItem, RecyclerView.ViewHolder>(ItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_activity, parent, false)
            ActivityViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_date, parent, false)
            DateViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ActivityViewHolder) {
            val item = currentList[position] as Activity
        } else if (holder is DateViewHolder) {
            val item = currentList[position] as Date
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is Activity -> 0
            is Date -> 1
        }
    }

    inner class ActivityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val btnClickMe = view.findViewById<Button>(R.id.btnClickMe)
        init {
            btnClickMe.setOnClickListener {
                val position = adapterPosition
                if (position in currentList.indices) {
                    clickListener.invoke(currentList[position] as Activity)
                }
            }
        }
    }

    inner class DateViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    private class ItemCallback : DiffUtil.ItemCallback<ListItem>() {
        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
            oldItem is Date && newItem is Date && oldItem == newItem ||
                    oldItem is Activity && newItem is Activity && oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean =
            oldItem == newItem
    }

}