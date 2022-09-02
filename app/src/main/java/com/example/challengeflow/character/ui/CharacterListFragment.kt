package com.example.challengeflow.character.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.example.challengeflow.R
import com.example.challengeflow.character.model.Character
import com.example.challengeflow.character.repository.CharacterDataSource
import com.example.challengeflow.character.ui.adapter.CharacterPagingAdapter
import com.example.challengeflow.character.ui.adapter.ItemCharacterClickListener
import com.example.challengeflow.character.ui.viewmodel.CharacterListViewModel
import com.example.challengeflow.character.ui.viewmodel.CharacterViewModelFactory
import com.example.challengeflow.databinding.FragmentCharacterListBinding
import com.example.challengeflow.detail.ui.CharacterDetailFragment
import com.example.challengeflow.retrofit.RetrofitModule
import kotlinx.coroutines.launch

class CharacterListFragment : Fragment(), ItemCharacterClickListener {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private var characterPagingAdapter: CharacterPagingAdapter? = null

    private var charactersPagingData: PagingData<Character>? = null

    private val viewModel: CharacterListViewModel by viewModels {
        val retrofit = RetrofitModule.provideRetrofit()
        val api = RetrofitModule.provideRickAndMortyApi(retrofit)
        CharacterViewModelFactory(
            CharacterDataSource(api)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        characterPagingAdapter = CharacterPagingAdapter(this)
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
        binding.charactersRecyclerView.apply {
            adapter = characterPagingAdapter
            layoutManager = GridLayoutManager(context, 2)
        }

    }

    private fun setObservables() {
        viewModel.getCharactersPaging().observe(viewLifecycleOwner) {
            viewLifecycleOwner.lifecycleScope.launch {
                if (charactersPagingData == null) {
                    charactersPagingData = it
                }
                characterPagingAdapter?.submitData(charactersPagingData!!)
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
        val bundle = bundleOf(
            CharacterDetailFragment.ARG_CHARACTER to character
        )
        //Se muestra el detalle al lado, si es tablet
        if (binding.characterDetailNavContainer != null) {
            binding.characterDetailNavContainer!!.findNavController()
                .navigate(R.id.fragment_character_detail, bundle)
        } else {
            //Se muestra el detalle en otro fragment
            findNavController().navigate(R.id.show_item_detail, bundle)
        }

    }

}