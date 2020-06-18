package com.justbuildit.gallery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.justbuildit.gallery.R;
import com.justbuildit.gallery.model.ImageFacer;

import java.util.ArrayList;

import static androidx.core.view.ViewCompat.setTransitionName;

public class ImagePagerAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    private ArrayList<ImageFacer> imageFacers;

    public ImagePagerAdapter(Context context, ArrayList<ImageFacer> imageFacers) {
        this.context = context;
        this.imageFacers = imageFacers;
    }

    @Override
    public int getCount() {
        return imageFacers.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.pic_browser_pager, container, false);
        ImageView imageView = view.findViewById(R.id.image_view);

        setTransitionName(imageView, String.valueOf(position) + "picture");
        final ImageFacer imageFacer = imageFacers.get(position);
        Glide.with(context)
                .load(imageFacer.getImage_Path())
                .apply(new RequestOptions().fitCenter())
                .into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, imageFacer.getImage_Name(), Toast.LENGTH_SHORT).show();

            }
        });
        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
