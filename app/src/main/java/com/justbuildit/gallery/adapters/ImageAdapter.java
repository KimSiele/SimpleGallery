package com.justbuildit.gallery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.justbuildit.gallery.Imageholder;
import com.justbuildit.gallery.R;
import com.justbuildit.gallery.model.ImageFacer;
import com.justbuildit.gallery.utils.itemClickListener;

import java.util.ArrayList;

import static androidx.core.view.ViewCompat.setTransitionName;

public class ImageAdapter extends RecyclerView.Adapter<Imageholder> {


    public itemClickListener imageClickListener;
    ArrayList<ImageFacer> imageList;
    Context imagecontext;


    public ImageAdapter(ArrayList<ImageFacer> imageList, Context imagecontext, itemClickListener imageClickListener) {
        this.imageList = imageList;
        this.imagecontext = imagecontext;
        this.imageClickListener = imageClickListener;
    }

    @NonNull
    @Override
    public Imageholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(imagecontext).inflate(R.layout.list_images, parent, false);
        return new Imageholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Imageholder holder, final int position) {

        final ImageFacer img = imageList.get(position);

        Glide.with(imagecontext)
                .load(img.getImage_Path())
                .apply(new RequestOptions().fitCenter())
                .into(holder.imageView);

        setTransitionName(holder.imageView, String.valueOf(position) + "_image");
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageClickListener.onImageClicked(holder, position, imageList);
            }
        });


    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

   /* public class Imageholder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView image_title;

        Imageholder(@NonNull View itemView) {
            super(itemView);
            image_title = itemView.findViewById(R.id.imageTitle);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
*/
}
