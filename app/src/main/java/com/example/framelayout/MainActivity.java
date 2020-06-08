package com.example.framelayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerClickListener {
 private RecyclerView recyclerView;
 private detailAdapt adapt;
 private  List<detailModel>modelList = new ArrayList<>();
 detailModel datamodel;
 ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
     @Override
     public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
         return false;
     }

     @Override
     public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();
        if (direction==ItemTouchHelper.LEFT){
            datamodel=modelList.get(position);
            modelList.remove(position);
            adapt.notifyItemRemoved(position);
            Snackbar.make(recyclerView,"item removed"+position+"at",Snackbar.LENGTH_LONG)
                    .setAction("undo", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            modelList.add(position,datamodel);
                            adapt.notifyItemInserted(position);
                        }
                    }).show();
        }
     }
 };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_details);
        populateList();


    }

    private void populateList() {

    for (int i =0;i<10;i++){
        int img= R.drawable.ic_person;
        String person= "Now person "+i;
        String name= "Now person Name is "+i;
        String msg= "Now person "+i;

        detailModel model=new detailModel(person,name,msg,img);
        modelList.add(model);

    }
    recycleSet(modelList);
    }

    private void recycleSet(List<detailModel> modelList) {
    adapt = new detailAdapt(this,this,modelList);
    LinearLayoutManager layout = new LinearLayoutManager(this);
    layout.setSmoothScrollbarEnabled(true);
    recyclerView.setLayoutManager(layout);
    recyclerView.setHasFixedSize(true);
    recyclerView.setAdapter(adapt);

    ItemTouchHelper helper = new ItemTouchHelper(simpleCallback);
    helper.attachToRecyclerView(recyclerView);

    }

    @Override
    public void onSingleClick(Object obj, int position) {
        detailModel model= (detailModel)obj;
        Toast.makeText(this, "item click"+position+"at", Toast.LENGTH_SHORT).show();
    }
}