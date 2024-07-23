package com.bowpi.todolist.ui.todoList

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bowpi.todolist.R
import com.bowpi.todolist.model.TodoItem
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TodoListAdapter(private val todoItems: List<TodoItem>, private val context: Context?) :
    RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewCocktail: ImageView = itemView.findViewById(R.id.imageViewCocktail)
        val textViewTitleCocktail: TextView = itemView.findViewById(R.id.textViewTitleCocktail)
        val textViewInstruccionCocktail: TextView = itemView.findViewById(R.id.textViewInstruccionCocktail)
        val constraintLayoutItemTodo: ConstraintLayout = itemView.findViewById(R.id.constraintLayoutItemTodo)
        val cardViewCheck: CardView = itemView.findViewById(R.id.cardViewCheck)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo, parent, false) // Asegúrate de que el nombre del layout sea correcto
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todoItem = todoItems[position]
        holder.textViewTitleCocktail.text = todoItem.name
        holder.textViewInstruccionCocktail.text = todoItem.description
        Glide.with(holder.itemView.context)
            .load(todoItem.strDrinkThumb)
            .placeholder(R.drawable.ic_launcher_background) // Puedes cambiar esto a un placeholder apropiado
            .into(holder.imageViewCocktail)
        if (todoItem.check) {
            holder.cardViewCheck.visibility = View.VISIBLE
        }else{
            holder.cardViewCheck.visibility = View.GONE
        }
        holder.constraintLayoutItemTodo.setOnClickListener {
            todoItem.check = !todoItem.check
            if (todoItem.check){
                showRatingDialog(todoItem, holder)
            }else{
                holder.cardViewCheck.visibility = View.GONE
                updateCheckStatusInFirebase(todoItem)
            }
        }
    }

    private fun showRatingDialog(todoItem: TodoItem, holder: TodoViewHolder) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_rating, null)
        val editTextRating = dialogView.findViewById<EditText>(R.id.editTextRating)

        val dialog = AlertDialog.Builder(context)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        dialogView.findViewById<View>(R.id.buttonOk).setOnClickListener {
            val rating = editTextRating.text.toString().toIntOrNull()
            if (rating != null && rating in 0..10) {
                todoItem.check = true
                holder.cardViewCheck.visibility = View.VISIBLE
                updateCheckStatusInFirebase(todoItem)
                dialog.dismiss()
            } else {
                editTextRating.error = "Ingrese un valor entre 0 y 10"
            }
        }

        dialog.show()
    }

    private fun updateCheckStatusInFirebase(todoItem: TodoItem) {
        database.child("todos").child(todoItem.idDrink).setValue(todoItem)
    }

    override fun getItemCount(): Int {
        return todoItems.size
    }
}
