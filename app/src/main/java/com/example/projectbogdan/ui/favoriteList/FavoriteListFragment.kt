package com.example.projectbogdan.ui.favoriteList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectbogdan.databinding.FragmentFavoriteListBinding
import com.example.projectbogdan.ui.shopingList.ShoppingListViewModel
import com.example.projectbogdan.ui.shopingList.ShoppingListViewModelFactory

class FavoriteListFragment : Fragment() {

private var _binding: FragmentFavoriteListBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!
    private lateinit var favouriteListAdapter: FavouriteListAdapter
    private lateinit var viewModel: FavoriteViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
      viewModel =
          ViewModelProvider(this, FavouriteListViewModelFactory())
              .get(FavoriteViewModel::class.java)
      _binding = FragmentFavoriteListBinding.inflate(inflater, container, false)

      initObservables()
      initListeners()
      return binding.root
  }

    private fun initObservables(){
        val layoutManager= LinearLayoutManager(activity)
        binding.recyclerviewFavoriteList.layoutManager = layoutManager
        favouriteListAdapter = FavouriteListAdapter(viewModel)
        binding.recyclerviewFavoriteList.adapter = favouriteListAdapter

        viewModel.isShowingProgress.observe(viewLifecycleOwner){
            binding.swipeRefreshIndicator.isRefreshing = it
        }
        viewModel.favouriteList.observe(viewLifecycleOwner){
            favouriteListAdapter.submitList(it)
            favouriteListAdapter.notifyDataSetChanged()
        }
        viewModel.itemToBeAddedToShoppingList.observe(viewLifecycleOwner){
            val size = it.size
            if(size == 0){
                binding.addToShoppingList.isEnabled = false
                binding.addToShoppingList.text = "Add to shopping list"
            }else{
                binding.addToShoppingList.isEnabled = true
                binding.addToShoppingList.text = "Add to shopping list $size products"
            }
        }
    }
    
    private fun initListeners(){
        binding.swipeRefreshIndicator.setOnRefreshListener {
            viewModel.getShoppingListData()
        }
        binding.addToShoppingList.setOnClickListener {
            viewModel.addAllToShoppingList()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}