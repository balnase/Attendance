package com.progresstech.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.progresstech.app.Adapter.AdapterData;
import com.progresstech.app.Model.ModelData;
import com.progresstech.app.Util.AppController;
import com.progresstech.app.Util.ServerAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    RecyclerView mRecyclerview;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    List<ModelData> mItems;
    Button btnInsert, btnDelete;
    ProgressDialog pd;

    private SearchView searchViewQuery;
    private NestedScrollView nestedScrollView;
    private FloatingActionButton fab;
    private ImageButton imageViewSearchMenu;
    private CoordinatorLayout cordinatorLayoutActivityA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchViewQuery = (SearchView)findViewById(R.id.searchViewQuery);
        imageViewSearchMenu = (ImageButton) findViewById(R.id.imageViewSearchMenu);
        cordinatorLayoutActivityA = (CoordinatorLayout) findViewById(R.id.cordinatorLayoutActivityA);
        nestedScrollView=(NestedScrollView)findViewById(R.id.nestedScrollView);

        EditText searchEditText = (EditText) searchViewQuery.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(ResourcesCompat.getColor(getResources(),R.color.colorPrimaryText,null));
        searchEditText.setHintTextColor(ResourcesCompat.getColor(getResources(),R.color.colorSecondaryText,null));

        ImageView searchImage = (ImageView) searchViewQuery.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        searchImage.setVisibility(View.GONE);
        ViewGroup linearLayoutSearchView =(ViewGroup) searchImage.getParent();
        linearLayoutSearchView.removeView(searchImage);
        linearLayoutSearchView.addView(searchImage);
        searchImage.setAdjustViewBounds(true);
        searchImage.setMaxWidth(0);
        searchImage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        searchImage.setImageDrawable(null);

        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerviewTemp);
        btnInsert = (Button) findViewById(R.id.btn_insert);
        btnDelete = (Button) findViewById(R.id.btn_delete);
        pd = new ProgressDialog(ContactActivity.this);
        mItems = new ArrayList<>();

        loadJson();
/*
        mManager = new LinearLayoutManager(ContactActivity.this,LinearLayoutManager.VERTICAL,false);
        mRecyclerview.setLayoutManager(mManager);
        mAdapter = new AdapterData(ContactActivity.this,mItems);
        mRecyclerview.setAdapter(mAdapter);*/

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactActivity.this,InsertProductActivity.class);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hapus = new Intent(ContactActivity.this,InsertProductActivity.class);
                startActivity(hapus);
            }
        });

    }

    public void PopupShowMenu(View view){
        PopupMenu popup = new PopupMenu(this, imageViewSearchMenu);
        popup.getMenuInflater().inflate(R.menu.menu_main, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int i = item.getItemId();
                if(i == R.id.action_settings){
                    SnackBarMessage("Click");
                    return true;
                }else{
                    return onMenuItemClick(item);
                }



            }
        });
        popup.show();
    }

    public void SnackBarMessage(String message){
        Snackbar.make(cordinatorLayoutActivityA, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadJson()
    {

        pd.setMessage("Mengambil Data");
        pd.setCancelable(false);
        pd.show();

        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.POST, ServerAPI.URL_DATA_PRODUCT, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        pd.cancel();
                        Log.d("volley","response : " + response.toString());
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                ModelData md = new ModelData();
                                md.setIdp(data.getString("idp"));
                                md.setNama(data.getString("nama"));
                                md.setSku(data.getString("sku"));
                                md.setGambar(data.getString("gambar"));
                                mItems.add(md);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Log.d("volley", "error : " + error.getMessage());
                    }
                });

        AppController.getInstance().addToRequestQueue(reqData);
    }
}
