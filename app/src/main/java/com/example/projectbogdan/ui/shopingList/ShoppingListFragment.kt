package com.example.projectbogdan.ui.shopingList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectbogdan.R
import com.example.projectbogdan.databinding.FragmentShoppingListBinding
import com.example.projectbogdan.ui.login.RegisterFragment
import com.example.projectbogdan.ui.productInfoPage.ProductInfoFragment

class ShoppingListFragment : Fragment() {

private var _binding: FragmentShoppingListBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!
    private lateinit var shoppingListAdapter: ShoppingListAdapter
    private lateinit var viewModel: ShoppingListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this, ShoppingListViewModelFactory())
                .get(ShoppingListViewModel::class.java)
        _binding = FragmentShoppingListBinding.inflate(inflater, container, false)

        initObservables()
        initListeners()
        return binding.root
    }

    private fun initObservables(){
        val layoutManager= LinearLayoutManager(activity)
        binding.recyclerviewShoppingList.layoutManager = layoutManager
        shoppingListAdapter = ShoppingListAdapter(viewModel)
        binding.recyclerviewShoppingList.adapter = shoppingListAdapter

        viewModel.isShowingProgress.observe(viewLifecycleOwner){
            binding.swipeRefreshIndicator.isRefreshing = it
        }
        viewModel.shoppingList.observe(viewLifecycleOwner){
            shoppingListAdapter.submitList(it)
            shoppingListAdapter.notifyDataSetChanged()
        }
        viewModel.itemToBeAdded.observe(viewLifecycleOwner){
            binding.finishShoppingButton.isEnabled = it.size == shoppingListAdapter.itemCount
        }
        viewModel.infoClicked.observe(viewLifecycleOwner){
            val bundle = Bundle()
            bundle.putSerializable("product", it)
            view?.let { it1 -> Navigation.findNavController(it1).navigate(R.id.action_navigation_shopping_list_to_productInfoFragment, bundle)}
        }
    }

    private fun initListeners(){
        binding.swipeRefreshIndicator.setOnRefreshListener {
            viewModel.getShoppingListData()
        }
        binding.finishShoppingButton.setOnClickListener {
            viewModel.finishShopping()
        }
    }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}