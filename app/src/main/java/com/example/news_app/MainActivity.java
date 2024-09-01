package com.example.news_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.news_app.Models.NewsApiResponse;
import com.example.news_app.Models.NewsHeadlines;

import java.util.List;

public class MainActivity extends AppCompatActivity implements selectListener ,  View.OnClickListener
{
    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog dialog;
    Button btn1 , btn2 , btn3 , btn4 , btn5 , btn6 , btn7;
    SearchView searchView;
    static String category ;
    static View v2;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = findViewById(R.id.btn_1);
           btn1.setOnClickListener(this);
        btn2 = findViewById(R.id.btn_2);
           btn2.setOnClickListener(this);
        btn3 = findViewById(R.id.btn_3);
           btn3.setOnClickListener(this);
        btn4 = findViewById(R.id.btn_4);
           btn4.setOnClickListener(this);
        btn5 = findViewById(R.id.btn_5);
           btn5.setOnClickListener(this);
        btn6 = findViewById(R.id.btn_6);
           btn6.setOnClickListener(this);
        btn7 = findViewById(R.id.btn_7);
           btn7.setOnClickListener(this);

        searchView = findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                Button button = (Button) v2;
                String category = button.getText().toString();
                dialog.setTitle("Searching about " + query);
                dialog.show();
                RequestManager manager = new RequestManager(MainActivity.this);
                manager.getNewsHeadlines(listener , category, query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                return false;
            }
        });

        dialog = new ProgressDialog(this);
        dialog.setTitle("Fetching News Articles...");
        dialog.show();


        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener , "general" , null);


    }

    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>()
    {
        @Override
        public void OnFetchData(List<NewsHeadlines> list, String message)
        {
            if(list.isEmpty())
            {
                Toast.makeText(MainActivity.this, "No data Found!!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                showNews(list);
            }
            dialog.dismiss();

        }

        @Override
        public void OnError(String message)
        {
            Toast.makeText(MainActivity.this, "An Error Occur!!", Toast.LENGTH_SHORT).show();
        }
    };

    private void showNews(List<NewsHeadlines> list)
    {
        recyclerView = findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this ,1));
        adapter = new CustomAdapter(this , list , this);
        recyclerView.setAdapter(adapter);


    }


    @Override
    public void OnNewsClicked(NewsHeadlines headlines)
    {

        startActivity(new Intent(MainActivity.this , DetailsActivity.class).putExtra("data" , headlines));
    }

    @Override
    public void onClick(View v)
    {
        v2=v;
        Button button = (Button) v;
        String category = button.getText().toString();
        dialog.setTitle("Fetching News Articles of " + category);
        dialog.show();
        RequestManager manager = new RequestManager(this);
        manager.getNewsHeadlines(listener , category, null);
    }
}