package com.progresstech.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.progresstech.app.Util.AppController;
import com.progresstech.app.Util.ServerAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InsertProductActivity extends AppCompatActivity {


    EditText idp,nama,sku,gambar,harga,diskon,harga_pokok;
    Button btnbatal,btnsimpan;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_product);

        /*get data from intent*/
        Intent data = getIntent();
        final int update = data.getIntExtra("update",0);
        String intent_idp = data.getStringExtra("idp");
        String intent_nama = data.getStringExtra("nama");
        String intent_sku = data.getStringExtra("sku");
        String intent_gambar = data.getStringExtra("gambar");
        String intent_harga = data.getStringExtra("harga");
        String intent_diskon = data.getStringExtra("diskon");
        String intent_harga_pokok = data.getStringExtra("harga_pokok");
        /*end get data from intent*/

        idp = (EditText) findViewById(R.id.inp_idp);
        nama = (EditText) findViewById(R.id.inp_nama);
        sku = (EditText) findViewById(R.id.inp_sku);
        gambar= (EditText) findViewById(R.id.inp_gambar);
        harga= (EditText) findViewById(R.id.inp_harga);
        diskon= (EditText) findViewById(R.id.inp_diskon);
        harga_pokok= (EditText) findViewById(R.id.inp_harga_pokok);
        btnbatal = (Button) findViewById(R.id.btn_cancel);
        btnsimpan = (Button) findViewById(R.id.btn_simpan);
        pd = new ProgressDialog(InsertProductActivity.this);

        /*kondisi update / insert*/
        if(update == 1)
        {
            btnsimpan.setText("Update Data");
            idp.setText(intent_idp);
            idp.setVisibility(View.GONE);
            nama.setText(intent_nama);
            sku.setText(intent_sku);
            gambar.setText(intent_gambar);
            harga.setText(intent_harga);
            diskon.setText(String.valueOf(intent_diskon));
            harga_pokok.setText(intent_harga_pokok);


        }


        btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(update == 1)
                {
                    Update_data();
                }else {
                    simpanData();
                }
            }
        });

        btnbatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(InsertProductActivity.this,ProductActivity.class);
                startActivity(main);
            }
        });
    }

    private void Update_data()
    {
        pd.setMessage("Update Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest updateReq = new StringRequest(Request.Method.POST, ServerAPI.URL_UPDATE_PRODUCT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(InsertProductActivity.this, "pesan : "+   res.getString("message") , Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        startActivity( new Intent(InsertProductActivity.this,ProductActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Toast.makeText(InsertProductActivity.this, "pesan : Gagal Insert Data", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("idp",idp.getText().toString());
                map.put("nama",nama.getText().toString());
                map.put("sku",sku.getText().toString());
                map.put("gambar",gambar.getText().toString());
                map.put("harga",harga.getText().toString());
                map.put("diskon",diskon.getText().toString());
                map.put("harga_pokok",harga_pokok.getText().toString());

                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(updateReq);
    }



    private void simpanData()
    {

        pd.setMessage("Menyimpan Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest sendData = new StringRequest(Request.Method.POST, ServerAPI.URL_INSERT_PRODUCT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(InsertProductActivity.this, "pesan : "+   res.getString("message") , Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        startActivity( new Intent(InsertProductActivity.this,ProductActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Toast.makeText(InsertProductActivity.this, "pesan : Gagal Insert Data", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("idp",idp.getText().toString());
                map.put("nama",nama.getText().toString());
                map.put("sku",sku.getText().toString());
                map.put("gambar",gambar.getText().toString());
                map.put("harga",harga.getText().toString());
                map.put("diskon",diskon.getText().toString());
                map.put("harga_pokok",harga_pokok.getText().toString());

                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(sendData);
    }
}
