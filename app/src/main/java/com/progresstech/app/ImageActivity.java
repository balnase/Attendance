package com.progresstech.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.progresstech.app.Util.ServerAPI;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.model.AspectRatio;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static android.support.v4.content.FileProvider.getUriForFile;

public class ImageActivity extends AppCompatActivity {

    String userID, username;
    int currentapiVersion = android.os.Build.VERSION.SDK_INT;
    ProgressDialog prg_L;
    Button captureBtn, save_album;
    ImageView picView;
    ArrayList<String> encodedImageList = new ArrayList<>();
    String GetUrlUpload;
    private ProgressDialog dialog = null;

    //crop variable
    public static String fileName;
    private JSONObject jsonObject = new JSONObject();;
    final int CODE_IMG_GALLERY = 1;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);


        captureBtn  = (Button) findViewById(R.id.capture_btn);
        save_album  = (Button) findViewById(R.id.save_album);
        picView     = (ImageView) findViewById(R.id.picture);


    /*    dialog = prg_L;
        dialog.setMessage("Saving Image...");
        dialog.setCancelable(false);*/


        save_album.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                dialog.show();

                SaveToAlbum();

            }

        });


        captureBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                fileName = System.currentTimeMillis() + ".jpg";
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, getCacheImagePath(fileName));

                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

                    startActivityForResult(takePictureIntent, CODE_IMG_GALLERY);

                }

            }

        });


    }

    private void SaveToAlbum() {

        JSONArray jsonArray = new JSONArray();

        if (encodedImageList.isEmpty()){

            Toast.makeText(ImageActivity.this, "Please select some images first.", Toast.LENGTH_SHORT).show();
            dialog.dismiss();

            return;
        }

        for (String encoded: encodedImageList){

            jsonArray.put(encoded);
        }

        try {

            jsonObject.put(ServerAPI.imageList, jsonArray);
            jsonObject.put("userID", userID);

        } catch (JSONException e) {

            Log.e("JSONObject Here", e.toString());
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, GetUrlUpload, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        Log.e("Message from server", jsonObject.toString());

                        Toast.makeText(ImageActivity.this, "Images Saved", Toast.LENGTH_SHORT).show();

                        encodedImageList.clear();

                        picView.setImageResource(R.drawable.ic_camera_alt_black_24dp);

                        dialog.dismiss();

                        ImageActivity.clearCache(ImageActivity.this);

                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Log.e("Message from server", volleyError.toString());

                Toast.makeText(ImageActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();

                dialog.dismiss();

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy( 5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(Objects.requireNonNull(ImageActivity.this)).add(jsonObjectRequest);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODE_IMG_GALLERY && resultCode == RESULT_OK) {


            startCrop(getCacheImagePath(fileName));

        }

        if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK && data != null){

            final Uri imageUriResultCrop = UCrop.getOutput(data);

            if(imageUriResultCrop != null){


                picView.setImageURI(imageUriResultCrop);


                Bitmap bitmap = null;

                try {

                    bitmap = MediaStore.Images.Media.getBitmap(Objects.requireNonNull(ImageActivity.this).getContentResolver(), imageUriResultCrop);

                } catch (IOException e) {

                    e.printStackTrace();
                }

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

                String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

                encodedImageList.add(encodedImage);

            }

        }

    }


    private Uri getCacheImagePath(String fileName) {

        File path = new File(getExternalCacheDir(), "camera");
        if (!path.exists()) path.mkdirs();
        File image = new File(path, fileName);

        return getUriForFile(ImageActivity.this, getPackageName() + ".provider", image);
    }

    public static void clearCache(Context context) {

        File path = new File(context.getExternalCacheDir(), "camera");
        if (path.exists() && path.isDirectory()) {
            for (File child : path.listFiles()) {
                child.delete();
            }
        }
    }


    public void startCrop (@NonNull Uri uri) {

        String destinationFileName = String.valueOf(System.currentTimeMillis()) + ".jpg";

        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), destinationFileName)));

        uCrop.withOptions(getCropOptions());

        //uCrop.start(getContext(), this, UCrop.REQUEST_CROP);



    }

    private UCrop.Options getCropOptions() {

        UCrop.Options options = new UCrop.Options();

        options.setCompressionQuality(70);
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setHideBottomControls(false);
        options.setFreeStyleCropEnabled(true);

        options.setAspectRatioOptions(2,

                new AspectRatio("1:2", 1, 2),
                new AspectRatio("3:4", 3, 4),
                new AspectRatio("Original", 1, 1),
                new AspectRatio("5:3", 5, 3),
                new AspectRatio("16:9", 16, 9)

        );

        options.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        options.setToolbarColor(getResources().getColor(R.color.colorPrimaryDark));
        options.setToolbarTitle("Edit Photo");

        return options;

    }

}