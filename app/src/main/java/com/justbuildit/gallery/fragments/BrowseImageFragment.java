package com.justbuildit.gallery.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.justbuildit.gallery.R;
import com.justbuildit.gallery.adapters.ImagePagerAdapter;
import com.justbuildit.gallery.model.ImageFacer;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BrowseImageFragment extends Fragment {

    private ArrayList<ImageFacer> allImages = new ArrayList<>();
    private int position;
    private Context context;


    public BrowseImageFragment() {
        // Required empty public constructor
    }

    private BrowseImageFragment(ArrayList<ImageFacer> allImages, int imagePosition, Context context) {
        this.allImages = allImages;
        this.position = imagePosition;
        this.context = context;
    }

    public static BrowseImageFragment newInstance(ArrayList<ImageFacer> allImages, int imagePosition, Context context) {
        return new BrowseImageFragment(allImages, imagePosition, context);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_browse_image, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        assert getActivity() != null;
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        assert getActivity() != null;
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager imagePager = view.findViewById(R.id.imagePager);
        ;
        final Toolbar toolbar = view.findViewById(R.id.tob_bar);
        final BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_bar);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.delete:
                        Toast.makeText(getContext(), allImages.get(position).getImage_Name() + "Deleted", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.edit:
                        Toast.makeText(getContext(), "Edit", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.share:
                        Toast.makeText(getContext(), "Opening share intent", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        ImagePagerAdapter imagesPager = new ImagePagerAdapter(getContext(), allImages);
        imagePager.setAdapter(imagesPager);
//        imagePager.setOffscreenPageLimit(3);
      /*
      displaying image at the current position parsed from display image Activity
      */
        imagePager.setCurrentItem(position);

        imagePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        imagePager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (view.dispatchTouchEvent(motionEvent)) {
                    toolbar.setVisibility(View.VISIBLE);
                    ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
                    toolbar.setTitle(allImages.get(position).getImage_Name());
                    bottomNavigationView.setVisibility(View.VISIBLE);
                } else {
                    toolbar.setVisibility(View.INVISIBLE);
                    bottomNavigationView.setVisibility(View.INVISIBLE);
                }
                return true;
            }
        });


        /*
        TODO
        setup recyclerview  indicator for viewPager
        */
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.image_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String imageName = allImages.get(position).getImage_Name();
        String imagePath = allImages.get(position).getImage_Path();
        String imageSIze = allImages.get(position).getImage_Size();
        switch (item.getItemId()) {
            case R.id.details:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true)
                        .setTitle("Details")
                        .setMessage("Name\n" + imageName + "\n" +
                                "Path\n" + imagePath + "\n" +
                                "Size\n" + imageSIze + "\n")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
                break;
            case R.id.rename:
                AlertDialog.Builder renameBuilder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = this.getLayoutInflater();
                View dialogview = inflater.inflate(R.layout.rename_image, null);
                final EditText currentName = dialogview.findViewById(R.id.editName);
                final EditText extension = dialogview.findViewById(R.id.editExtension);
                TextView txtname = dialogview.findViewById(R.id.txtname);
                TextView txtExtension = dialogview.findViewById(R.id.extension);
                txtname.setText(allImages.get(position).getImage_Path());
                renameBuilder.setCancelable(false)
                        .setTitle("Rename")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//
                                String currentImageName = currentName.getText().toString() + extension.getText().toString();
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
