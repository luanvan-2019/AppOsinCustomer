package com.example.coosincustomer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.coosincustomer.Adapter.OrderPagerAdapter;
import com.example.coosincustomer.HistoryActivity;
import com.example.coosincustomer.R;
import com.google.android.material.tabs.TabLayout;

public class OrderFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    View view;
    ImageView imgHistory;

    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_order,container,false);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);
        imgHistory = view.findViewById(R.id.ic_history);

//        String check = getArguments().getString("check");
        try {
            if (getArguments().getString("check") != null){
                Toolbar toolbar = view.findViewById(R.id.toolbar_order_fragment);
                toolbar.setNavigationIcon(R.drawable.ic_back);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().onBackPressed();
                    }
                });
            }
        }catch(Exception ex){

        }

        OrderPagerAdapter adapter = new OrderPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FragmentDungle(),"Dùng lẻ");
        adapter.addFragment(new Fragment_dungdk(),"Dùng định kỳ");
        adapter.addFragment(new Fragment_tongvs(),"Tổng vệ sinh");
        adapter.addFragment(new Fragment_dvnauan(),"DV nấu ăn");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        imgHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HistoryActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
