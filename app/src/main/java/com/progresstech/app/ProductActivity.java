package com.progresstech.app;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
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
import android.widget.TextView;

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

public class ProductActivity extends AppCompatActivity {

    RecyclerView mRecyclerview;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    List<ModelData> mItems;
    ArrayList<Integer> aHarga;

    int sum=0;
    TextView tv_total;

    Button btnInsert, btnDelete, btn_placeorder;
    ProgressDialog pd;

    private SearchView searchViewQuery;
    private NestedScrollView nestedScrollView;
    private FloatingActionButton fab;
    private ImageButton imageViewSearchMenu;
    private LinearLayout cordinatorLayoutActivityA;

    //private static final String TAG = "ProductActivity";

    //vars
   // private ArrayList<String> mNames = new ArrayList<>();
   // private ArrayList<String> mImageUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
      //  Log.d(TAG, "onCreate: started.");

       // initImageBitmaps();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchViewQuery = (SearchView)findViewById(R.id.searchViewQuery);
        imageViewSearchMenu = (ImageButton) findViewById(R.id.imageViewSearchMenu);
        cordinatorLayoutActivityA = (LinearLayout) findViewById(R.id.cordinatorLayoutActivityA);

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
        tv_total = (TextView) findViewById(R.id.tv_total);
       // btn_placeorder = (Button) findViewById(R.id.btn_placeorder);

        pd = new ProgressDialog(ProductActivity.this);
        mItems = new ArrayList<>();
        aHarga = new ArrayList<>();
      //  mNames = new ArrayList<>();
      //  mImageUrls = new ArrayList<>();


        loadJson();

        mManager = new LinearLayoutManager(ProductActivity.this,LinearLayoutManager.VERTICAL,false);
        mRecyclerview.setLayoutManager(mManager);
        mAdapter = new AdapterData(ProductActivity.this,mItems,aHarga);
        mRecyclerview.setAdapter(mAdapter);
        //,mNames,mImageUrls


  /*      btn_placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i < aHarga.size(); i++){
                    sum += aHarga.get(i);
                    mAdapter.notifyDataSetChanged();
                }
                tv_total.setText(String.valueOf(sum));
            }
        });*/

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("message_subject_intent"));

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductActivity.this,InsertProductActivity.class);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hapus = new Intent(ProductActivity.this,DeleteProductActivity.class);
                startActivity(hapus);
            }
        });

    }

  /*  private void initImageBitmaps(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImageUrls.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        mNames.add("Havasu Falls");

        mImageUrls.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mNames.add("Trondheim");

        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mNames.add("Portugal");

        mImageUrls.add("https://i.redd.it/j6myfqglup501.jpg");
        mNames.add("Rocky Mountain National Park");


        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mNames.add("Mahahual");

        mImageUrls.add("https://i.redd.it/k98uzl68eh501.jpg");
        mNames.add("Frozen Lake");


        mImageUrls.add("https://i.redd.it/glin0nwndo501.jpg");
        mNames.add("White Sands Desert");

        mImageUrls.add("https://i.redd.it/obx4zydshg601.jpg");
        mNames.add("Austrailia");

        mImageUrls.add("https://i.imgur.com/ZcLLrkY.jpg");
        mNames.add("Washington");

        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.recyclerviewTemp);
        AdapterData adapter = new AdapterData(this,mItems,aHarga);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }*/

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
                                md.setHarga(data.getString("harga"));
                                md.setDiskon(data.getInt("diskon"));
                                md.setHarga_pokok(data.getString("harga_pokok"));
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


    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String name = intent.getStringExtra("name");

            tv_total.setText(name);



        }
    };

}
