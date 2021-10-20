package com.example.smoothcommerceassignment.feature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.smoothcommerceassignment.R
import com.example.smoothcommerceassignment.data.Colour
import com.example.smoothcommerceassignment.databinding.GridColorItemBinding

class ColoursAdapter(
    val adapterOnClick: (String) -> Unit,
    val colourLikeClicked: (Colour) -> Unit
) : RecyclerView.Adapter<ColoursAdapter.ColourViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Colour>() {
        override fun areItemsTheSame(oldItem: Colour, newItem: Colour): Boolean {
            return oldItem.hex == newItem.hex // if two objects represent the same item
        }

        // called only if areItemsTheSame(..,..) returns true, to see if object is same, have the contents changed?
        override fun areContentsTheSame(oldItem: Colour, newItem: Colour): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColourViewHolder {
        val binding =
            GridColorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ColourViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ColourViewHolder, position: Int) {
        if (differ.currentList.isEmpty())
            return

        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun updateColoursList(updatedList: MutableList<Colour>) {
        differ.submitList(updatedList)
    }

    inner class ColourViewHolder(private val binding: GridColorItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(colour: Colour) {
            binding.apply {
                tvTitle.text = "Title : ${colour.title}"
                tvHex.text = "Hex : ${colour.hex}"
                imageLike.setImageResource(if (colour.isFavourite) R.drawable.ic_heart else R.drawable.ic_heart_outlined)

                Glide.with(itemView)
                    .load(colour.imageUrl)
                    .into(imageView)

                imageLike.setOnClickListener {
                    val selectedColour = differ.currentList[adapterPosition]
                    selectedColour.isFavourite =
                        differ.currentList[adapterPosition].isFavourite.not()
                    colourLikeClicked(selectedColour)
                    imageLike.setImageResource(if (selectedColour.isFavourite) R.drawable.ic_heart else R.drawable.ic_heart_outlined)
                }

                itemView.setOnClickListener {
                    adapterOnClick(colour.imageUrl)
                }
            }
        }
    }
}