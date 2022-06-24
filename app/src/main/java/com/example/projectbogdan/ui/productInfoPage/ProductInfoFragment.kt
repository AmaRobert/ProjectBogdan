package com.example.projectbogdan.ui.productInfoPage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.example.projectbogdan.R
import com.example.projectbogdan.data.model.ShoppingProduct
import com.example.projectbogdan.databinding.FragmentProductInfoBinding
import com.example.projectbogdan.databinding.FragmentStorageBinding
import com.example.projectbogdan.ui.storage.StorageViewModel
import com.example.projectbogdan.ui.storage.StorageViewModelFactory

class ProductInfoFragment : Fragment() {

    companion object {
        fun newInstance() = ProductInfoFragment()
    }

    private lateinit var viewModel: ProductInfoViewModel
    private lateinit var binding : FragmentProductInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this, ProductInfoViewModelFactory())
                .get(ProductInfoViewModel::class.java)

        binding = FragmentProductInfoBinding.inflate(inflater, container, false)
        val bundle = this.arguments
        if(bundle!= null){
            viewModel.loadPage(bundle.get("product") as ShoppingProduct)
        }
        initObservables()
        initListeners()
        return binding.root
    }

    private fun initObservables(){
        viewModel.product.observe(viewLifecycleOwner){
            binding.textViewNameValue.text = it.name
            binding.textViewCategoryValue.text = it.category
            binding.textViewRatingValue.text = it.rating.toString() + "%"
            binding.textViewMentionsValue.setText(it.mentions)
            binding.textViewQuantityValue.setText(it.quantity)
        }
        viewModel.endWithSuccess.observe(viewLifecycleOwner){
            view?.let { it1 -> Navigation.findNavController(it1).popBackStack() }
        }
    }

    private fun initListeners(){
        binding.textViewMentionsValue.doAfterTextChanged {
            viewModel.updateMentionsValue(it.toString())
        }
        binding.textViewQuantityValue.doAfterTextChanged {
            viewModel.updateQuantityValue(it.toString())
        }
        binding.saveButton.setOnClickListener {
            viewModel.saveProduct()
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductInfoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}