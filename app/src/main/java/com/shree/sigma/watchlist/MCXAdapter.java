package com.shree.sigma.watchlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shree.sigma.R;

import java.util.List;

public class MCXAdapter extends RecyclerView.Adapter<MCXAdapter.MCXViewHolder> {
    private Context context;
    private List<MCXModel> mList;
    private MCXViewModel viewModel;
    private int pos;
    private RecyclerViewClickListener clickListener;
    public void setData(List<MCXModel> list){
        mList = list;
    }

    public MCXAdapter(Context context, List<MCXModel> mList, MCXViewModel viewModel, RecyclerViewClickListener clickListener) {
        this.context = context;
        this.mList = mList;
        this.viewModel = viewModel;
        this.clickListener=clickListener;
    }
    @NonNull
    @Override
    public MCXViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row,parent,false);

        return new MCXViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MCXViewHolder holder, int position) {

        holder.mName.setText(mList.get(position).getName());
        holder.mDate.setText(mList.get(position).getDate());
        holder.mLot.setText(mList.get(position).getLot());
        holder.mNum1.setText(mList.get(position).getNum1());
        holder.mNum2.setText(mList.get(position).getNum2());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                viewModel.setSelectedItemPosition(holder.getAdapterPosition());
                clickListener.onClick(holder.getAdapterPosition());
            }
        });


    }

    @Override
    public int getItemCount() {
            return mList.size();
    }

    public class MCXViewHolder extends RecyclerView.ViewHolder {
        TextView mName, mDate, mLot, mNum1, mNum2;
        public MCXViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.mcx_name);
            mDate = itemView.findViewById(R.id.mcx_date);
            mLot = itemView.findViewById(R.id.mcx_lot);
            mNum1 = itemView.findViewById(R.id.mcx_num1);
            mNum2 = itemView.findViewById(R.id.mcx_num2);
        }
    }

    public String getDataAtIndex(int index) {
        List<MCXModel> data = viewModel.getList().getValue();
        //String str = mList.get(index).getName();
        if (data != null && index >= 0 && index < data.size()) {
            return mList.get(index).getName();
        } else {
            return null;
        }
    }

    public MCXModel getDataAt(int index) {
        List<MCXModel> data = viewModel.getList().getValue();
        //String str = mList.get(index).getName();
        if (data != null && index >= 0 && index < data.size()) {
            return new MCXModel(mList.get(index).getName(),mList.get(index).getDate(),mList.get(index).getLot(),mList.get(index).getNum1(),mList.get(index).getNum2());
        } else {
            return null;
        }
    }
    public interface RecyclerViewClickListener {
        void onClick(int position);
    }
}
