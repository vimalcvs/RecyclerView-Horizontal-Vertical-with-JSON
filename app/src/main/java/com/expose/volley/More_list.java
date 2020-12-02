package com.expose.volley;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class More_list extends AppCompatActivity {
    RecyclerView recyclerview;
    ProductAdapter mAdapter;
    List<Model> mmDataList = new ArrayList<>();
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        recyclerview = (RecyclerView)findViewById(R.id.recyclerview);

        mmDataList= (List<Model>) getIntent().getExtras().getSerializable("DATALIST");
        pos =getIntent().getExtras().getInt("Position");

        Log.d("VVV", String.valueOf(mmDataList.size()));

        mAdapter =new ProductAdapter(getApplicationContext(),mmDataList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(mAdapter);
    }

    private class ProductAdapter extends RecyclerView.Adapter<MyviewHolder> {
        Context context;
        List<Model> mDataList;

        public ProductAdapter(Context context, List<Model> mDataList) {

            this.context=context;
            this.mDataList=mDataList;


        }

        @Override
        public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View layoutview = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_large,parent,false);

            return  new MyviewHolder(layoutview);


        }






        @Override
        public void onBindViewHolder(final MyviewHolder holder, final int position) {



            /**
             * according to the position of the first item  getting the first all list and passing to the horizontal recycler views**/

            Model m =mDataList.get(position);
              /*  m.setURL(mDataList.get(position).getURL());
                m.setNAME(mDataList.get(position).getNAME());
                m.setTIMESTAMP(mDataList.get(position).getTIMESTAMP());*/

            holder.dob.setText(m.getTIMESTAMP());
            holder.name.setText(m.getNAME());
            Glide.with(getApplicationContext()).load(m.getURL())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.image);

            holder.image.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {

                    Log.d("PPP", String.valueOf(mDataList.size()));
                    Log.d("PPP", String.valueOf(position));
                    Intent intent = new Intent(More_list.this, ScrollingActivity.class).putExtra("wall", mDataList.get(position).getURL()).putExtra("name", mDataList.get(position).getNAME());
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(More_list.this,holder.image,"vineesh_trans");

                    v.getContext().startActivity(intent, options.toBundle());

                }
            });
        }



        @Override
        public int getItemCount() {
            //4 rows
            return mDataList.size();
        }
    }

    private class MyviewHolder extends RecyclerView.ViewHolder {


        TextView name,description,dob;
        ImageView image;
        public MyviewHolder(View itemView) {

            super(itemView);

            name = (TextView)itemView.findViewById(R.id.name);
            description = (TextView)itemView.findViewById(R.id.description);
            dob = (TextView)itemView.findViewById(R.id.dob);
            image = (ImageView)itemView.findViewById(R.id.image);




        }
    }
}
