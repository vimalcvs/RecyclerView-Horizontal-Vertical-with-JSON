package com.expose.volley;

import android.app.ProgressDialog;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerview;
    ProductAdapter mAdapter;

    List<List<Model>> AOTHERLIST;
    List<Model> mmDataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setIcon(R.drawable.grid);


        AOTHERLIST = new ArrayList<List<Model>>();

        recyclerview = (RecyclerView)findViewById(R.id.recyclerview);


        prepare_Data();




    }

    private void prepare_Data() {

/**
 * data reading from .json file from the asset folder
 * not from the URL **/
        JSONArray array=null;
        JSONArray array2 =null;
        try {

            array = new JSONArray(loadJSONFromAsset());//4

            for (int i = 0;i<array.length();i++)//4
            {
                JSONObject object1 = array.getJSONObject(i);//1
                 array2 =object1.getJSONArray("items");//12

                 mmDataList =new ArrayList<>();

                for (int j=0;j<array2.length();j++)//12
                {
                    Model m = new Model();
                    JSONObject ob=array2.getJSONObject(j);

                    m.setNAME(ob.getString("name"));
                    m.setTIMESTAMP(ob.getString("timestamp"));
                    JSONObject ob2=ob.getJSONObject("url");
                    m.setURL(ob2.getString("large"));


                    mmDataList.add(m);//12

                }

                AOTHERLIST.add(mmDataList);//4


            }






        } catch (JSONException e) {
            e.printStackTrace();
        }

        //4 and 12
        adapter();;

    }

    private void adapter() {

        mAdapter =new ProductAdapter(getApplicationContext(),AOTHERLIST);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),1);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(layoutManager);
        recyclerview.setAdapter(mAdapter);

    }

    private class ProductAdapter extends RecyclerView.Adapter<MyviewHolder> {
        Context context;
        List<List<Model>> mDataList;
        int length,length_second;
        List<Model> DATA;
        public ProductAdapter(Context context, List<List<Model>> mDataList) {

            this.context=context;
            this.mDataList=mDataList;


        }



        @Override
        public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View layoutview = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_recycler_view,parent,false);

            return  new MyviewHolder(layoutview);


        }






        @Override
        public void onBindViewHolder(final MyviewHolder holder, final int position) {





            /**
             * according to the position of the first item  getting the first all list and passing to the horizontal recycler views**/
            DATA =new ArrayList<>();
        for(int i=0;i<mDataList.get(position).size();i++)//12
           {



                   Model m =new Model();
                   m.setURL(mDataList.get(position).get(i).getURL());
                   m.setNAME(mDataList.get(position).get(i).getNAME());
                   m.setTIMESTAMP(mDataList.get(position).get(i).getTIMESTAMP());
                   DATA.add(m);


           }


            SmallAdapter  smalladapter =new SmallAdapter(context,DATA);


            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(context, GridLayoutManager.HORIZONTAL, false);
            holder. _recyclerView.setLayoutManager(layoutManager);
            holder._recyclerView.setAdapter(smalladapter);



            holder.view_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   DATA=mDataList.get(position);

                    Intent intent = new Intent(MainActivity.this, More_list.class);
                    intent.putExtra("Position",position);
                    intent.putExtra("DATALIST", (Serializable) DATA);
                    v.getContext().startActivity(intent);

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


        RecyclerView _recyclerView;
        TextView view_more;

        public MyviewHolder(View itemView) {

            super(itemView);

            view_more= (TextView)itemView.findViewById(R.id.view_more);
            _recyclerView =(RecyclerView)itemView.findViewById(R.id._hori_recyclerview);




        }
    }















    private class SmallAdapter extends RecyclerView.Adapter<ViewHol> {
        Context context;
        List<Model> dataList;
        int length_second;
        public SmallAdapter(Context context, List<Model> dataList) {
            this.context = context;
            this.dataList =dataList;


        }

        @Override
        public ViewHol onCreateViewHolder(ViewGroup parent, int viewType) {
            View layoutview = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item,parent,false);

            return  new ViewHol(layoutview);
        }

        @Override
        public void onBindViewHolder(final ViewHol holder, final int position) {



            Model m = dataList.get(position);
            holder.name.setText(m.getNAME());
            holder.dob .setText(m.getTIMESTAMP());

            Glide.with(context).load(m.getURL())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.image);



            holder.image.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {


                    Log.d("PPP", String.valueOf(dataList.size()));
                    Log.d("PPP", String.valueOf(position));
                    Intent intent = new Intent(MainActivity.this, ScrollingActivity.class).putExtra("wall", dataList.get(position).getURL()).putExtra("name", dataList.get(position).getNAME());
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,holder.image,"vineesh_trans");

                    v.getContext().startActivity(intent, options.toBundle());

                }
            });

        }



        @Override
        public int getItemCount() {

            //12 colummns
            return dataList.size();
        }
    }

    private class ViewHol extends RecyclerView.ViewHolder {
        TextView name,description,dob;
        ImageView image;
        public ViewHol(final View itemView) {

            super(itemView);
            name = (TextView)itemView.findViewById(R.id.name);
            description = (TextView)itemView.findViewById(R.id.description);
            dob = (TextView)itemView.findViewById(R.id.dob);
            image = (ImageView)itemView.findViewById(R.id.image);


        }
    }



    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getAssets().open("vineesh.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
