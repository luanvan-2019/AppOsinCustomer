package com.example.coosincustomer.Model;

import android.view.View;

import androidx.annotation.NonNull;

public interface OnItemClickShowLichLV {
    void onClick(View v, @NonNull int position);
    void onLongClick(View v, @NonNull int position);
}
