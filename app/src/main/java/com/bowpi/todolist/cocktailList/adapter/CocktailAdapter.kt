package com.bowpi.todolist.cocktailList.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bowpi.todolist.R
import com.bowpi.todolist.model.Cocktail
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

class CocktailAdapter(private val cocktails: List<Cocktail>) :
    RecyclerView.Adapter<CocktailAdapter.CocktailViewHolder>() {

    class CocktailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageViewCocktail)
        val titleTextView: TextView = itemView.findViewById(R.id.textViewTitleCocktail)
        val instructionsTextView: TextView = itemView.findViewById(R.id.textViewInstruccionCocktail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cocktail, parent, false)
        return CocktailViewHolder(view)
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        val cocktail = cocktails[position]
        holder.titleTextView.text = cocktail.strDrink
        holder.instructionsTextView.text = cocktail.strInstructions
        Glide.with(holder.itemView.context)
            .load(cocktail.strDrinkThumb)
            .apply(RequestOptions().transform(RoundedCorners(32)))
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.baseline_error_24)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return cocktails.size
    }
}