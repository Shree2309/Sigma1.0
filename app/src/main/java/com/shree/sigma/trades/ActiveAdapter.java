package com.shree.sigma.trades;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shree.sigma.R;

import java.util.List;

public class ActiveAdapter extends RecyclerView.Adapter<ActiveAdapter.ActiveViewHolder> {
    private Context context;
    private List<ActiveModel> mList;



    public ActiveAdapter(Context context, List<ActiveModel> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public ActiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_active,parent,false);

        return new ActiveAdapter.ActiveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveViewHolder holder, int position) {

        holder.mAction.setText(mList.get(position).getAction());
        holder.mTag.setText(mList.get(position).getTag());
        holder.mName.setText(mList.get(position).getName());
        holder.mDate.setText(mList.get(position).getDate());
        holder.mTime.setText(mList.get(position).getTime());
        holder.mRate.setText(mList.get(position).getRate());
        holder.mSb.setText(mList.get(position).getSb());
        holder.mMargin.setText(mList.get(position).getMargin());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ActiveViewHolder extends RecyclerView.ViewHolder{
        TextView mAction,mTag, mDate, mTime, mName, mRate,mSb,mMargin;

        public ActiveViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.tvName);
            mDate = itemView.findViewById(R.id.tvDate);
            mAction = itemView.findViewById(R.id.tvAction);
            mTag = itemView.findViewById(R.id.tvTag);
            mTime = itemView.findViewById(R.id.tvCurrentTime);
            mRate = itemView.findViewById(R.id.tvRate);
            mSb = itemView.findViewById(R.id.tvSb);
            mMargin = itemView.findViewById(R.id.tvMargin);
        }
    }
}
