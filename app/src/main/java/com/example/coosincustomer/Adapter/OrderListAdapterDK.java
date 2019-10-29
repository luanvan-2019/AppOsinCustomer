package com.example.coosincustomer.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coosincustomer.Model.ListOrder;
import com.example.coosincustomer.Model.ListOrderDK;
import com.example.coosincustomer.Model.OnItemClickListener;
import com.example.coosincustomer.R;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderListAdapterDK extends RecyclerView.Adapter<OrderListAdapterDK.OrderListHolder> {

    ArrayList<ListOrderDK> mangOrder;

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public OrderListAdapterDK(ArrayList<ListOrderDK> mangOrder) {
        this.mangOrder = mangOrder;
    }

    @NonNull
    @Override
    public OrderListAdapterDK.OrderListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_order_dk,null);
        return new OrderListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListAdapterDK.OrderListHolder holder, int position) {
        ListOrderDK listOrder = mangOrder.get(position);
        holder.txtStatus.setText(listOrder.getStatus());
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String totalGiaString = decimalFormat.format(listOrder.getGia());
        holder.gia.setText(totalGiaString + " đ");
        holder.txtCa.setText(listOrder.getCa());
        holder.txtDateStart.setText(listOrder.getDateStart());
        holder.txtDateEnd.setText(listOrder.getDateEnd());
        holder.txtschedule.setText(listOrder.getLich());
        holder.txtDiadiem.setText(listOrder.getDiadiem());
        holder.txtMahoadon.setText("MĐH: DK"+listOrder.getMahoadon()+"");

    }

    @Override
    public int getItemCount() {
        return mangOrder.size() > 0 ? mangOrder.size() : 0;
    }

    class OrderListHolder extends RecyclerView.ViewHolder {

        TextView txtStatus, txtDateStart, txtCa, txtDiadiem, txtMahoadon, gia,txtDateEnd,txtschedule;
        //truyen item view vao va anh xa
        public OrderListHolder(@NonNull View itemView) {
            super(itemView);
            txtStatus = itemView.findViewById(R.id.status_list_order);
            txtCa = itemView.findViewById(R.id.ca_order);
            txtDateStart = itemView.findViewById(R.id.date_order);
            txtDiadiem = itemView.findViewById(R.id.diadiem);
            txtMahoadon = itemView.findViewById(R.id.ma_hoadon);
            gia = itemView.findViewById(R.id.gia);
            txtDateEnd = itemView.findViewById(R.id.date_end);
            txtschedule = itemView.findViewById(R.id.schedule_order);

            PushDownAnim.setPushDownAnimTo(itemView).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick(view, getLayoutPosition());
                }
            });
            PushDownAnim.setPushDownAnimTo(itemView).setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onLongClick(view, getLayoutPosition());
                    return true;
                }
            });
        }
    }
}
