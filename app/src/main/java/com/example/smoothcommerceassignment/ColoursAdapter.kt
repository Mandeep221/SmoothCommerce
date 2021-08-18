package com.example.smoothcommerceassignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smoothcommerceassignment.data.Colour
import com.example.smoothcommerceassignment.databinding.GridColorItemBinding

class ColoursAdapter : RecyclerView.Adapter<ColoursAdapter.ColourViewHolder>() {

    private var coloursList = emptyList<Colour>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColourViewHolder {
        val binding =
            GridColorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ColourViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ColourViewHolder, position: Int) {
        if (coloursList.isEmpty())
            return

        holder.bind(coloursList[position])
    }

    override fun getItemCount(): Int {
        return coloursList.size
    }

    fun updateColoursList(updatedList: List<Colour>) {
        coloursList = updatedList
        notifyDataSetChanged()
    }

    class ColourViewHolder(private val binding: GridColorItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(colour: Colour) {
            binding.apply {
                //colourView.setBackgroundColor()
                tvTitle.text = colour.title
                tvHex.text = colour.hex

                Glide.with(itemView)
                    .load(colour.imageUrl)
                    .into(imageView)
            }
        }
    }
}