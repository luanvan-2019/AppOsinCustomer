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
import com.example.coosincustomer.Model.OnItemClickListener;
import com.example.coosincustomer.Model.OnItemClickShowLichLV;
import com.example.coosincustomer.R;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;

public class ListUserChatAdapter extends RecyclerView.Adapter<ListUserChatAdapter.ListUserChatHolder> {

    ArrayList<ListFavorite> mangFavorite;

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public ListUserChatAdapter(ArrayList<ListFavorite> mangFavorite) {
        this.mangFavorite = mangFavorite;
    }

    @NonNull
    @Override
    public ListUserChatAdapter.ListUserChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.select_user_chat_item,null);
        return new ListUserChatHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListUserChatAdapter.ListUserChatHolder holder, int position) {
        ListFavorite listFavorite = mangFavorite.get(position);
        holder.txtName.setText(listFavorite.getName());
        holder.txtType.setText(listFavorite.getType());

    }

    @Override
    public int getItemCount() {
        return mangFavorite.size() > 0 ? mangFavorite.size() : 0;
    }

    class ListUserChatHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtType;
        //truyen item view vao va anh xa
        public ListUserChatHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txt_select_user_name);
            txtType = itemView.findViewById(R.id.txt_select_user_type);

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
