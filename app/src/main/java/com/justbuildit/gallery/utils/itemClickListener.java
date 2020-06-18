package com.justbuildit.gallery.utils;

import com.justbuildit.gallery.Imageholder;
import com.justbuildit.gallery.model.ImageFacer;

import java.util.ArrayList;

public interface itemClickListener {

    void onImageClicked(Imageholder imageholder, int position, ArrayList<ImageFacer> imageFacerArrayList);

    void onImageClicked(String imageFolderPath, String folderName);
}
