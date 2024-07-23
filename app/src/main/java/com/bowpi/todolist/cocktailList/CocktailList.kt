package com.bowpi.todolist.cocktailList

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bowpi.todolist.cocktailList.adapter.CocktailAdapter
import com.bowpi.todolist.model.CocktailResponse
import com.bowpi.todolist.databinding.FragmentSecondBinding
import com.bowpi.todolist.services.repository.CocktailRepository
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class CocktailList : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val repository = CocktailRepository()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewCocktails.layoutManager = LinearLayoutManager(context)
        binding.imageViewSearch.setOnClickListener(){
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.editTextTextSearch.windowToken, 0)

            if (binding.editTextTextSearch.text.isNotEmpty()){
                searchCocktails(binding.editTextTextSearch.text.toString())
            }else{
                Snackbar.make(view,"Escribe algo en la busqueda",Snackbar.LENGTH_SHORT).show()
            }
        }
        searchCocktails("margarita")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun searchCocktails(search: String){
        repository.searchCocktails(search) { cocktails ->
            val adapter = CocktailAdapter(cocktails)
            binding.recyclerViewCocktails.adapter = adapter
        }
    }
}