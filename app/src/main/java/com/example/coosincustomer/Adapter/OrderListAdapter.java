package com.example.coosincustomer.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coosincustomer.Model.ListOrder;
import com.example.coosincustomer.R;

import java.util.ArrayList;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderListHolder> {

    ArrayList<ListOrder> mangOrder;

    public OrderListAdapter(ArrayList<ListOrder> mangOrder) {
        this.mangOrder = mangOrder;
    }

    @NonNull
    @Override
    public OrderListAdapter.OrderListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_order,null);
        return new OrderListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListAdapter.OrderListHolder holder, int position) {
        ListOrder listOrder = mangOrder.get(position);
        holder.txtStatus.setText(listOrder.getStatus());
        holder.gia.setText(listOrder.getGia() + "");
        holder.txtCa.setText(listOrder.getCa());
        holder.txtDate.setText(listOrder.getDate());
        holder.txtDiadiem.setText(listOrder.getDiadiem());
        holder.txtMahoadon.setText(listOrder.getMahoadon());

    }

    @Override
    public int getItemCount() {
        return mangOrder.size() > 0 ? mangOrder.size() : 0;
    }

    class OrderListHolder extends RecyclerView.ViewHolder {

        TextView txtStatus, txtDate, txtCa, txtDiadiem, txtMahoadon, gia;
        ImageView imgHinhanh;
        //truyen item view vao va anh xa
        public OrderListHolder(@NonNull View itemView) {
            super(itemView);
            txtStatus = itemView.findViewById(R.id.status_list_order);
            txtCa = itemView.findViewById(R.id.ca_order);
            txtDate = itemView.findViewById(R.id.date_order);
            txtDiadiem = itemView.findViewById(R.id.diadiem);
            txtMahoadon = itemView.findViewById(R.id.ma_hoadon);
            gia = itemView.findViewById(R.id.gia);
        }
    }
}
