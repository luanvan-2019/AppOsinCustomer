package com.example.coosincustomer.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.coosincustomer.Adapter.OrderListAdapter;
import com.example.coosincustomer.Model.ListOrder;
import com.example.coosincustomer.R;

import java.util.ArrayList;

public class FragmentDungle extends Fragment {

    View view;
    RecyclerView recyclerView;
    ArrayList<ListOrder> listOrders;
    OrderListAdapter orderListAdapter;

    public FragmentDungle() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.dungle_fragment,container,false);
        recyclerView = view.findViewById(R.id.recyclerview);
        listOrders = new ArrayList<>();
        listOrders.add(new ListOrder("Đang tìm kiếm NV","12/09/2019","Ca sáng (08:00 - 11:30)","23/10 Bùi Thị Xuân",
                "HĐ: 01234",277000));
        listOrders.add(new ListOrder("Đã nhận","15/09/2019","Ca tối (17:00 - 19:30)","134 Lê Văn Sỹ",
                "HĐ: 13123",150000));
        listOrders.add(new ListOrder("Đang tìm kiếm NV","12/09/2019","Ca sáng (08:00 - 11:30)","23/10 Bùi Thị Xuân",
                "HĐ: 01234",277000));
        listOrders.add(new ListOrder("Đã nhận","15/09/2019","Ca tối (17:00 - 19:30)","134 Lê Văn Sỹ",
                "HĐ: 13123",150000));
        listOrders.add(new ListOrder("Đang tìm kiếm NV","12/09/2019","Ca sáng (08:00 - 11:30)","23/10 Bùi Thị Xuân",
                "HĐ: 01234",277000));
        listOrders.add(new ListOrder("Đã nhận","15/09/2019","Ca tối (17:00 - 19:30)","134 Lê Văn Sỹ",
                "HĐ: 13123",150000));
        listOrders.add(new ListOrder("Đang tìm kiếm NV","12/09/2019","Ca sáng (08:00 - 11:30)","23/10 Bùi Thị Xuân",
                "HĐ: 01234",277000));
        listOrders.add(new ListOrder("Đã nhận","15/09/2019","Ca tối (17:00 - 19:30)","134 Lê Văn Sỹ",
                "HĐ: 13123",150000));
        orderListAdapter = new OrderListAdapter(listOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setAdapter(orderListAdapter);
        return view;
    }
}
