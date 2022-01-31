package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.myapplication.Rick.Rick
import com.example.myapplication.databinding.ItemRickBinding

class RickAdapter(
private val onRickCliсked: (Rick)->Unit
) : ListAdapter<Rick, RickViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RickViewHolder {
        return RickViewHolder(
            binding = ItemRickBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onRickCliked = onRickCliсked
        )
    }

    override fun onBindViewHolder(holder: RickViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Rick>() {
            override fun areItemsTheSame(oldItem: Rick, newItem: Rick): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Rick, newItem: Rick): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
}