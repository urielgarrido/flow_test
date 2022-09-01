package com.example.challengeflow.character.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.challengeflow.character.di.RetrofitModule
import com.example.challengeflow.character.model.Character
import com.example.challengeflow.character.repository.CharacterRepository
import com.example.challengeflow.character.ui.adapter.CharacterAdapter
import com.example.challengeflow.character.ui.adapter.ItemCharacterClickListener
import com.example.challengeflow.character.ui.viewmodel.CharacterListViewModel
import com.example.challengeflow.character.ui.viewmodel.CharacterViewModelFactory
import com.example.challengeflow.character.usecase.GetCharactersUseCase
import com.example.challengeflow.databinding.FragmentCharacterListBinding

class CharacterListFragment : Fragment(), ItemCharacterClickListener {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CharacterListViewModel by viewModels {
        val retrofit = RetrofitModule.provideRetrofit()
        val api = RetrofitModule.provideRickAndMortyApi(retrofit)
        CharacterViewModelFactory(GetCharactersUseCase(CharacterRepository(api)))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)

        getCharacters()
        setObservables()

        return binding.root
    }

    private fun getCharacters() {
        viewModel.getCharacters()
    }

    private fun setObservables() {
        viewModel.characters.observe(viewLifecycleOwner) { characters ->
            setupRecyclerView(binding.charactersRecyclerView, characters)
        }

        viewModel.loadingData.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                showProgressLoading(binding.loadingProgressBar)
            } else {
                dismissProgressLoading(binding.loadingProgressBar)
            }
        }
    }

    private fun showProgressLoading(loadingProgressBar: ContentLoadingProgressBar?) {
        loadingProgressBar?.show()
    }

    private fun dismissProgressLoading(loadingProgressBar: ContentLoadingProgressBar?) {
        loadingProgressBar?.hide()
    }

    private fun setupRecyclerView(charactersRecyclerView: RecyclerView, characters: List<Character>) {
        charactersRecyclerView.apply {
            adapter = CharacterAdapter(characters, this@CharacterListFragment)
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.characters.removeObservers(viewLifecycleOwner)
        viewModel.loadingData.removeObservers(viewLifecycleOwner)
        viewModel.error.removeObservers(viewLifecycleOwner)
    }

    override fun characterClick(character: Character) {
        //Se muestra el detalle al lado, si es tablet
        //Se muestra el detalle en otro fragment

        Toast.makeText(context, character.name, Toast.LENGTH_SHORT).show()
    }

}