package com.example.acer.collectionplus.View;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.acer.Infoionplus.ViewModel.UserDetailInfoVM;
import com.example.acer.collectionplus.Adapter.RecomUserAdapter;
import com.example.acer.collectionplus.Adapter.UserDetailAdapter;
import com.example.acer.collectionplus.R;
import com.example.acer.collectionplus.ViewModel.UserDetailVM;
import com.example.acer.collectionplus.databinding.FragmentUserDetailBinding;

public class UserDetailFragment extends Fragment {
    private FragmentUserDetailBinding binding;
    private UserDetailVM userDetailVM;
//    private com.example.acer.Infoionplus.ViewModel.UserDetailInfoVM userDetailInfoVM;
    private UserDetailAdapter userDetailAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    public static SwipeRefreshLayout userSwipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_detail, container, false);

//        Toolbar toolbar = rootView.findViewById(R.id.toolbarId);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        initUserInfo();
        this.userDetailVM = new UserDetailVM(userDetailAdapter);
//        this.userDetailInfoVM = new UserDetailInfoVM();
//        userDetailInfoVM.getbinding(binding);
        return binding.getRoot();
    }

    private void initUserInfo() {
        recyclerView = binding.userCollect;
        recyclerView.setHasFixedSize(true);//设置固定大小
        recyclerView.setItemAnimator(new android.support.v7.widget.DefaultItemAnimator());//设置默认动画
        linearLayoutManager =new android.support.v7.widget.LinearLayoutManager(getActivity());
//        linearLayoutManager.setOrientation(android.support.v7.widget.OrientationHelper.HORIZONTAL);//设置滚动方向，横向滚动
        recyclerView.setLayoutManager(linearLayoutManager);

        userDetailAdapter=new UserDetailAdapter(getActivity());
        recyclerView.setAdapter(userDetailAdapter);

        userSwipeRefreshLayout = binding.swipeUserDetail;
        userSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                userDetailVM.initData();
            }
        });
    }

    private void initUserCollect() {

    }
}
