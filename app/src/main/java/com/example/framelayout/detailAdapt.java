package com.example.framelayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class detailAdapt extends RecyclerView.Adapter<detailAdapt.MyViewHolder> {

    private Context mContext;
    private List<detailModel>modelList;
    private RecyclerActionClick actionClick;
    private RecyclerClickListener listener;

    public detailAdapt(Context mContext, List<detailModel> modelList, RecyclerActionClick actionClick, RecyclerClickListener listener) {
        this.mContext = mContext;
        this.modelList = modelList;
        this.actionClick = actionClick;
        this.listener = listener;
    }

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(mContext).inflate(R.layout.activity_second,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final detailModel model= modelList.get(position);
        holder.textViewPerson.setText(model.getPerson());
        holder.textViewName.setText(model.getName());
        holder.textViewMsg.setText(model.getLastmsg());
        holder.imageViewPerson.setImageResource(model.getImg());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSingleClick(model,position);

            }
        });
        final boolean isExpand= modelList.get(position).isExpandable();
        holder.expandLayout.setVisibility(isExpand?View.VISIBLE:View.GONE);

        holder.viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu menu = new PopupMenu(mContext,holder.viewMore);
                menu.getMenuInflater().inflate(R.menu.more,menu.getMenu());
                menu.show();
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {


                    public boolean onMenuItemClick(MenuItem item) {
                        boolean isClicked=false;
                        switch (item.getItemId()){

                            case R.id.delete:
                                isClicked=true;
                                actionClick.onDeleteCLick(model,position);
                                break;
                            case R.id.undo:
                                isClicked=true;
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewPerson,textViewName,textViewMsg;
        ImageView imageViewPerson,viewMore;
        ConstraintLayout expandLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewPerson=itemView.findViewById(R.id.recycler_listname);
            textViewName=itemView.findViewById(R.id.recycler_name);
            textViewMsg=itemView.findViewById(R.id.recycler_lm);
            imageViewPerson=itemView.findViewById(R.id.recycler_iv);
            viewMore=itemView.findViewById(R.id.recycler_more);
            expandLayout=itemView.findViewById(R.id.expandable_layout);
            textViewPerson.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detailModel model=modelList.get(getAdapterPosition());
                    model.setExpandable(!model.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });

        }
    }
}
