package com.yx.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yx.model.CustomerListModel;
import com.yx.model.CustomerModel;
import com.yx.phonetest.R;

import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CustomerListModel> data;
    private Context context;

    public CustomerAdapter(List<CustomerListModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    /**
     * 获取选中的
     * @return
     */
    public List<CustomerModel> getChoose(){
        List<CustomerModel> cus=new ArrayList<>();
        for (int i=0;i<data.size();i++){
            if (data.get(i).ispick){
                String c=data.get(i).title;
                for (int j=0;j<data.size();j++){
                    if (c.equals(data.get(j).title)&&data.get(j).type!=1){
                        cus.add(data.get(j).customerModel);
                    }
                }
            }
        }
        return cus;
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if (i == 1) {
            view = LayoutInflater.from(context).inflate(R.layout.item_title, viewGroup, false);
            return new TitleHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_customer, viewGroup, false);
            return new CustomerHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        CustomerListModel customerListModel = data.get(i);
        if (viewHolder instanceof TitleHolder) {
            TitleHolder holder = (TitleHolder) viewHolder;
            holder.tv_title.setText(customerListModel.title);
            holder.iv_pick.setVisibility(customerListModel.ispick ? View.VISIBLE : View.GONE);
        } else if (viewHolder instanceof CustomerHolder) {
            CustomerHolder holder = (CustomerHolder) viewHolder;
            holder.tv_name.setText(customerListModel.customerModel.customer_name);
            holder.tv_phone.setText(customerListModel.customerModel.customer_number+"");
            holder.tv_time.setText(customerListModel.customerModel.customer_time);
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class TitleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_title;
        LinearLayout ll_item;
        ImageView iv_pick;

        public TitleHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            ll_item = itemView.findViewById(R.id.ll_item);
            iv_pick = itemView.findViewById(R.id.iv_pick);
            ll_item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_item:
                    for (int i = 0; i < data.size(); i++) {
                        data.get(i).ispick = false;
                    }
                    data.get(getAdapterPosition()).ispick = true;
                    notifyDataSetChanged();
                    break;
            }
        }
    }

    class CustomerHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_phone, tv_time;

        public CustomerHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_phone = itemView.findViewById(R.id.tv_phone);
            tv_time = itemView.findViewById(R.id.tv_time);
        }
    }
}
