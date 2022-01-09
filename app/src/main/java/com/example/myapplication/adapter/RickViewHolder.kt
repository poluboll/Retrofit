package com.example.myapplication.adapter

import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myapplication.ListFragment
import com.example.myapplication.Rick.Rick
import com.example.myapplication.databinding.ItemRickBinding

class RickViewHolder(
    private val binding: ItemRickBinding,
    private val onRickCliked: (Rick) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(rick: Rick) {
        with(binding) {
            image.load(rick.imageUrl)
            textView.text = rick.name
            root.setOnClickListener {
                onRickCliked(rick)
            }
        }
    }
}