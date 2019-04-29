package com.example.a12902.myapplication.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a12902.myapplication.R;
import com.example.a12902.myapplication.ui.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

public class DetailFragment extends SwipeBackFragment {
    @BindView(R.id.tv)
    TextView mTv;

    private static final String CONTENT = "arg_content";
    private Unbinder unbinder;
    private String mContent;

    public static DetailFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString(CONTENT, content);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mContent = bundle.getString(CONTENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return attachToSwipeBack(view);
    }

    private void initData() {
        mTv.setText(mContent);
    }

    @OnClick(R.id.btn)
    void click() {
        startActivity(new Intent(getActivity(), MainActivity.class));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
