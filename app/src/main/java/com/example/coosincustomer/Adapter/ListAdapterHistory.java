package com.example.coosincustomer.Adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coosincustomer.Model.ListHistory;
import com.example.coosincustomer.Model.OnItemClickListener;
import com.example.coosincustomer.R;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ListAdapterHistory extends RecyclerView.Adapter<ListAdapterHistory.ListSubmitedHolder> {

    ArrayList<ListHistory> mangListhistory;

    private OnItemClickListener mOnItemClickListener;

    public ListAdapterHistory(ArrayList<ListHistory> mangListhistory) {
        this.mangListhistory = mangListhistory;

    }

    @NonNull
    @Override
    public ListSubmitedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_history,null);
        return new ListSubmitedHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterHistory.ListSubmitedHolder holder, int position) {
        ListHistory listHistory = mangListhistory.get(position);
        holder.txtOrderType.setText(listHistory.getOrdertype());
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String totalGiaString = decimalFormat.format(listHistory.getGia());
        holder.gia.setText(totalGiaString + " đ");
        holder.txtCa.setText(listHistory.getCa());
        holder.txtDate.setText(listHistory.getDate());
        holder.txtDiadiem.setText(listHistory.getDiadiem());
        if (listHistory.getOrdertype().trim().equals("Dùng lẻ")){
            holder.txtMahoadon.setText("MĐH: DL"+listHistory.getMahoadon());
        }else if (listHistory.getOrdertype().trim().equals("Định kỳ")){
            holder.txtMahoadon.setText("MĐH: DK"+listHistory.getMahoadon());
            holder.txtOrderType.setBackgroundResource(R.drawable.bg_text_orange);
        }else if (listHistory.getOrdertype().trim().equals("Nấu ăn")){
            holder.txtMahoadon.setText("MĐH: NA"+listHistory.getMahoadon());
            holder.txtOrderType.setBackgroundResource(R.drawable.bg_text_pink);
        }else{
            holder.txtMahoadon.setText("MĐH: TVS"+listHistory.getMahoadon());
            holder.txtOrderType.setBackgroundResource(R.drawable.bg_text_ryan);
        }
        holder.txtEmpName.setText(listHistory.getEmpName());
        if (!listHistory.getDateEnd().trim().equals("")){
            holder.txtDateEnd.setText("đến  "+listHistory.getDateEnd());
        }

    }

    @Override
    public int getItemCount() {
        return mangListhistory.size() > 0 ? mangListhistory.size() : 0;
    }

    class ListSubmitedHolder extends RecyclerView.ViewHolder {

        TextView txtDate, txtCa, txtDiadiem, txtMahoadon, gia,txtOrderType,txtEmpName,txtDateEnd;

        public ListSubmitedHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderType = itemView.findViewById(R.id.txt_order_type);
            txtCa = itemView.findViewById(R.id.ca_order);
            txtDate = itemView.findViewById(R.id.date_order);
            txtDiadiem = itemView.findViewById(R.id.diadiem);
            txtMahoadon = itemView.findViewById(R.id.ma_hoadon);
            gia = itemView.findViewById(R.id.gia);
            txtEmpName = itemView.findViewById(R.id.txt_emp_name);
            txtDateEnd =itemView.findViewById(R.id.date_order_end);
        }
    }

}
