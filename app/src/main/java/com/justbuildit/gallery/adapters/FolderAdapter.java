package com.justbuildit.gallery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.justbuildit.gallery.R;
import com.justbuildit.gallery.model.ImageFolder;
import com.justbuildit.gallery.utils.itemClickListener;

import java.util.ArrayList;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.folderHolder> {
    Context foldercontext;
    ArrayList<ImageFolder> folderList;
    private itemClickListener ClickListener;

    public FolderAdapter(Context foldercontext, ArrayList<ImageFolder> folderArrayList, itemClickListener ClickListener) {
        this.foldercontext = foldercontext;
        this.folderList = folderArrayList;
        this.ClickListener = ClickListener;
    }

    @NonNull
    @Override
    public folderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(foldercontext).inflate(R.layout.list_folders, parent, false);
        return new folderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull folderHolder holder, int position) {
        final ImageFolder imageFolder = folderList.get(position);

        Glide.with(foldercontext)
                .load(imageFolder.getImg_first())
                .apply(new RequestOptions().fitCenter())
                .into(holder.folder_background);

        String text = "(" + imageFolder.getNumber_of_images() + ") " + imageFolder.getFolderName();
        holder.folder_title.setText(text);


        holder.folder_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickListener.onImageClicked(imageFolder.getPathName(), imageFolder.getFolderName());
            }
        });

    }

    @Override
    public int getItemCount() {
        return folderList.size();
    }

    class folderHolder extends RecyclerView.ViewHolder {
        ImageView folder_background;
        TextView folder_title;

        public folderHolder(@NonNull View itemView) {
            super(itemView);
            folder_background = itemView.findViewById(R.id.folderView);
            folder_title = itemView.findViewById(R.id.folderTitle);

        }
    }
}
