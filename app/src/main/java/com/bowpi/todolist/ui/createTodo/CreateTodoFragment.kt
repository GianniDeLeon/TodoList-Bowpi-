package com.bowpi.todolist.ui.createTodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.bowpi.todolist.R
import com.bowpi.todolist.model.TodoItem
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CreateTodoFragment : Fragment() {
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_todo, container, false)

        database = FirebaseDatabase.getInstance().reference

        val cocktail = arguments?.let { CreateTodoFragmentArgs.fromBundle(it).cocktail }
        val editTextTextName = view.findViewById<EditText>(R.id.editTextTextName)
        editTextTextName.setText(cocktail?.strDrink)
        val editTextTextMultiLineDescription = view.findViewById<EditText>(R.id.editTextTextMultiLineDescription)
        editTextTextMultiLineDescription.setText(cocktail?.strInstructions)
        val buttonGuardar = view.findViewById<Button>(R.id.buttonGuardar)
        buttonGuardar.setOnClickListener{
            val name = editTextTextName.text.toString()
            val description = editTextTextMultiLineDescription.text.toString()
            val strDrinkThumb = cocktail?.strDrinkThumb ?: ""

            if (name.isNotEmpty() && description.isNotEmpty()) {
                val todoItem = TodoItem(
                    idDrink = cocktail?.idDrink ?: "",
                    name = name,
                    description = description,
                    strDrinkThumb = strDrinkThumb,
                    check = false
                )
                saveTodoItem(todoItem)
                Snackbar.make(view, "Guardado con éxito", Snackbar.LENGTH_SHORT).show()
                findNavController().navigate(CreateTodoFragmentDirections.actionCreateTodoFragmentToFirstFragment())
            } else {
                Snackbar.make(view, "Por favor, completa todos los campos", Snackbar.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun saveTodoItem(todoItem: TodoItem) {
        database.child("todos").child(todoItem.idDrink).setValue(todoItem)
    }
}