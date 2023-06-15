package com.gram.app.ui.gallery.photo

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.gram.app.R
import com.gram.app.databinding.FragmentPhotoBinding
import com.gram.core.android.requireArgs
import com.gram.core.component.ComponentProvider
import com.gram.core.findBuilder
import javax.inject.Inject

class PhotoFragment :
    Fragment(R.layout.fragment_photo),
    ComponentProvider<PhotoBuilder.Component> {

    @Inject
    internal lateinit var photoArgs: PhotoArgs

    private lateinit var binding: FragmentPhotoBinding

    private val component: PhotoBuilder.Component by lazy {
        findBuilder(PhotoBuilder::class).build(
            requireContext(),
            requireArgs(PhotoArgs::class.java),
        )
    }

    override fun requireComponent() = component

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPhotoBinding.bind(view)

        binding.description.text = photoArgs.description
        binding.color.text = photoArgs.color
        binding.likes.text = "${photoArgs.likes}"
        binding.location.text = photoArgs.location.city
        binding.header.clipToOutline = true
        binding.image.clipToOutline = true

        bindImage(binding.header, photoArgs.urls.full)
        bindImage(binding.image, photoArgs.urls.regular)
    }

    private fun bindImage(image: ImageView, url: String) {
        Glide.with(binding.root.context)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(image)
    }
}
