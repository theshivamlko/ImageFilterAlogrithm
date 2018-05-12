package com.navoki.imagefilters;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton saveButton;
    private ImageView imageView;
    private Spinner spnrFilter;
    private int WRITE_PERMISSION_REQUEST = 4;
    private AlertDialog alertDialog;
    private Context context;
    private int IMAGE_REQUEST = 1;
    private int CAPTURE_REQUEST = 5;
    private File imageFile;
    private Uri imageUri;
    private Bitmap usersBitmap, newBitmap;
    private ProgressDialog progressDialog;

    private final int GREY_FILTER = 1;
    private final int NEGATIVE_FILTER = 2;
    private final int SEPIA_FILTER = 3;
    private final int GREEN_FILTER = 4;
    private final int GREY_OUT_BARS_FILTER = 5;
    private final int SEPIA_OUT_BARS_FILTER = 6;
    private final int GREY_DIAGONAL_FILTER = 7;
    private final int SEPIA_DIAGONAL_FILTER = 8;
    private final int SKETCH_FILTER = 9;

    private String[] filterName = {"Choose filter", "Grey Filter", "Negative Filter", "Sepia Filter",
            "Green Filter", "Grey Filter Outside Bars", "Sepia Filter Outside Bars", "Grey Diagonal Filter"
            , "Sepia Diagonal Filter", "Sketch Filter"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("FilterApp");

        initialize();


        // asking permission first on marshmallow and above
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}, WRITE_PERMISSION_REQUEST);
                } else
                    imageChooserDialog();
            }
        });


        spnrFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    if (usersBitmap != null) {
                        // Pass the bitmap to apply filter

                        new BitmapFilterTask().execute(position);

                    } else
                        Toast.makeText(context, "Choose image first", Toast.LENGTH_LONG).show();

                }
                spnrFilter.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (newBitmap != null) {

                    File directory = new File(Environment.getExternalStorageDirectory()
                            + File.separator + "MyImageFilter");

                    if (!directory.isDirectory())
                        directory.mkdirs();

                    File file = new File(directory.getAbsoluteFile() + File.separator + System.currentTimeMillis() + ".png");

                    if (!file.exists()) {
                        try {
                            file.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        newBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                        fileOutputStream.close();

                        Toast.makeText(context, "Image Saved in " + directory.getAbsoluteFile(), Toast.LENGTH_LONG).show();

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else
                    Toast.makeText(context, "No bitmap found", Toast.LENGTH_LONG).show();


            }
        });

    }


    // Task in Background as it will interupt UI Thread
    class BitmapFilterTask extends AsyncTask<Integer, Bitmap, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(Integer... integers) {
            switch (integers[0]) {
                case GREY_FILTER:
                    return ImageFilters.setGreyFilter(usersBitmap);
                case NEGATIVE_FILTER:
                    return ImageFilters.setNegativeFilter(usersBitmap);
                case SEPIA_FILTER:
                    return ImageFilters.setSepiaFilter(usersBitmap);
                case GREEN_FILTER:
                    return ImageFilters.setGreenFilter(usersBitmap);
                case GREY_OUT_BARS_FILTER:
                    return ImageFilters.setGreyOutBarsFilter(usersBitmap);
                case SEPIA_OUT_BARS_FILTER:
                    return ImageFilters.setSepiaOutBarsFilter(usersBitmap);
                case GREY_DIAGONAL_FILTER:
                    return ImageFilters.setGreyDiagonalFilter(usersBitmap);
                case SEPIA_DIAGONAL_FILTER:
                    return ImageFilters.setSepiaDiagonalFilter(usersBitmap);
                case SKETCH_FILTER:
                    return ImageFilters.setSketchFilter(usersBitmap);

            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            progressDialog.dismiss();

            if (bitmap != null) {
                newBitmap = bitmap;

                imageView.setImageBitmap(bitmap);

            } else
                Toast.makeText(context, "Something gone wrong", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {

            imageUri = data.getData();
            imageView.setImageURI(imageUri);

            String path = null;
            try {
                path = Utils.getPath(context, imageUri);

                imageFile = new File(path);
                usersBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());


            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == CAPTURE_REQUEST && resultCode == Activity.RESULT_OK) {

            imageView.setImageURI(imageUri);
            usersBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            imageChooserDialog();
    }


    /**
     * Dialog to choose from Camera/Gallery
     */
    private void imageChooserDialog() {
        alertDialog = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Dialog_Alert).create();
        View view1 = View.inflate(context, R.layout.dialog_choose_image, null);

        RelativeLayout camera;
        RelativeLayout gallery;

        camera = (RelativeLayout) view1.findViewById(R.id.camera);
        gallery = (RelativeLayout) view1.findViewById(R.id.gallery);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseFromCamera();
                alertDialog.dismiss();
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chooseFromGallery();
                alertDialog.dismiss();
            }
        });

        alertDialog.setView(view1);

        alertDialog.show();
    }


    /**
     * Choose from capturing image
     */
    private void chooseFromGallery() {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= 19) {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        } else {
            intent.setAction(Intent.ACTION_GET_CONTENT);
        }

        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_REQUEST);
    }


    /**
     * Choose image from Gallery
     */
    private void chooseFromCamera() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        String filename = String.valueOf(System.currentTimeMillis()) + ".jpg";
        imageFile = new File(context.getCacheDir(), filename);

        if (!imageFile.exists())
            try {
                imageFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        imageUri = FileProvider.getUriForFile(context,
                BuildConfig.APPLICATION_ID + ".provider", imageFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAPTURE_REQUEST);
    }


    private void initialize() {

        saveButton = (FloatingActionButton) findViewById(R.id.saveButton);
        imageView = (ImageView) findViewById(R.id.imageView);
        spnrFilter = (Spinner) findViewById(R.id.spnrFilter);
        context = this;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Processing...");
        progressDialog.setCancelable(false);

        ArrayList<String> filterNameList = new ArrayList<>(Arrays.asList(filterName));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, filterNameList);

        spnrFilter.setAdapter(adapter);

    }

}
