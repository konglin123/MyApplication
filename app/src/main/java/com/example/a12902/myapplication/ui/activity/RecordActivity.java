package com.example.a12902.myapplication.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a12902.myapplication.R;
import com.example.a12902.myapplication.adapter.AccessoryAdapter;
import com.example.a12902.myapplication.base.BaseActivity;
import com.example.a12902.myapplication.db.AccessoryDbEntity;
import com.example.a12902.myapplication.db.DbManager;
import com.example.a12902.myapplication.util.BitmapUtil;
import com.example.a12902.myapplication.util.FileUtil;
import com.example.a12902.myapplication.util.TimeFormatUtils;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import pub.devrel.easypermissions.EasyPermissions;

public class RecordActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.choose)
    TextView choose;
    @BindView(R.id.rv)
    RecyclerView rv;

    private Dialog dialog;
    private View inflate;
    private View choosePhoto;
    private View takePhoto;

    private static final String TAG = "RecordActivity";

    private String LocalImgPath=Environment.getExternalStorageDirectory()+"MyApplication/pic";
    public static final int SELECT_TAKE_PHOTO = 1;
    public static final int SELECT_PICK_PHOTO = 2;
    public static final int SELECT_RESIZE_PHOTO = 3;
    public static final int VIDEO_RECORD = 4;
    private String resizedPicName;
    private int zoomX=400, zoomY=400;
    private Intent lastIntent;
    private boolean needResize=true;
    private String takePhotoPath;
     private List<AccessoryDbEntity>  accessoryLists=new ArrayList<>();
    private AccessoryAdapter accessoryAdapter;


    @Override
    protected void obtainIntent() {

    }

    @Override
    protected void initListener() {
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });


    }

    @Override
    protected void initData() {

        rv.setLayoutManager(new LinearLayoutManager(this));
        accessoryAdapter = new AccessoryAdapter(R.layout.item_accessory, accessoryLists);
        rv.setAdapter(accessoryAdapter);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record;
    }

    private void showDialog() {
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        inflate = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null);
        choosePhoto = inflate.findViewById(R.id.choosePhoto);
        takePhoto = inflate.findViewById(R.id.takePhoto);
        choosePhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        dialog.setContentView(inflate);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams attributes = dialogWindow.getAttributes();
        attributes.y=20;
        dialogWindow.setAttributes(attributes);
        dialog.show();
    }

    @Override
    public void onClick(View view) {
switch (view.getId()){
    case R.id.takePhoto:
        if (!EasyPermissions.hasPermissions(RecordActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)) {
            EasyPermissions.requestPermissions(RecordActivity.this, "拍照请赋予权限", 20,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
            return;
        }
        takePhoto();
        break;
    case R.id.choosePhoto:
        break;
}
    }

    //拍照
    private void takePhoto() {

        if (Environment.getExternalStorageState().equals("mounted")) {
            File path = new File(LocalImgPath);
            if (!path.exists()) {
                path.mkdirs();
            }
            Intent localIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);
//                takePhotoPath = LocalImgPath + "/"+ System.currentTimeMillis() + ".jpg";
            File photoFile = getCreateFile();
            takePhotoPath = photoFile.getAbsolutePath();
            if (Build.VERSION.SDK_INT < 24) {
                Uri photoUri = Uri.fromFile(new File(takePhotoPath));
                localIntent.putExtra("output", photoUri);
            } else {
                localIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                localIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this,"com.example.a12902.myapplication.fileprovider", new File(takePhotoPath)));

//                localIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                ContentValues contentValues = new ContentValues(1);
//                contentValues.put(MediaStore.Images.Media.DATA, takePhotoPath);
//                Uri uri = getContentResolver().
//                        insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
//                localIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            }

            startActivityForResult(localIntent, SELECT_TAKE_PHOTO);
        } else {
            Log.i(TAG, "sdcard not exist");
            Toast.makeText(this, "内存卡不存在", Toast.LENGTH_SHORT).show();
        }
    }

    //从相册选择
    private void selectPhoto() {
        Intent localIntent = new Intent();
        localIntent.setType("image/*");
        localIntent.setAction("android.intent.action.GET_CONTENT");
        startActivityForResult(localIntent, SELECT_PICK_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.i(TAG, "onActivityResult resultCode = " + resultCode);
        if (resultCode == Activity.RESULT_OK) {
            String path = doPhoto(requestCode, data);
            if(requestCode==Crop.REQUEST_CROP){
                Log.e(TAG, "onActivityResult: "+path);
                AccessoryDbEntity entity = new AccessoryDbEntity();
                entity.setAccType(1);
                entity.setPssj(TimeFormatUtils.getCurrentTimeSeconds());
                entity.setSourcePath(path);
                entity.setThumbPath(BitmapUtil.createThumbnail(path));
                DbManager.getInstance().getAccessoryDao().insert(entity);
                accessoryLists.add(entity);
                accessoryAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String doPhoto(int requestCode, Intent data) {
        switch (requestCode) {
            case SELECT_TAKE_PHOTO:
                if (needResize) {
                    startPhotoZoom(takePhotoPath, zoomX, zoomY, zoomX, zoomY, SELECT_RESIZE_PHOTO);
                } else {
//                    this.lastIntent.putExtra("photo_path", takePhotoPath);
//                    setResult(Activity.RESULT_OK, this.lastIntent);
//                    finish();
                    Log.e(TAG, "doPhoto: "+takePhotoPath);
                    File file = new File(takePhotoPath);
                    String size = FileUtil.formatFileSize(file.length());
                    Log.e(TAG, "doPhoto: "+size);
                }
                return takePhotoPath;
//                break;
            case SELECT_PICK_PHOTO:
                String filePath = getImagePathFromUri(data.getData(), this);
                if (needResize) {
                    startPhotoZoom(filePath, zoomX, zoomY, zoomX, zoomY, SELECT_RESIZE_PHOTO);
                } else {
//                    this.lastIntent.putExtra("photo_path", filePath);
//                    setResult(Activity.RESULT_OK, this.lastIntent);
//                    finish();
                    Log.e(TAG, "doPhoto: "+filePath);
                    File file = new File(filePath);
                    String size = FileUtil.formatFileSize(file.length());
                    Log.e(TAG, "doPhoto: "+size);
                }
                return filePath;
//                break;
            case SELECT_RESIZE_PHOTO:
            case Crop.REQUEST_CROP:
//                this.lastIntent.putExtra("photo_path", resizedPicName);
//                setResult(Activity.RESULT_OK, this.lastIntent);
//                finish();
                Log.e(TAG, "doPhoto: "+resizedPicName);
                File file1 = new File(resizedPicName);
                String size = FileUtil.formatFileSize(file1.length());
                Log.e(TAG, "doPhoto: "+size);
                return resizedPicName;
//                break;
            case VIDEO_RECORD:
                if (data != null) {
                    String file = data.getStringExtra("video");
                    this.lastIntent.putExtra("photo_path", file);
                    setResult(Activity.RESULT_OK, this.lastIntent);
                    finish();
                }
                return null;
//                break;
            default:
                return null;
//                break;
        }
    }

    /**
     * 裁剪图片方法实现
     */
    private void startPhotoZoom(String filePath, int aspectX, int aspectY, int outputX, int outputY, int requestCode) {
        Uri uri;
        uri = getImageContentUri(new File(filePath));
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uri, "image/*");
//        intent.putExtra("crop", "true");
//        intent.putExtra("aspectX", aspectX);
//        intent.putExtra("aspectY", aspectY);
//        intent.putExtra("outputX", outputX);
//        intent.putExtra("outputY", outputY);
//        intent.putExtra("scale", true);         //黑边
//        intent.putExtra("scaleUpIfNeeded", true);   //黑边

//        File file = new File(resizedPicName);
        File file = getCreateFile();
        resizedPicName = file.getAbsolutePath();
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri outputUri;
        outputUri = Uri.fromFile(file);
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
//        intent.putExtra("return-data", false);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//        intent.putExtra("noFaceDetection", true); // no face detection
//
//        List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
//        for (ResolveInfo resolveInfo : resInfoList) {
//            String packageName = resolveInfo.activityInfo.packageName;
//            grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        }

        Log.i(TAG, "input = " + filePath);
        Log.i(TAG, "output = " + file);
//        startActivityForResult(intent, requestCode);
        Crop.of(uri, outputUri).asSquare().start(this);
    }

    /**
     * 将BitMap类型保存到本地
     */
    public boolean saveBit(Bitmap oBitmap, File oFile) {
        boolean result = false;
        // 如果文件不存在。则创建文件。注意。需要sd卡写入权限
        if (!oFile.exists()) {
            try {
                // 创建一个新文件
                oFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            // oFile.createNewFile();
            // 定义文件输出流
            FileOutputStream outputStream = new FileOutputStream(oFile);
            // 将bitmap的内容使用compress方法复制到file文件中
            oBitmap.compress(Bitmap.CompressFormat.JPEG, 95, outputStream);
            // 释放文件写入
            outputStream.flush();
            // 关闭文件源
            outputStream.close();
            System.out.println(TAG + "write pic suc");
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 保存裁剪之后的图片数据
     *
     * @param picdata
     */
    private void setPicToDisk(Intent picdata) {
        Bitmap photo = BitmapFactory.decodeFile(resizedPicName); //decodeUriAsBitmap(imageUri);
        String filename = getLocaleTime("yyyyMMddHHmmss") + ".jpg";
        String newPathName = LocalImgPath + filename;
        File oFile = new File(newPathName);
        if (saveBit(photo, oFile)) {
            System.out.println(TAG + oFile.getPath());
            this.lastIntent.putExtra("photo_path", newPathName);
            setResult(Activity.RESULT_OK, this.lastIntent);
            finish();
        }
    }


    public Uri getImageContentUri(File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    private File getCreateFile() {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = getExternalCacheDir().getPath();
        } else {
            cachePath = getCacheDir().getPath();
        }
        File dir = new File(cachePath);
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timeStamp = format.format(new Date());
        String fileName = "robot_" + timeStamp + ".png";
        File file = new File(dir, fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 根据uri获取文件路径
     *
     * @param fileUrl
     * @param context
     * @return
     */
    public static String getImagePathFromUri(Uri fileUrl, Context context) {
        String fileName = null;
        if (fileUrl != null) {
            if (fileUrl.getScheme().compareTo("content") == 0) {
                // content://开头的uri
                Cursor cursor = null;
                if (Build.VERSION.SDK_INT >= 19) {
                    if (isExternalStorageDocument(fileUrl)) {
                        final String docId = DocumentsContract.getDocumentId(fileUrl);
                        final String[] split = docId.split(":");
                        final String type = split[0];

                        if ("primary".equalsIgnoreCase(type)) {
                            return Environment.getExternalStorageDirectory() + "/" + split[1];
                        }
                    }
                    // DownloadsProvider
                    else if (isDownloadsDocument(fileUrl)) {
                        final String id = DocumentsContract.getDocumentId(fileUrl);
                        final Uri contentUri = ContentUris.withAppendedId(
                                Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                        return getDataColumn(context, contentUri, null, null);
                    }
                    // MediaProvider
                    else if (isMediaDocument(fileUrl)) {
                        final String docId = DocumentsContract.getDocumentId(fileUrl);
                        final String[] split = docId.split(":");
                        final String type = split[0];

                        Uri contentUri = null;
                        if ("image".equals(type)) {
                            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        } else if ("video".equals(type)) {
                            contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                        } else if ("audio".equals(type)) {
                            contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                        }

                        final String selection = "_id=?";
                        final String[] selectionArgs = new String[]{split[1]};

                        return getDataColumn(context, contentUri, selection, selectionArgs);
                    } else if (isFileExplorer(fileUrl)) {
                        String path = fileUrl.getPath();
                        int index = path.indexOf("external_files");
                        if (index >= 0) {
                            return Environment.getExternalStorageDirectory() + path.substring(index + "external_files".length(), path.length());
                        } else {
                            return null;
                        }
                    } else {
                        cursor = context.getContentResolver().query(fileUrl, null, null, null, null);
                    }
                } else {
                    cursor = context.getContentResolver().query(fileUrl, null, null, null, null);
                }
                if (cursor != null && cursor.moveToFirst()) {
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    fileName = cursor.getString(column_index); // 取出文件路径

                    // Android 4.1 更改了SD的目录，sdcard映射到/storage/sdcard0
                    if (!fileName.startsWith("/storage") && !fileName.startsWith("/mnt")) {
                        // 检查是否有”/mnt“前缀
                        fileName = "/mnt" + fileName;
                    }
                    cursor.close();
                }
            } else if (fileUrl.getScheme().compareTo("file") == 0) // file:///开头的uri
            {
                fileName = fileUrl.toString().replace("file://", "");
                int index = fileName.indexOf("/sdcard");
                fileName = index == -1 ? fileName : fileName.substring(index);
//                if (!fileName.startsWith("/mnt"))
//                {
//                    // 加上"/mnt"头
//                    fileName = "/mnt" + fileName;
//                }
            }
        }
        return fileName;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static String getLocaleTime(String format) {
        SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat(format);
        return simpleDateTimeFormat.format(Calendar.getInstance(
                Locale.CHINESE).getTime());
    }

    public static boolean isFileExplorer(Uri uri) {
        return "com.android.fileexplorer.myprovider".equals(uri.getAuthority());
    } }
