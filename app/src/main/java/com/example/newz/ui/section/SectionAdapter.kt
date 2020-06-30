package com.example.newz.ui.section

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newz.databinding.ItemSectionBinding

class SectionAdapter(private val listener: SectionListener) :
    ListAdapter<Section, SectionAdapter.SectionViewHolder>(TopicDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        return SectionViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }

    class SectionViewHolder private constructor(private val binding: ItemSectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Section, listener: SectionListener) {
            binding.apply {
                binding.section = item
                binding.title.isChecked = item.isChecked
                binding.title.setOnClickListener { listener.onSectionClicked(item) }
                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): SectionViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemSectionBinding.inflate(layoutInflater, parent, false)
                return SectionViewHolder(binding)
            }
        }
    }

    class TopicDiffCallback : DiffUtil.ItemCallback<Section>() {
        override fun areItemsTheSame(oldItem: Section, newItem: Section): Boolean {
            return oldItem.type == newItem.type
        }

        override fun areContentsTheSame(oldItem: Section, newItem: Section): Boolean {
            return oldItem == newItem
        }
    }

    interface SectionListener {
        fun onSectionClicked(section: Section)
    }
}
