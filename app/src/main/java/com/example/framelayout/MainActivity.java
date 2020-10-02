package com.example.framelayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerClickListener, RecyclerActionClick {
    private RecyclerView recyclerView;
    private detailAdapt adapt;
    private Button btn;
    private List<detailModel> modelList = new ArrayList<>();
    private detailModel deleteData, removeData;
    private ImageView viewImage;
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            if (direction == ItemTouchHelper.LEFT) {
                deleteData = modelList.get(position);

                showData(position, true, viewHolder.itemView.getContext());

            }


        }
    };

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(R.layout.toolbar_recycle);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_details);
        viewImage = findViewById(R.id.recycler_iv);
        btn = findViewById(R.id.datebtn);
        TextView textView = actionBar.getCustomView().findViewById(R.id.custom_action_bar);
        textView.setText("My Application");
        // Objects.requireNonNull(getSupportActionBar()).setTitle(null);

        populateList();
        showDatePicker();
    }

    private void showDatePicker() {
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select a Date ");
        final MaterialDatePicker materialDatePicker = builder.build();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.more, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean isclick = false;
        switch (item.getItemId()) {

            case R.id.delete:
                isclick = true;
                Toast.makeText(this, "delete item ", Toast.LENGTH_SHORT).show();
                break;
            case R.id.search:
                isclick = true;
                Toast.makeText(this, "search click", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return false;
    }

    private void populateList() {
        for (int i = 0; i < 10; i++) {
            int img = R.drawable.ic_person;
            int img2 = R.drawable.ic_launcher_foreground;

            String person = "this person is " + i;
            String name = "this person name  is " + i;
            String msg = "this person last message is " + i;

            detailModel model = new detailModel(person, name, msg, img2, img);
            modelList.add(model);

            showRecycle(modelList);

        }

    }

    private void showRecycle(List<detailModel> modelList) {
        adapt = new detailAdapt(this, modelList, this, this);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapt);

        ItemTouchHelper helper = new ItemTouchHelper(simpleCallback);
        helper.attachToRecyclerView(recyclerView);
    }

    private void showImage() {

        ImagePicker.Builder builder = new ImagePicker.Builder(MainActivity.this);

        builder.allowMultipleImages(false);

        builder.allowOnlineImages(false);
        builder.compressLevel(ImagePicker.ComperesLevel.MEDIUM);
        builder.scale(500, 500);
        builder.directory(ImagePicker.Directory.DEFAULT);
        builder.extension(ImagePicker.Extension.PNG);
        builder.mode(ImagePicker.Mode.CAMERA_AND_GALLERY);

        builder.build();

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE) {
                List<String> paths = data.getStringArrayListExtra(ImagePicker.EXTRA_IMAGE_PATH);

                if (paths != null) {
                    String imagePath = paths.get(0);

                    // Load picked image
                    Glide.with(this).load(imagePath).into(viewImage);
                }

            }
        }

    }

    @Override
    public void onSingleClick(Object obj, int position) {
        detailModel model = (detailModel) obj;
        Toast.makeText(this, "item click at " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteCLick(Object obj, int position) {
        showData(position, false, this);
    }

    private void showData(final int position, final boolean isSwiped, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Are You Sure for Delete This");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                removeData = modelList.get(position);
                modelList.remove(position);
                adapt.notifyItemRemoved(position);
                Snackbar snackbar = Snackbar.make(recyclerView, "item click at position " + position, Snackbar.LENGTH_LONG);
                snackbar.setAction("undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        modelList.add(position, removeData);
                        adapt.notifyItemInserted(position);
                    }


                });
                snackbar.show();
            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (isSwiped) {
                    modelList.add(deleteData);
                    adapt.notifyItemChanged(position);
                }
            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();


    }

    @Override
    public void onAddCLick(Object obj, int position) {

    }
}