package com.example.framelayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class detailAdapt extends RecyclerView.Adapter<detailAdapt.MyviewHolder> {
    @NonNull
    private Context mContext;
    private List<detailModel> modelList;
    private RecyclerActionClick actionClick;
    private RecyclerClickListener listener;

    public detailAdapt(@NonNull Context mContext, List<detailModel> modelList, RecyclerActionClick actionClick, RecyclerClickListener listener) {
        this.mContext = mContext;
        this.modelList = modelList;
        this.actionClick = actionClick;
        this.listener = listener;
    }

    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_second, parent, false);

        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyviewHolder holder, final int position) {
        final detailModel model = modelList.get(position);
        holder.RecycleImage.setImageResource(model.getRecycleImg());
        holder.textViewPerson.setText(model.getPerson());
        holder.textViewName.setText(model.getName());
        holder.textViewMsg.setText(model.getLastmsg());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSingleClick(model, position);
            }
        });

        boolean isExpand = modelList.get(position).isExpand();
        holder.ExpandLayout.setVisibility(isExpand ? View.VISIBLE : View.GONE);

        holder.ViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu menu= new PopupMenu(mContext,holder.ViewMore);
                menu.getMenuInflater().inflate(R.menu.more,menu.getMenu());

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        boolean isCLick=false;

                        switch (item.getItemId()){
                            case R.id.delete:
                                isCLick=true;
                                actionClick.onDeleteCLick(model,position);

                                break;
                        }
                        return false;
                    }
                });
                menu.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        TextView textViewPerson, textViewName, textViewMsg;
        ImageView  RecycleImage, ViewMore;
        ConstraintLayout ExpandLayout;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            ViewMore = itemView.findViewById(R.id.recycler_more);
            textViewPerson = itemView.findViewById(R.id.recycler_listname);
            textViewName = itemView.findViewById(R.id.recycler_name);
            textViewMsg = itemView.findViewById(R.id.recycler_lm);
            RecycleImage = itemView.findViewById(R.id.recycler_iv);
            ExpandLayout = itemView.findViewById(R.id.expandable_layout);

            textViewPerson.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detailModel model = modelList.get(getAdapterPosition());
                    model.setExpand(!model.isExpand());
                    notifyItemChanged(getAdapterPosition());

                }
            });

        }
    }
}
