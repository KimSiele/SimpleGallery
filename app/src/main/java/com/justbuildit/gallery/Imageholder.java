package com.justbuildit.gallery;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Imageholder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public TextView image_title;

    public Imageholder(@NonNull View itemView) {
        super(itemView);
        image_title = itemView.findViewById(R.id.imageTitle);
        imageView = itemView.findViewById(R.id.imageView);
    }
}
