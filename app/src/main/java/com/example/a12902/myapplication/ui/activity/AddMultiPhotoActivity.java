package com.example.a12902.myapplication.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a12902.myapplication.R;
import com.example.a12902.myapplication.entity.Moment;

import java.io.File;
import java.util.ArrayList;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import pub.devrel.easypermissions.EasyPermissions;

public class AddMultiPhotoActivity extends AppCompatActivity implements BGASortableNinePhotoLayout.Delegate, View.OnClickListener {
    /**
     * 是否是单选「测试接口用的」
     */
    private CheckBox mSingleChoiceCb;
    /**
     * 是否具有拍照功能「测试接口用的」
     */
    private CheckBox mTakePhotoCb;
    /**
     * 是否可编辑
     */
    private CheckBox mEditableCb;
    /**
     * 是否显示九图控件的加号按钮「测试接口用的」
     */
    private CheckBox mPlusCb;
    /**
     * 是否开启拖拽排序功能「测试接口用的」
     */
    private CheckBox mSortableCb;
    /**
     * 拖拽排序九宫格控件
     */
    private BGASortableNinePhotoLayout mPhotosSnpl;
    private EditText mContentEt;
    private TextView mChoosePhoto;
    private TextView mPublish;

    private static final int RC_CHOOSE_PHOTO = 1;
    private static final int RC_PHOTO_PREVIEW = 2;
    private static final String EXTRA_MOMENT = "EXTRA_MOMENT";
    private static final String TAG = "AddMultiPhotoActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_multi_photo);
        initView();
        initListener();
    }


    private void initView() {
        mSingleChoiceCb = findViewById(R.id.cb_moment_add_single_choice);
        mTakePhotoCb = findViewById(R.id.cb_moment_add_take_photo);

        mEditableCb = findViewById(R.id.cb_moment_add_editable);
        mPlusCb = findViewById(R.id.cb_moment_add_plus);
        mSortableCb = findViewById(R.id.cb_moment_add_sortable);

        mContentEt = findViewById(R.id.et_moment_add_content);
        mPhotosSnpl = findViewById(R.id.snpl_moment_add_photos);

        mChoosePhoto = findViewById(R.id.tv_moment_add_choice_photo);
        mPublish = findViewById(R.id.tv_moment_add_publish);
    }


    private void initListener() {
        //是否开启单选
        mSingleChoiceCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    //开启单选只能选择一张照片
                    mPhotosSnpl.setData(null);
                    mPhotosSnpl.setMaxItemCount(1);
                } else {
                    mPhotosSnpl.setMaxItemCount(9);
                }
            }
        });

        //是否编辑
        mEditableCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                mPhotosSnpl.setEditable(checked);
            }
        });

        //是否显示加号
        mPlusCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                mPhotosSnpl.setPlusEnable(checked);
            }
        });

        //是否拖曳排序
        mSortableCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                mPhotosSnpl.setSortable(checked);
            }
        });

        // 设置拖拽排序控件的代理
        mPhotosSnpl.setDelegate(this);
        mChoosePhoto.setOnClickListener(this);
        mPublish.setOnClickListener(this);

    }

    //点击九宫格控件添加照片
    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        choosePhoto();
    }

    //点击九宫格控件删除照片
    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mPhotosSnpl.removeItem(position);
    }

    //点击九宫格控件预览照片
    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        Intent photoPickerPreviewIntent = new BGAPhotoPickerPreviewActivity.IntentBuilder(this)
                .previewPhotos(models) // 当前预览的图片路径集合
                .selectedPhotos(models) // 当前已选中的图片路径集合
                .maxChooseCount(mPhotosSnpl.getMaxItemCount()) // 图片选择张数的最大值
                .currentPosition(position) // 当前预览图片的索引
                .isFromTakePhoto(false) // 是否是拍完照后跳转过来
                .build();
        startActivityForResult(photoPickerPreviewIntent, RC_PHOTO_PREVIEW);
    }

    //点击九宫格控件拖曳排序照片
    @Override
    public void onNinePhotoItemExchanged(BGASortableNinePhotoLayout sortableNinePhotoLayout, int fromPosition, int toPosition, ArrayList<String> models) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_moment_add_choice_photo:
                //选择图片
                choosePhoto();
                break;
            case R.id.tv_moment_add_publish:
                String content = mContentEt.getText().toString().trim();
                if(TextUtils.isEmpty(content)&&mPhotosSnpl.getItemCount()==0){
                    Toast.makeText(this, "必须填写这一刻的想法或选择照片！", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra(EXTRA_MOMENT,new Moment(content,mPhotosSnpl.getData()));
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }

    private void choosePhoto() {
        if (!EasyPermissions.hasPermissions(AddMultiPhotoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)) {
            EasyPermissions.requestPermissions(AddMultiPhotoActivity.this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", 20,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
            return;
        }
// 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
        File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "MyApplication");

        Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(this)
                .cameraFileDir(mTakePhotoCb.isChecked() ? takePhotoDir : null) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                .maxChooseCount(mPhotosSnpl.getMaxItemCount() - mPhotosSnpl.getItemCount()) // 图片选择张数的最大值
                .selectedPhotos(null) // 当前已选中的图片路径集合
                .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                .build();
        startActivityForResult(photoPickerIntent, RC_CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == RC_CHOOSE_PHOTO) {
            if (mSingleChoiceCb.isChecked()) {
                mPhotosSnpl.setData(BGAPhotoPickerActivity.getSelectedPhotos(data));
            } else {
                ArrayList<String> selectedPhotos = BGAPhotoPickerActivity.getSelectedPhotos(data);
                for (String path :
                        selectedPhotos) {
                    Log.e(TAG, "onActivityResult: " + path);
                }
                mPhotosSnpl.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
            }
        }else if(requestCode == RC_PHOTO_PREVIEW){
            mPhotosSnpl.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
        }
    }

    public static Moment getMoment(Intent intent) {
        return intent.getParcelableExtra(EXTRA_MOMENT);
    }
}
