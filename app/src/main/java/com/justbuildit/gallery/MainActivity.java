package com.justbuildit.gallery;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.justbuildit.gallery.adapters.FolderAdapter;
import com.justbuildit.gallery.model.ImageFacer;
import com.justbuildit.gallery.model.ImageFolder;
import com.justbuildit.gallery.utils.itemClickListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements itemClickListener {

    private static final int PERMISION_REQUEST_READ_EXTERNAL_STORAGE = 1;
    TextView empty;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ImageFolder> imgfolds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISION_REQUEST_READ_EXTERNAL_STORAGE);
        }

        recyclerView = findViewById(R.id.recyclerView);
        empty = findViewById(R.id.empty);
        layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        imgfolds = getImagePaths();

        if (imgfolds.isEmpty()) {
            empty.setVisibility(View.VISIBLE);
        } else {
            FolderAdapter folderAdapter = new FolderAdapter(MainActivity.this, imgfolds, this);
            recyclerView.setAdapter(folderAdapter);
        }


    }

    private ArrayList<ImageFolder> getImagePaths() {

        ArrayList<ImageFolder> imagesFolders = new ArrayList<>();
        ArrayList<String> imagePaths = new ArrayList<>();
        Uri imagesUrl = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {android.provider.MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.BUCKET_ID};
        Cursor cursor = this.getContentResolver().query(imagesUrl, projection, null, null, null);

        try {
            if (cursor != null) {
                cursor.moveToFirst();
            }
            do {
                ImageFolder imgFolds = new ImageFolder();
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                String folder = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                String dataPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));

                String folderPaths = dataPath.substring(0, dataPath.lastIndexOf(folder + "/"));
                folderPaths = folderPaths + folder + "/";

                if (!imagePaths.contains(folderPaths)) {
                    imagePaths.add(folderPaths);

                    imgFolds.setPathName(folderPaths);
                    imgFolds.setFolderName(folder);
                    imgFolds.setImg_first(dataPath);
                    imgFolds.addImages();

                    imagesFolders.add(imgFolds);
                } else {
                    for (int i = 0; i < imagesFolders.size(); i++) {
                        if (imagesFolders.get(i).getPathName().equals(folderPaths)) {
                            imagesFolders.get(i).setImg_first(dataPath);
                            imagesFolders.get(i).addImages();
                        }

                    }
                }
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < imagesFolders.size(); i++) {
            Log.d("image folders", imagesFolders.get(i).getFolderName() + "and path =" + imagesFolders.get(i).getPathName() + imagesFolders.get(i).getNumber_of_images());
        }
        return imagesFolders;
    }

    @Override
    public void onImageClicked(Imageholder imgholder, int position, ArrayList<ImageFacer> imageFacerArrayList) {

    }

    @Override
    public void onImageClicked(String imageFolderPath, String folderName) {

        Intent showImages = new Intent(MainActivity.this, DisplayImages.class);
        showImages.putExtra("imagefolderPath", imageFolderPath);
        showImages.putExtra("imagefolderName", folderName);
        startActivity(showImages);

    }

}
