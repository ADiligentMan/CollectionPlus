package com.example.acer.collectionplus.Helper;

import android.databinding.BindingConversion;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class BindingAdapter {

    @android.databinding.BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView iv, String url) {
        Glide.with(iv.getContext()).load(url).into(iv);
    }
}
