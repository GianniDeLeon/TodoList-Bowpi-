package com.bowpi.todolist.ui.todoList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bowpi.todolist.R
import com.bowpi.todolist.databinding.FragmentFirstBinding
import com.bowpi.todolist.model.TodoItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TodoListFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: DatabaseReference
    private lateinit var adapter: TodoListAdapter
    private val todoItems = mutableListOf<TodoItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Inicializar Firebase Database
        database = FirebaseDatabase.getInstance().reference

        binding.recyclerViewTodoList.layoutManager = LinearLayoutManager(context)
        adapter = TodoListAdapter(todoItems,context)
        binding.recyclerViewTodoList.adapter = adapter

        //binding.recyclerViewTodoList.layoutManager
        loadDataFromFirebase()

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.floatingActionButtonOrder.setOnClickListener{
            loadDataOrderFromFirebase()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadDataOrderFromFirebase() {
        database.child("todos").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                todoItems.clear()
                for (dataSnapshot in snapshot.children) {
                    val todoItem = dataSnapshot.getValue(TodoItem::class.java)
                    todoItem?.let { todoItems.add(it) }
                }
                todoItems.sortBy { it.check }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar el error
            }
        })
    }

    private fun loadDataFromFirebase() {
        database.child("todos").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                todoItems.clear()
                for (dataSnapshot in snapshot.children) {
                    val todoItem = dataSnapshot.getValue(TodoItem::class.java)
                    todoItem?.let { todoItems.add(it) }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                // Manejar el error
            }
        })
    }
}