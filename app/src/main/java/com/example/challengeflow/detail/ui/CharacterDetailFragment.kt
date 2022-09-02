package com.example.challengeflow.detail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.challengeflow.R
import com.example.challengeflow.character.model.Character
import com.example.challengeflow.databinding.FragmentCharacterDetailBinding
import com.example.challengeflow.detail.repository.CharacterDetailRepository
import com.example.challengeflow.detail.ui.viewmodel.CharacterDetailViewModel
import com.example.challengeflow.detail.ui.viewmodel.CharacterDetailViewModelFactory
import com.example.challengeflow.detail.usecases.GetFirstCharacterUseCase
import com.example.challengeflow.retrofit.RetrofitModule

class CharacterDetailFragment : Fragment() {

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterDetailViewModel by viewModels {
        val retrofit = RetrofitModule.provideRetrofit()
        val api = RetrofitModule.provideRickAndMortyApi(retrofit)
        val repository = CharacterDetailRepository(api)
        CharacterDetailViewModelFactory(GetFirstCharacterUseCase(repository))
    }

    private lateinit var characterDetail: Character

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_CHARACTER)) {
                characterDetail = requireArguments().getParcelable(ARG_CHARACTER)!!
            }
        } ?: run {
            getFirstCharacterDetail()
        }
    }

    private fun getFirstCharacterDetail() {
        viewModel.getFirstCharacter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        val rootView = binding.root

        updateContent()

        return rootView
    }

    private fun updateContent() {
        if (::characterDetail.isInitialized) {
            updateView()
        } else {
            viewModel.character.observe(viewLifecycleOwner) { character ->
                character?.let {
                    characterDetail = it
                    updateView()
                } ?: run {
                    showErrorToast()
                }
            }
        }
    }

    private fun showErrorToast() {
        Toast.makeText(context, getString(R.string.error_detail), Toast.LENGTH_SHORT).show()
    }

    private fun updateView() {
        Glide.with(this)
            .load(characterDetail.image)
            .placeholder(R.drawable.ic_baseline_image_not_supported)
            .into(binding.characterDetailImage)

        binding.apply {
            characterName.text = getString(R.string.name, characterDetail.name)
            characterStatus.text = getString(R.string.status, characterDetail.status)
            characterSpecies.text = getString(R.string.species, characterDetail.species)
            characterGender.text = getString(R.string.gender, characterDetail.gender)
        }
    }

    companion object {
        const val ARG_CHARACTER = "character"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}