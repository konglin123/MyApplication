package com.example.a12902.myapplication.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.a12902.myapplication.R;
import com.example.a12902.myapplication.adapter.ContentAdapter;
import com.example.a12902.myapplication.app.MyApp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ContentFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView mContentRv;
    @BindView(R.id.headerView)
    ImageView mHeaderIv;
    private static final String EXTRA_POSITION = "EXTRA_POSITION";

    private int mPosition;
    private Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(MyApp.getInstance()).inflate(R.layout.fragment_test, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPosition = getArguments().getInt(EXTRA_POSITION);
        if (mPosition == 0) {
            mHeaderIv.setImageResource(R.mipmap.one);
        } else if (mPosition == 1) {
            mHeaderIv.setImageResource(R.mipmap.two);
        } else {
            mHeaderIv.setImageResource(R.mipmap.three);
        }
        List<String> data = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            data.add("第" + (mPosition + 1) + "页 标题" + i);
        }
        mContentRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        ContentAdapter contentAdapter = new ContentAdapter(R.layout.item_test, data);
        mContentRv.setAdapter(contentAdapter);
    }

    /**
     * @param position 位置
     * @return
     */
    public static ContentFragment newInstance(int position) {
        ContentFragment contentFragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_POSITION, position);
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //butterKnife解绑
        unbinder.unbind();
    }
}
