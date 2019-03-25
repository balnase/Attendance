package com.progresstech.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {

    private TextView textViewUsername, textViewUserEmail;
    private CardView produkCard, mapsCard, calendarCard, orderCard;
    private ImageView profileCard;

  //  Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // getSupportActionBar().setTitle("Dashboard");

        //DEFINING CLASS
        produkCard = (CardView) findViewById(R.id.produk_card);
        mapsCard = (CardView) findViewById(R.id.maps_card);
        profileCard = (ImageView) findViewById(R.id.profile_card);
        calendarCard = (CardView) findViewById(R.id.calendar_card);
        orderCard = (CardView) findViewById(R.id.order_card);

        produkCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent produk = new Intent(DashboardActivity.this,ProductActivity.class);
                startActivity(produk);
            }
        });

        mapsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent maps = new Intent(DashboardActivity.this,MapsActivity.class);
                startActivity(maps);
            }
        });

        profileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profile = new Intent(DashboardActivity.this,ProfileActivity.class);
                startActivity(profile);
            }
        });

        calendarCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent calendar = new Intent(DashboardActivity.this,CalendarActivity.class);
                startActivity(calendar);
            }
        });

        orderCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent order = new Intent(DashboardActivity.this,ImageActivity.class);
                startActivity(order);
            }
        });


        //ADD CLICK LISTENER TO THE CARDS
       // contactCard.setOnClickListener(this);
       // profileCard.setOnClickListener(this);

        //LOGIN
      /*  if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }*/

       // textViewUsername = (TextView) findViewById(R.id.textViewUsername);
      //  textViewUserEmail = (TextView) findViewById(R.id.textViewUseremail);


     //  textViewUserEmail.setText(SharedPrefManager.getInstance(this).getUserEmail());
     //   textViewUsername.setText(SharedPrefManager.getInstance(this).getUsername());

            /*
     // animation floating logout
     b=(Button)findViewById(R.id.fab_1);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation=AnimationUtils.loadAnimation(DashboardActivity.this,R.anim.mixed_anim);

                b.startAnimation(animation);

            }
        });*/

        // LOGOUT
        FloatingActionButton fab = findViewById(R.id.fab_1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // FAB Action goes here
                showPopup();
            }
        });
    }

   /* @Override
    public void onClick(View v){
        Intent i ;

        switch (v.getId()) {
            case R.id.contact_card : i = new Intent (this,ContactActivity.class);startActivity(i);break   ;
            case R.id.profile_card : i = new Intent (this,ProfileActivity.class);startActivity(i);break   ;
        }
    }*/

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

   @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuLogout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
        return true;
    }

    @Override
    public void onCheckedChanged(FloatingActionButton fabView, boolean isChecked) {
        // When a FAB is toggled, log the action.

        switch (fabView.getId()){
            case R.id.fab_1:
                break;
            default:
                break;
        }
    }*/


    // first step helper function
    private void showPopup() {
        AlertDialog.Builder alert = new AlertDialog.Builder(DashboardActivity.this);
        alert.setMessage("Are you sure?")
                .setPositiveButton("Logout", new DialogInterface.OnClickListener()                 {

                    public void onClick(DialogInterface dialog, int which) {

                        logout(); // Last step. Logout function

                    }
                }).setNegativeButton("Cancel", null);

        AlertDialog alert1 = alert.create();
        alert1.show();
    }

    private void logout() {
        SharedPrefManager.getInstance(this).logout();
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }

}
