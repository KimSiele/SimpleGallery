package com.justbuildit.gallery;

import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.transition.Fade;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.justbuildit.gallery.adapters.ImageAdapter;
import com.justbuildit.gallery.fragments.BrowseImageFragment;
import com.justbuildit.gallery.model.ImageFacer;
import com.justbuildit.gallery.utils.itemClickListener;

import java.util.ArrayList;

public class DisplayImages extends AppCompatActivity implements itemClickListener {

    RecyclerView image_recycler;
    ArrayList<ImageFacer> folderImages;
    String imageFolderPath;
    ProgressBar loading;
    LinearLayoutManager linearLayoutManager;
    ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_display_images);
//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar();
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        loading = findViewById(R.id.loader);
        image_recycler = findViewById(R.id.image_recycler);
        linearLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        image_recycler.setLayoutManager(linearLayoutManager);
        image_recycler.hasFixedSize();


        String foldername = getIntent().getStringExtra("imagefolderName");
        imageFolderPath = getIntent().getStringExtra("imagefolderPath");
        folderImages = new ArrayList<>();
        actionBar.setTitle(foldername);


        loading.setVisibility(View.VISIBLE);
        folderImages = getAllFolderImages(imageFolderPath);
        image_recycler.setAdapter(new ImageAdapter(folderImages, DisplayImages.this, this));
        loading.setVisibility(View.GONE);


    }

    private ArrayList<ImageFacer> getAllFolderImages(String imageFolderPath) {

        ArrayList<ImageFacer> images = new ArrayList<>();
        Uri allVideosUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.Media.SIZE};

        Cursor cursor = DisplayImages.this.getContentResolver().query(allVideosUri, projection, MediaStore.Images.Media.DATA + " like ? ", new String[]{"%" + imageFolderPath + "%"}, null);

        try {
            if (cursor != null) {
                cursor.moveToFirst();
            }
            do {
                ImageFacer img = new ImageFacer();
                assert cursor != null;
                img.setImage_Name(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)));
                img.setImage_Path(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));
                img.setImage_Size(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)));

                images.add(img);
            } while (cursor.moveToNext());
            cursor.close();
            ArrayList<ImageFacer> reselection = new ArrayList<>();
            for (int i = images.size() - 1; i > -1; i--) {
                reselection.add(images.get(i));
            }
            images = reselection;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return images;

    }

    @Override
    public void onImageClicked(Imageholder imgholder, int position, ArrayList<ImageFacer> imageFacerArrayList) {

        BrowseImageFragment browseImageFragment = BrowseImageFragment.newInstance(imageFacerArrayList, position, DisplayImages.this);

        /*Api check for fading feature
         * */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            browseImageFragment.setEnterTransition(new Fade());
            browseImageFragment.setExitTransition(new Fade());
        }
        getSupportFragmentManager()
                .beginTransaction()
                .addSharedElement(imgholder.imageView, position + "picture")
                .add(R.id.displayImages, browseImageFragment)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onImageClicked(String imageFolderPath, String folderName) {

    }
}
