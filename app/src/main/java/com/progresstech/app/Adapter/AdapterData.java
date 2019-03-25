package com.progresstech.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.progresstech.app.InsertProductActivity;
import com.progresstech.app.Model.ModelData;
import com.progresstech.app.ProductDetailActivity;
import com.progresstech.app.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData> {

   // private static final String TAG = "AdapterData";
   // private ArrayList<String> mImageNames = new ArrayList<>();
   // private ArrayList<String> mImages = new ArrayList<>();
   //,  ArrayList<String> imageNames, ArrayList<String> images
    private List<ModelData> mItems ;
    ArrayList<Integer> aHarga;
    private Context context;

    int minteger = 0;
    int sum=0;

    public AdapterData (Context context, List<ModelData> items, ArrayList<Integer> aPrice)
    {
        this.context = context;
        this.mItems = items;
        this.aHarga = aPrice;

       // mImageNames = imageNames;
       // mImages = images;
    }


    @Override
    public HolderData onCreateViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_product,parent,false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(final HolderData holder, final int position) {
       // Log.d(TAG, "onBindViewHolder: called.");

        ModelData md = mItems.get(position);
        holder.tvnama.setText(md.getNama());
        holder.tvharga.setText(md.getHarga());
        holder.tvdiskon.setText(String.valueOf(md.getDiskon()));

      /*  Glide.with(context)
                .asBitmap()
                .load(mItems.get(position))
                .into(holder.imgproduct);*/


        //PRODUCT DETAILS
        holder.imgproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ProductDetailActivity.class);

                intent.putExtra("image_product", mItems.get(position).getGambar());
                intent.putExtra("name_product", mItems.get(position).getNama());
                intent.putExtra("price_product", mItems.get(position).getHarga());
                context.startActivity(intent);
            }
        });

        holder.add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                ModelData order = mItems.get(position);
                String xnilai = mItems.get(position).getQty();
                int nilai = 0;
                if("".equalsIgnoreCase(xnilai)){
                    nilai = 0;
                }else{
                    nilai = Integer.parseInt(mItems.get(position).getQty());
                }

                minteger = nilai + 1;
                order.setQty(String.valueOf(minteger));
                holder.tviteam_amount.setText(mItems.get(position).getQty());
                aHarga.add( mItems.get(position).getDiskon());
                //Toast.makeText(context, "" + String.valueOf(minteger), Toast.LENGTH_LONG).show();
                /*
                for(int i = 0; i < aHarga.size(); i++){
                    sum += aHarga.get(i);
                }

                notifyDataSetChanged();
                final Intent intent = new Intent("message_subject_intent");
                intent.putExtra("name", "" + sum);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                */
            }
        });

        holder.remove_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                ModelData order = mItems.get(position);
                String xnilai = mItems.get(position).getQty();
                int nilai = 0;
                if("".equalsIgnoreCase(xnilai)){
                    minteger = 0;
                }else if("0".equalsIgnoreCase(xnilai)){
                    minteger = 0;
                }else{
                    nilai = Integer.parseInt(mItems.get(position).getQty());
                    minteger = nilai - 1;
                }
                order.setQty(String.valueOf(minteger));
                holder.tviteam_amount.setText(mItems.get(position).getQty());
                aHarga.add( mItems.get(position).getDiskon());
                //Toast.makeText(context, "" + xnilai, Toast.LENGTH_LONG).show();
            }
        });


        holder.md = md;

        Glide.with(context).load(mItems.get(position).getGambar()).placeholder(R.drawable.bgapps).into(holder.imgproduct);

      /*  holder.imgproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.startActivity(new Intent(context,ProductDetailActivity.class));
                Toast.makeText(context, "This is my Toast message!" + mItems.get(position).getGambar() ,
                        Toast.LENGTH_LONG).show();
                Glide.with(context).load(mItems.get(position).getGambar()).placeholder(R.drawable.bgapps).into(holder.imgproduct2);
            }
        });*/
    }

    private void RemoveQty(int pos) {

        aHarga.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, aHarga.size());


    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    class HolderData extends RecyclerView.ViewHolder
    {
        TextView tvnama,tvharga,tvdiskon,tviteam_amount,add_item,remove_item;
        ModelData md;
        ImageView imgproduct, imgproduct2;
        public HolderData (View view)
        {
            super(view);

            tvnama = (TextView) view.findViewById(R.id.nama);
            tvharga = (TextView) view.findViewById(R.id.harga);
            tviteam_amount = (TextView) view.findViewById(R.id.iteam_amount);
            tvdiskon = (TextView) view.findViewById(R.id.diskon);
            imgproduct = (ImageView) view.findViewById(R.id.imgproduct);
            imgproduct2 = (ImageView) view.findViewById(R.id.imgproduct2);
            add_item = (TextView) view.findViewById(R.id.add_item);
            remove_item = (TextView) view.findViewById(R.id.remove_item);

          //  imageName = (TextView) view.findViewById(R.id.name_product);


            tvharga.setPaintFlags(tvharga.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent update = new Intent(context, InsertProductActivity.class);
                    update.putExtra("update",1);
                    update.putExtra("idp",md.getIdp());
                    update.putExtra("nama",md.getNama());
                    update.putExtra("sku",md.getSku());
                    update.putExtra("gambar",md.getGambar());
                    update.putExtra("harga",md.getHarga());
                    update.putExtra("diskon",(String.valueOf(md.getDiskon())));
                    update.putExtra("harga_pokok",md.getHarga_Pokok());

                    context.startActivity(update);
                }
            });
        }
    }

}
