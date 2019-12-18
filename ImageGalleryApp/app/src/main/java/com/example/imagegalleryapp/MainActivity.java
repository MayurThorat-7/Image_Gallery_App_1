package com.example.imagegalleryapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.imagegalleryapp.adapter.RecyclerItemClickListener;
import com.example.imagegalleryapp.adapter.RecyclerViewAdapter;
import com.example.imagegalleryapp.model.AppDatabase;
import com.example.imagegalleryapp.model.ImageData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static List<ImageData> listOfdata;
    private RecyclerView recyclerView;
    private JsonArrayRequest requestOfJSonArray;
    private RequestQueue requestQueue;
    private RecyclerView.LayoutManager layoutManagerOfrecyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listOfdata = new ArrayList<>();
        initView();
        setupRecyclerView();
        getImageDataFromDB();
        recyclerViewAdapter = new RecyclerViewAdapter(listOfdata, this);
        ((RecyclerViewAdapter) recyclerViewAdapter).setmItemClickListener(onItemClickListener);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    private void setupRecyclerView() {
        recyclerView.setHasFixedSize(true);
        appDatabase = AppDatabase.getInstance(getApplicationContext());
        layoutManagerOfrecyclerView = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManagerOfrecyclerView);
    }

    private RecyclerItemClickListener.OnItemClickListener onItemClickListener = new RecyclerItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View childView, int position) {
            if (childView.getId() == R.id.thumbnail_image_view) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(AppConstants.intent_key_url, listOfdata.get(position).getImageURL());
                startActivity(intent);
            }
        }

        @Override
        public void onItemLongPress(View childView, int position) {

        }

        @Override
        public void onOtherViewClick(View childView, int position) {

        }
    };

    //API for image data
    public void callAPI() {
        Toast.makeText(MainActivity.this, R.string.get_data_from_api_and_stored_in_database, Toast.LENGTH_LONG).show();
        requestOfJSonArray = new JsonArrayRequest(AppConstants.HTTP_JSON_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ParseJsonResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(requestOfJSonArray);
    }


    public void ParseJsonResponse(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            ImageData imageData = new ImageData();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                imageData.setImageTitle(json.getString(AppConstants.image_title));
                imageData.setThImageURL(json.getString(AppConstants.image_th_url));
                imageData.setImageURL(json.getString(AppConstants.image_url));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            listOfdata.add(imageData);
        }

        recyclerViewAdapter.notifyDataSetChanged();
        Utils.dismissProgress();

        //inserting to database
        saveImageData(listOfdata);
    }

    private void saveImageData(final List<ImageData> listImageData) {
        final List<ImageData> listImageData1 = listImageData;
        class SaveTask extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                //insert data in database
                appDatabase.imageDao().insertAll(listImageData1);
                return null;
            }

            @Override
            protected void onPostExecute(Void voids) {
                super.onPostExecute(voids);
                Toast.makeText(getApplicationContext(), R.string.saved, Toast.LENGTH_LONG).show();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }

    private void getImageDataFromDB() {
        class GetImageData extends AsyncTask<Void, Void, List<ImageData>> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Utils.showProgress(MainActivity.this);
            }

            @Override
            protected List<ImageData> doInBackground(Void... voids) {
                List<ImageData> imageList = appDatabase.imageDao().getAll();
                return imageList;
            }

            @Override
            protected void onPostExecute(List<ImageData> imageData) {
                Utils.dismissProgress();
                super.onPostExecute(imageData);
                listOfdata.addAll(imageData);
                recyclerViewAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, R.string.get_data_from_database, Toast.LENGTH_LONG).show();
                if (listOfdata.isEmpty()) {
                    // Set up progress before call
                    Utils.showProgress(MainActivity.this);
                    callAPI();
                }
            }
        }

        GetImageData getImageData = new GetImageData();
        getImageData.execute();
    }
}