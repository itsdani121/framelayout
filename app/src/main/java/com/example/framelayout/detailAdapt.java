package com.example.framelayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class detailAdapt extends RecyclerView.Adapter<detailAdapt.MyViewHolder> {
    @NonNull
    private Context mContext;
    private RecyclerClickListener listener;
    private List<detailModel>modelList;

    public detailAdapt(@NonNull Context mContext, RecyclerClickListener listener, List<detailModel> modelList) {
        this.mContext = mContext;
        this.listener = listener;
        this.modelList = modelList;
    }

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.activity_second,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final detailModel model = modelList.get(position);
        holder.textViewPerson.setText(model.getPerson());
        holder.textViewName.setText(model.getName());
        holder.textViewMsg.setText(model.getLastmsg());
        holder.imageView.setImageResource(model.getImg());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSingleClick(model,position);
            }
        });
        boolean isExpandable= modelList.get(position).isExpandable();
        holder.ExpandLayout.setVisibility(isExpandable?View.VISIBLE:View.GONE);

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

       TextView textViewPerson,textViewName,textViewMsg;
       ImageView imageView;
       ConstraintLayout ExpandLayout;




        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            textViewPerson= itemView.findViewById(R.id.recycler_listname);
           textViewName= itemView.findViewById(R.id.recycler_name);
           textViewMsg= itemView.findViewById(R.id.recycler_lm);
           imageView= itemView.findViewById(R.id.recycler_iv);
           ExpandLayout= itemView.findViewById(R.id.expandable_layout);

           textViewPerson.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                  detailModel model= modelList.get(getAdapterPosition());
                   model.setExpandable(!model.isExpandable());
                   notifyItemChanged(getAdapterPosition());
               }
           });


        }
    }
}
