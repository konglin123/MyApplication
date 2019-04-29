package com.example.a12902.myapplication.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.a12902.myapplication.R;
import com.example.a12902.myapplication.ui.fragment.HomeFragment;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

public class FragmentationFlowActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragmentation_flow);
        if (findFragment(HomeFragment.class) == null) {
            loadRootFragment(R.id.container, HomeFragment.newInstance());
        }
    }
}
