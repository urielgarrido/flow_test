package com.example.challengeflow.character.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challengeflow.R
import com.example.challengeflow.character.model.Character
import com.example.challengeflow.databinding.ItemCharacterBinding

class CharacterAdapter(
    private val listCharacters: List<Character>,
    private val clickListener: ItemCharacterClickListener
): RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemCharacterBinding): RecyclerView.ViewHolder(binding.root) {
        val characterImage = binding.characterItemImageView
        val characterName = binding.characterItemName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemCharacter = listCharacters[position]
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

    override fun getItemCount(): Int = listCharacters.size
}

interface ItemCharacterClickListener {
    fun characterClick(character: Character)
}
