package com.gram.app.ui.gallery.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.gram.app.databinding.ListItemBinding
import com.gram.gallery.model.Photo
import javax.inject.Inject

@PhotosBuilder.Scope
class PhotosAdapter @Inject constructor() :
    ListAdapter<Photo, PhotosAdapter.ViewHolder>(DiffCallback()) {

    var listingAdapterListener: ListingAdapterListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layout = ListItemBinding.inflate(inflater, parent, false)

        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position], listingAdapterListener)
    }

    fun submit(items: List<Photo>) {
        submitList(items)
    }

    class ViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Photo, listingAdapterListener: ListingAdapterListener?) {
            binding.root.clipToOutline = true
            bindImage(item.urls.regular)
            binding.root.setOnClickListener {
                listingAdapterListener?.onItemClicked(item)
            }
        }

        private fun bindImage(url: String) {
            Glide.with(binding.root.context)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.root)
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }
    }

    interface ListingAdapterListener {
        fun onItemClicked(photo: Photo)
    }
}
