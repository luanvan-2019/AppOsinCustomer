package com.example.coosincustomer.Fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.coosincustomer.Adapter.ListUserChatAdapter;
import com.example.coosincustomer.ListChatActivity;
import com.example.coosincustomer.LoadingDialog;
import com.example.coosincustomer.R;
import com.thekhaeng.pushdownanim.PushDownAnim;

/**
 * A simple {@link Fragment} subclass.
 */
public class DifferenceFragment extends Fragment {

    RelativeLayout relChat;
    View v;


    public DifferenceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_difference, container, false);
        relChat = v.findViewById(R.id.rel_chat);

        PushDownAnim.setPushDownAnimTo(relChat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoadingDialog loadingDialog = new LoadingDialog();
                loadingDialog.loading(getActivity());
                Intent intent = new Intent(getActivity(), ListChatActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

}
