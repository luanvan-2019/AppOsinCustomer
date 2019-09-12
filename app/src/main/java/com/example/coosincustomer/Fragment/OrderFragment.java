package com.example.coosincustomer.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.coosincustomer.Adapter.OrderListAdapter;
import com.example.coosincustomer.Adapter.OrderPagerAdapter;
import com.example.coosincustomer.Adapter.ViewPagerAdapter;
import com.example.coosincustomer.Model.ListOrder;
import com.example.coosincustomer.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class OrderFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    View view;

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

        OrderPagerAdapter adapter = new OrderPagerAdapter(getFragmentManager());
        adapter.addFragment(new FragmentDungle(),"Dùng lẻ");
        adapter.addFragment(new Fragment_dungdk(),"Dùng định kỳ");
        adapter.addFragment(new Fragment_tongvs(),"Tổng vệ sinh");
        adapter.addFragment(new Fragment_dvnauan(),"DV nấu ăn");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}
