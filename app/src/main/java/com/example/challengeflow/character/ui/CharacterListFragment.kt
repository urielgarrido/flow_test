package com.example.challengeflow.character.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.challengeflow.character.di.RetrofitModule
import com.example.challengeflow.character.model.Character
import com.example.challengeflow.character.repository.CharacterDataSource
import com.example.challengeflow.character.ui.adapter.CharacterPagingAdapter
import com.example.challengeflow.character.ui.adapter.ItemCharacterClickListener
import com.example.challengeflow.character.ui.viewmodel.CharacterListViewModel
import com.example.challengeflow.character.ui.viewmodel.CharacterViewModelFactory
import com.example.challengeflow.databinding.FragmentCharacterListBinding
import kotlinx.coroutines.launch

class CharacterListFragment : Fragment(), ItemCharacterClickListener {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private var characterPagingAdapter: CharacterPagingAdapter? = null

    private val viewModel: CharacterListViewModel by viewModels {
        val retrofit = RetrofitModule.provideRetrofit()
        val api = RetrofitModule.provideRickAndMortyApi(retrofit)
        CharacterViewModelFactory(
            CharacterDataSource(api)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setObservables()
    }

    private fun initViews() {
        characterPagingAdapter = CharacterPagingAdapter(this)
        binding.charactersRecyclerView.apply {
            adapter = characterPagingAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    private fun setObservables() {
        viewModel.getCharactersPaging().observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {
                characterPagingAdapter?.submitData(it)
            }
            viewModel.changeLoading(false)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.loadingData.removeObservers(viewLifecycleOwner)
    }

    override fun characterClick(character: Character) {
        //Se muestra el detalle al lado, si es tablet
        //Se muestra el detalle en otro fragment

        Toast.makeText(context, character.name, Toast.LENGTH_SHORT).show()
    }

}