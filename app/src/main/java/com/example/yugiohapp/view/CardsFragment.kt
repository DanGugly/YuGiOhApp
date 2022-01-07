package com.example.yugiohapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yugiohapp.adapter.CardsAdapter
import com.example.yugiohapp.databinding.FragmentCardsBinding
import com.example.yugiohapp.utils.CardDetailsClick
import com.example.yugiohapp.utils.UIState
import com.example.yugiohapp.viewmodel.CardsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CardsFragment : Fragment(), CardDetailsClick {

    private val binding by lazy {
        FragmentCardsBinding.inflate(layoutInflater)
    }

    private val cardsViewModel : CardsViewModel by viewModel()

    private lateinit var cardsAdapter: CardsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cardsAdapter = CardsAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.cardsRecycler.apply{
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = cardsAdapter
        }
        binding.loading.setOnRefreshListener {
            cardsViewModel.subscribeToCardsInfo()
        }

        cardsViewModel.cardsLiveData.observe(viewLifecycleOwner, ::handleCards)

        cardsViewModel.subscribeToCardsInfo()

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun handleCards(uiState: UIState) {
        when(uiState){
            is UIState.LOADING -> {
                binding.cardsRecycler.visibility = View.GONE
                binding.loading.isRefreshing = true
            }
            is UIState.SUCCESS -> {
                binding.loading.isRefreshing = false
                binding.cardsRecycler.visibility = View.VISIBLE
                cardsAdapter.setCards(uiState.data.data)
            }
            is UIState.ERROR -> {
                binding.cardsRecycler.visibility = View.GONE
                binding.loading.isRefreshing = false
                binding.loading.visibility = View.GONE
                Toast.makeText(requireContext(), "Please try again..", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = CardsFragment()
    }

    override fun moveToDetailsFragment(cardName: String) {
        // We place logic to move to details fragment
    }
}