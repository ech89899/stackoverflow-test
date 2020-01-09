package com.exmaple.stackoverflowtest.ui.tags

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exmaple.stackoverflowtest.R
import com.exmaple.stackoverflowtest.data.models.Tag
import com.exmaple.stackoverflowtest.databinding.TagsListItemBinding

class TagsAdapter(private var items: MutableList<Tag>, private val onTagSelected: (tag: Tag) -> Unit)
    : RecyclerView.Adapter<TagsAdapter.ViewHolder>() {

    val icons: Map<String, Int> = mapOf("android" to R.drawable.android_logo)

    fun updateItems(newItems: MutableList<Tag>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TagsListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(private val binding: TagsListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Tag) {
            binding.tag = item
            binding.imageRes = icons.getOrElse(item.name) { android.R.color.transparent }
            binding.root.setOnClickListener { onTagSelected(item) }
            binding.executePendingBindings()
        }
    }
}
