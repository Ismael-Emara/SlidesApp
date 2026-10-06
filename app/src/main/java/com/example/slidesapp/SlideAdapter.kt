package com.example.slidesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.slidesapp.databinding.ItemSlideBinding

class SlideAdapter(private val slides: List<Slide>) : RecyclerView.Adapter<SlideAdapter.SlideViewHolder>() {

    class SlideViewHolder(private val binding: ItemSlideBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(slide: Slide, position: Int) {
            binding.slideRoot.setBackgroundColor(slide.backgroundColor)
            binding.tvSlideTitle.text = slide.title
            binding.tvSlideContent.text = slide.content
            binding.ivSlideIcon.setImageResource(slide.iconRes)

            slide.actionText?.let { text ->
                binding.btnSlideAction.text = text
                binding.btnSlideAction.visibility = View.VISIBLE
                binding.btnSlideAction.setOnClickListener {
                    slide.actionListener?.invoke()
                }
            } ?: run {
                binding.btnSlideAction.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideViewHolder {
        val binding = ItemSlideBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SlideViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SlideViewHolder, position: Int) {
        holder.bind(slides[position], position)
    }

    override fun getItemCount(): Int = slides.size
}