package com.progresstech.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;


public class ProductDetailActivity extends AppCompatActivity {


    private TextView tvtitle,tvprice;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        tvtitle = (TextView) findViewById(R.id.name_product);
        img = (ImageView) findViewById(R.id.img_product);
        tvprice = (TextView) findViewById(R.id.price_product);

        // Recieve data
        Intent intent = getIntent();
        String Title = intent.getExtras().getString("name_product");
        String Image = intent.getExtras().getString("image_product") ;
        String Price = intent.getExtras().getString("price_product") ;

        // Setting values

        tvtitle.setText(Title);
        tvprice.setText(Price);
       // img.setImageResource(image);

        Glide.with(this)
                .asBitmap()
                .load(Image)
                .into(img);

    }
}

  /*  TextView name;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        name = findViewById(R.id.name_product);
        image = findViewById(R.id.img_product);

        Intent intent = getIntent();
        name.setText(intent.getStringExtra("name"));
        image.setImageResource(intent.getIntExtra("image",0));
    }

   private static final String TAG = "ProductDetailActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Log.d(TAG, "onCreate: started.");

        getIncomingIntent();
    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("image_url") && getIntent().hasExtra("image_name")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            String imageUrl = getIntent().getStringExtra("image_url");
            String imageName = getIntent().getStringExtra("image_name");

            setImage(imageUrl, imageName);
        }
    }


    private void setImage(String imageUrl, String imageName){
        Log.d(TAG, "setImage: setting te image and name to widgets.");

        TextView name = findViewById(R.id.name_product);
        name.setText(imageName);

        ImageView image = findViewById(R.id.image);
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(image);
    }
}*/


