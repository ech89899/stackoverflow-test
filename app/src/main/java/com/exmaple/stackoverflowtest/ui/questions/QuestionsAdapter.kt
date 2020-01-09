package com.exmaple.stackoverflowtest.ui.questions

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.exmaple.stackoverflowtest.data.models.Question
import com.exmaple.stackoverflowtest.databinding.QuestionsListItemBinding
import java.text.SimpleDateFormat
import java.util.*

class QuestionsAdapter(private var items: MutableList<Question>)
    : RecyclerView.Adapter<QuestionsAdapter.ViewHolder>() {

    private val dateFormat: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.US)

    fun updateItems(newItems: MutableList<Question>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = QuestionsListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(private val binding: QuestionsListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Question) {
            binding.question = item
            binding.questionDate = dateFormat.format(Date(item.creationDate * 1000))
            binding.executePendingBindings()
        }
    }
}
