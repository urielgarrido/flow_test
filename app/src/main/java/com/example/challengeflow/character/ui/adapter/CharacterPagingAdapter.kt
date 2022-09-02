package com.example.challengeflow.character.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challengeflow.R
import com.example.challengeflow.character.model.Character
import com.example.challengeflow.databinding.ItemCharacterBinding

class CharacterPagingAdapter(
    private val clickListener: ItemCharacterClickListener
): PagingDataAdapter<Character, CharacterPagingAdapter.ViewHolder>(CharacterDiffCallback()) {

    inner class ViewHolder(binding: ItemCharacterBinding): RecyclerView.ViewHolder(binding.root) {
        val characterImage = binding.characterItemImageView
        val characterName = binding.characterItemName
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemCharacter = getItem(position)!!
        with(holder) {
            characterName.text = itemCharacter.name

            Glide.with(itemView)
                .load(itemCharacter.image)
                .placeholder(R.drawable.ic_baseline_image_not_supported)
                .into(characterImage)

            itemView.setOnClickListener {
                clickListener.characterClick(itemCharacter)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemView)
    }

}

class CharacterDiffCallback : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem == newItem
    }

}

interface ItemCharacterClickListener {
    fun characterClick(character: Character)
}