package com.example.movierating;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements com.example.movierating.RecyclerAdapter.ListItemClickListener {

    FloatingActionButton button;
    RecyclerView RecyclerView;
    DatabaseReference databaseReference;
    ProgressBar progressBar;

    RecyclerAdapter RecyclerAdapter;
    ArrayList<ItemList> itemListArrayList =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        button= findViewById(R.id.AddMovie);
        RecyclerView=findViewById(R.id.recyclerView);
        progressBar=findViewById(R.id.progressBar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView.setLayoutManager(linearLayoutManager);
        RecyclerView.setHasFixedSize(true);
        RecyclerAdapter = new RecyclerAdapter(this, itemListArrayList,this);
        RecyclerView.setAdapter(RecyclerAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);

            }
        });

        databaseReference.child("rating_table").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                itemListArrayList.clear();


                Log.d("Main", "child count: " + dataSnapshot.getChildrenCount());
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    String title = String.valueOf(data.child("title").getValue());
                    String genres = String.valueOf(data.child("genres").getValue());
                    float rating = Float.parseFloat(data.child("rating").getValue().toString());
                    String imageAdress =String.valueOf(data.child("image").getValue());

                    itemListArrayList.add(new ItemList(title,genres,rating,imageAdress));


                }
                RecyclerAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                RecyclerView.setVisibility(View.VISIBLE);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onListItemClickListener(int clickedItemIndex) {

    }
}
