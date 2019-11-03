package com.example.coosincustomer.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coosincustomer.Model.ListCanceled;
import com.example.coosincustomer.Model.ListHistory;
import com.example.coosincustomer.Model.OnItemClickListener;
import com.example.coosincustomer.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ListAdapterCanceled extends RecyclerView.Adapter<ListAdapterCanceled.ListCanceledHolder> {

    ArrayList<ListCanceled> mangListcanceled;

    private OnItemClickListener mOnItemClickListener;

    public ListAdapterCanceled(ArrayList<ListCanceled> mangListcanceled) {
        this.mangListcanceled = mangListcanceled;

    }

    @NonNull
    @Override
    public ListCanceledHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_canceled,null);
        return new ListCanceledHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterCanceled.ListCanceledHolder holder, int position) {
        ListCanceled listCanceled = mangListcanceled.get(position);
        holder.txtOrderType.setText(listCanceled.getOrdertype());
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String totalGiaString = decimalFormat.format(listCanceled.getGia());
        holder.gia.setText(totalGiaString + " đ");
        holder.txtCa.setText(listCanceled.getCa());
        holder.txtDate.setText(listCanceled.getDate());
        holder.txtDiadiem.setText(listCanceled.getDiadiem());
        if (listCanceled.getOrdertype().trim().equals("Dùng lẻ")){
            holder.txtMahoadon.setText("MĐH: DL"+listCanceled.getMahoadon());
        }else if (listCanceled.getOrdertype().trim().equals("Định kỳ")){
            holder.txtMahoadon.setText("MĐH: DK"+listCanceled.getMahoadon());
            holder.txtOrderType.setBackgroundResource(R.drawable.bg_text_orange);
        }else if (listCanceled.getOrdertype().trim().equals("Nấu ăn")){
            holder.txtMahoadon.setText("MĐH: NA"+listCanceled.getMahoadon());
            holder.txtOrderType.setBackgroundResource(R.drawable.bg_text_pink);
        }else{
            holder.txtMahoadon.setText("MĐH: TVS"+listCanceled.getMahoadon());
            holder.txtOrderType.setBackgroundResource(R.drawable.bg_text_ryan);
        }

    }

    @Override
    public int getItemCount() {
        return mangListcanceled.size() > 0 ? mangListcanceled.size() : 0;
    }

    class ListCanceledHolder extends RecyclerView.ViewHolder {

        TextView txtDate, txtCa, txtDiadiem, txtMahoadon, gia,txtOrderType;

        public ListCanceledHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderType = itemView.findViewById(R.id.txt_order_type);
            txtCa = itemView.findViewById(R.id.ca_order);
            txtDate = itemView.findViewById(R.id.date_order);
            txtDiadiem = itemView.findViewById(R.id.diadiem);
            txtMahoadon = itemView.findViewById(R.id.ma_hoadon);
            gia = itemView.findViewById(R.id.gia);
        }
    }

}
