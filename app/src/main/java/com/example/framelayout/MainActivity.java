package com.example.framelayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.DialogInterface;
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

public class MainActivity extends AppCompatActivity implements RecyclerClickListener, RecyclerActionClick {

    private RecyclerView recyclerView;
    private detailAdapt adapt;
    private List<detailModel> modelList = new ArrayList<>();
    detailModel modelData, swipeData;
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            if (direction == ItemTouchHelper.LEFT) {
                swipeData = modelList.get(position);

                showDialogBox(position, true);
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

        for (int i = 0; i < 10; i++) {
            int img = R.drawable.ic_person;
            String person = "Person  " + i;
            String name = "Person Name " + i;
            String msg = "Person Last Message " + i;
            detailModel model = new detailModel(person, name, msg, img);
            modelList.add(model);
        }
        addRecycle(modelList);
    }

    private void addRecycle(List<detailModel> modelList) {
        adapt = new detailAdapt(this, modelList, this, this);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        layout.setSmoothScrollbarEnabled(true);
        recyclerView.setAdapter(adapt);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layout);

        ItemTouchHelper helper = new ItemTouchHelper(simpleCallback);
        helper.attachToRecyclerView(recyclerView);

    }

    @Override
    public void onDeleteCLick(Object obj, int position) {

        showDialogBox(position, false);

    }

    private void showDialogBox(final int position, final boolean isSwiped) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure to delete this Item");
        builder.setCancelable(false);
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                deleteItem(position);

            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                adapt.notifyDataSetChanged();
               /* if (isSwiped) {
                    modelList.add(position, swipeData);
                    adapt.notifyItemInserted(position);

                }*/

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void deleteItem(final int position) {
        modelData = modelList.get(position);
        modelList.remove(position);
        adapt.notifyItemRemoved(position);
        adapt.notifyItemRangeChanged(position,modelList.size());
        Snackbar snackbar = Snackbar.make(recyclerView, "item removed at position " + position, Snackbar.LENGTH_LONG);
        snackbar.setAction("undo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelList.add(position, modelData);
                adapt.notifyItemInserted(position);
                adapt.notifyItemRangeChanged(position,modelList.size());

            }
        });
        snackbar.show();
    }


    @Override
    public void onUndoCLick(Object obj, int position) {

    }

    @Override
    public void onSingleClick(Object obj, int position) {
        detailModel model = (detailModel) obj;
        Toast.makeText(this, "click at " + position, Toast.LENGTH_SHORT).show();
    }
}