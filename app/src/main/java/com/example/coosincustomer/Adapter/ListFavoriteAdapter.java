package com.example.coosincustomer.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coosincustomer.Model.ListFavorite;
import com.example.coosincustomer.Model.ListOrder;
import com.example.coosincustomer.Model.OnItemClickListener;
import com.example.coosincustomer.Model.OnItemClickShowLichLV;
import com.example.coosincustomer.R;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ListFavoriteAdapter extends RecyclerView.Adapter<ListFavoriteAdapter.ListFavoriteHolder> {

    ArrayList<ListFavorite> mangFavorite;
    private OnItemClickListener mOnItemClickListener;
    private OnItemClickShowLichLV mOnItemClickShowLichLV;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }
    public void setmOnItemClickShowLichLV(OnItemClickShowLichLV mOnItemClickShowLichLV){
        this.mOnItemClickShowLichLV = mOnItemClickShowLichLV;
    }

    public ListFavoriteAdapter(ArrayList<ListFavorite> mangFavorite) {
        this.mangFavorite = mangFavorite;
    }

    @NonNull
    @Override
    public ListFavoriteAdapter.ListFavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_favorite,null);
        return new ListFavoriteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListFavoriteAdapter.ListFavoriteHolder holder, int position) {
        ListFavorite listFavorite = mangFavorite.get(position);
        holder.txtName.setText(listFavorite.getName());
        holder.txtType.setText(listFavorite.getType());
        holder.txtPhone.setText(listFavorite.getPhone());

    }

    @Override
    public int getItemCount() {
        return mangFavorite.size() > 0 ? mangFavorite.size() : 0;
    }

    class ListFavoriteHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtType, txtPhone;
        Button btnRemoveFavorite,btnShowCalendar;
        ImageView imgHinhanh;
        //truyen item view vao va anh xa
        public ListFavoriteHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txt_staff_favorite_name);
            txtType = itemView.findViewById(R.id.txt_staff_type_favorite);
            txtPhone = itemView.findViewById(R.id.txt_staff_phone_favorite);
            btnRemoveFavorite = itemView.findViewById(R.id.btn_remove_favorite);
            btnShowCalendar = itemView.findViewById(R.id.btn_xem_lich);

            PushDownAnim.setPushDownAnimTo(btnRemoveFavorite).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClick(view, getLayoutPosition());
                }
            });
            PushDownAnim.setPushDownAnimTo(btnShowCalendar).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickShowLichLV.onClick(view, getLayoutPosition());
                }
            });

        }
    }
}
