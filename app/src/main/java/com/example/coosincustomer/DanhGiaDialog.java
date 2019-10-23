package com.example.coosincustomer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DanhGiaDialog extends DialogFragment {

    int position = 4;
    public interface DanhGiaListener{
        void onPositiveButtonClicked(String[] list,int position);
        void onNegativeButtonClicked();
    }
    DanhGiaListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (DanhGiaListener) context;
        }
        catch (Exception e){
            throw new ClassCastException(getActivity().toString()+"aaaaaaaaaaa");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] list = getActivity().getResources().getStringArray(R.array.danhgia);
        builder.setTitle("Bạn cảm thấy thế nào về ca làm?").setSingleChoiceItems(list, position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                position = i;
            }
        }).setPositiveButton("Đánh giá", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mListener.onPositiveButtonClicked(list,position);
            }
        }).setNegativeButton("Bỏ qua", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mListener.onNegativeButtonClicked();
            }
        });
        return builder.create();
    }
}
