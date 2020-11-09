package com.example.habits.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.habits.GlideApp
import com.example.habits.R
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.fragment_about.view.*


class AboutFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_about, container, false)

        val url = "https://thispersondoesnotexist.com/image"
        GlideApp.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .override(300, 300)
            .centerCrop()
            .transform(CircleCrop())
            .into(view.image)

        return view
    }

}