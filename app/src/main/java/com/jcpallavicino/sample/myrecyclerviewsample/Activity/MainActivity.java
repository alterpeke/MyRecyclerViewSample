package com.jcpallavicino.sample.myrecyclerviewsample.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jcpallavicino.sample.myrecyclerviewsample.R;

import java.util.ArrayList;
import com.jcpallavicino.sample.myrecyclerviewsample.Utils.AsynkConnector;
import com.jcpallavicino.sample.myrecyclerviewsample.Utils.Callback;
import com.jcpallavicino.sample.myrecyclerviewsample.Utils.DataHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> image_titles = new ArrayList<>();
    ArrayList<String> image_url = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getData();

    }

    private void getData() {
        String content = "{}";

        String param = "";

        AsynkConnector c = new AsynkConnector(0, param ,content, new Callback() {
            private int dots = 0;
            @Override
            public void starting() {

            }

            @Override
            public void completed(String res, int responseCode) {

                if(responseCode==200){

                    parseResponseJson(res);

                    RecyclerView recyclerView = (RecyclerView)findViewById(R.id.imagegallery);
                    recyclerView.setHasFixedSize(true);

                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),3);
                    recyclerView.setLayoutManager(layoutManager);

                    ArrayList<CreateList> createLists = prepareData();
                    MyAdapter adapter = new MyAdapter(getApplicationContext(), createLists);
                    recyclerView.setAdapter(adapter);


                }else{
                }
            }

            @Override
            public void completedWithErrors(Exception e) {
            }

            @Override
            public void update() {
            }
        });
        c.execute();
    }

    private void parseResponseJson(String res){
        try{
            JSONArray arr = new JSONArray(res);

            for(int i=0; i<10; ++i)
            {
                JSONObject jo = arr.getJSONObject(i);
                DataHandler.albumId = jo.getString("albumId");
                DataHandler.url = jo.getString("url");
                DataHandler.thumbnailUrl = jo.getString("thumbnailUrl");
                DataHandler.title = jo.getString("title");
                image_titles.add(DataHandler.title);
                image_url.add(DataHandler.url);
            }

            }
        catch (JSONException e){
            System.out.println(e.getMessage());
        }

    }

    private ArrayList<CreateList> prepareData(){

        ArrayList<CreateList> theimage = new ArrayList<>();
        for(int i = 0; i< image_titles.size(); i++){
            CreateList createList = new CreateList();
            createList.setImage_title(image_titles.get(i));
            //Simple URL to display one image
            createList.setImage_ID(image_url.get(i));

            theimage.add(createList);
        }
        return theimage;
    }

}
