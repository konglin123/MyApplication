package com.example.a12902.myapplication.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.example.a12902.myapplication.app.MyApp;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * @author ChenYe
 *         created by on 2018/1/23 0023. 09:17
 **/

public class BitmapUtil {

    /**
     * 获取所有图片路径
     *
     * @param context
     * @return
     */
    public static List<String> getAllPicPath(Context context) {
        List<String> urls = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor mCursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.ImageColumns.DATA}, "", null,
                MediaStore.MediaColumns.DATE_ADDED + " DESC");
        if (mCursor.moveToFirst()) {
            int date = mCursor.getColumnIndex(MediaStore.Images.Media.DATA);
            do {
                // 获取图片的路径
                String path = mCursor.getString(date);
                Boolean has = false;
                if (has) {
                    continue;
                }
                urls.add(path);
            } while (mCursor.moveToNext());
        }
        mCursor.close();
        Log.e("BitmapUtil", "当前手机共有照片" + urls.size() + "张");
        return urls;
    }

    /**
     * 通过传过来的图片路径去返回该图片的缩略图路径，如果不存在缩略图
     * 就返回null
     *
     * @param context
     * @param path
     * @return
     */
    public static String queryImageThumbnailByPath(Context context, String path) {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{MediaStore.Images.Media._ID};
        String selection = MediaStore.Images.Media.DATA + " = ? ";
        String[] selectionArgs = new String[]{path};

        Cursor cursor = query(context, uri, projection, selection,
                selectionArgs);
        int id = -1;
        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
        }
        cursor.close();
        if (id == -1) {
            return null;
        }

        uri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
        projection = new String[]{MediaStore.Images.Thumbnails.DATA};
        selection = MediaStore.Images.Thumbnails.IMAGE_ID + " = ? ";
        selectionArgs = new String[]{String.valueOf(id)};

        cursor = query(context, uri, projection, selection, selectionArgs);
        String thumbnail = null;
        if (cursor.moveToFirst()) {
            int idxData = cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA);
            thumbnail = cursor.getString(idxData);
        }
        cursor.close();
        return thumbnail;
    }

    private static Cursor query(Context context, Uri uri, String[] projection,
                                String selection, String[] selectionArgs) {
        ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(uri, projection, selection, selectionArgs,
                null);
        return cursor;
    }

    /**
     * 传入bitmap,然后设置项设置的缩略图宽高，返回缩略图(bitmap对象)
     *
     * @param source
     */
    public static Bitmap extractMiniThumb(Bitmap source, int width, int height, boolean recycle) {
        if (source == null) {
            return null;
        }

        float scale;
        if (source.getWidth() < source.getHeight()) {
            scale = width / (float) source.getWidth();
        } else {
            scale = height / (float) source.getHeight();
        }
        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);
        Bitmap miniThumbnail = transform(matrix, source, width, height, false);

        //是否释放原图资源
        if (recycle && miniThumbnail != source) {
            source.recycle();
        }
        return miniThumbnail;
    }

    /**
     * 生成缩略图的核心代码
     *
     * @param scaler
     * @param source
     * @param targetWidth
     * @param targetHeight
     * @param scaleUp
     * @return
     */
    public static Bitmap transform(Matrix scaler, Bitmap source,
                                   int targetWidth, int targetHeight, boolean scaleUp) {
        int deltaX = source.getWidth() - targetWidth;
        int deltaY = source.getHeight() - targetHeight;
        if (!scaleUp && (deltaX < 0 || deltaY < 0)) {
            Bitmap b2 = Bitmap.createBitmap(targetWidth, targetHeight,
                    Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b2);

            int deltaXHalf = Math.max(0, deltaX / 2);
            int deltaYHalf = Math.max(0, deltaY / 2);
            Rect src = new Rect(deltaXHalf, deltaYHalf, deltaXHalf
                    + Math.min(targetWidth, source.getWidth()), deltaYHalf
                    + Math.min(targetHeight, source.getHeight()));
            int dstX = (targetWidth - src.width()) / 2;
            int dstY = (targetHeight - src.height()) / 2;
            Rect dst = new Rect(dstX, dstY, targetWidth - dstX, targetHeight
                    - dstY);
            c.drawBitmap(source, src, dst, null);
            return b2;
        }
        float bitmapWidthF = source.getWidth();
        float bitmapHeightF = source.getHeight();

        float bitmapAspect = bitmapWidthF / bitmapHeightF;
        float viewAspect = (float) targetWidth / targetHeight;

        if (bitmapAspect > viewAspect) {
            float scale = targetHeight / bitmapHeightF;
            if (scale < .9F || scale > 1F) {
                scaler.setScale(scale, scale);
            } else {
                scaler = null;
            }
        } else {
            float scale = targetWidth / bitmapWidthF;
            if (scale < .9F || scale > 1F) {
                scaler.setScale(scale, scale);
            } else {
                scaler = null;
            }
        }

        Bitmap b1;
        if (scaler != null) {
            // this is used for minithumb and crop, so we want to filter here.
            b1 = Bitmap.createBitmap(source, 0, 0, source.getWidth(),
                    source.getHeight(), scaler, true);
        } else {
            b1 = source;
        }

        int dx1 = Math.max(0, b1.getWidth() - targetWidth);
        int dy1 = Math.max(0, b1.getHeight() - targetHeight);

        Bitmap b2 = Bitmap.createBitmap(b1, dx1 / 2, dy1 / 2, targetWidth,
                targetHeight);

        if (b1 != source) {
            b1.recycle();
        }
        return b2;
    }

    /**
     * 获取bitmap大小,但是bitmap大小不带照片的文件大小，一般bitmap比照片原本的文件大小要大很多,
     * 要获取原本的照片文件大小，可以直接在res/raw目录下把照片放在哪里用new File然后读出来
     *
     * @param bitmap
     * @return
     */
    public static int getBitmapSize(Bitmap bitmap) {
        //API 19
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return bitmap.getAllocationByteCount();
        }
        //API 12
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getByteCount();
        }
        //earlier version
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    /**
     * 将Bitmap转换成文件
     * 保存文件
     *
     * @param bm
     * @throws IOException
     */
    public static File saveFile(Bitmap bm) {
        File file = FileUtil.getOutputMediaFile(2, null);
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("File", "保存缩略图出错啦");
            return null;
        } finally {
            try {
                if (bos != null) {
                    bos.flush();
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 把bitmap保存成文件
     *
     * @param bitmap 传进来的是要压缩的图片和要压缩的图片的名字
     */
    public static String saveBitmap(Bitmap bitmap) {
        File file = FileUtil.getOutputMediaFile(4, null);
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("File", "保存缩略图出错啦" + e.getMessage());
        } finally {
            try {
                if (bos != null) {
                    bos.flush();
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (bitmap != null) {
            bitmap.recycle();
            Log.e("BitmapUtil", "保存的图片大小是" + FileUtil.formatFileSize(file.length()));
        } else {
            Log.e("BitmapUtil", "bitmap == null,请查看原因");
        }
        return file.getPath();
    }

    /**
     * 因为原图的缩略图不存在或者找不到，所以就调用这个方法，用原图来自己创建缩略图。
     *
     * @param sourPath 原图路径
     * @return 创建的缩略图byte[]
     */
    public static String createThumbnail(String sourPath) {
        Bitmap sourBitmap = BitmapFactory.decodeFile(sourPath);
        Bitmap thumBitmap = BitmapUtil.extractMiniThumb(sourBitmap, 200, 200, true);
        File thumbFile = BitmapUtil.saveFile(thumBitmap);
        if (thumbFile != null) {
            String fileSize = FileUtil.formatFileSize(thumbFile.length());
            Log.e("BitmapUtil", "缩略地址是:" + thumbFile.getAbsolutePath() + ",大小:" + fileSize);
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        thumBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return thumbFile.getAbsolutePath();
    }

    /**
     * 保存刚才选择的照片和生成这些照片对应的缩略图
     * 根据传进来的原路路径List,去批量创建缩略图，然后返回原路径与缩略图路径的map:
     * Map<原图路径，缩略图路径>
     *
     * @return
     */
    public static Map<String, String> createThumbAndSave(List<String> paths) {
        Map<String, String> thumbs = new HashMap<>(paths.size());
        for (String path : paths) {
            String thumbnail = "";
            try {
                thumbnail = BitmapUtil.createThumbnail(path);
                thumbs.put(path, thumbnail);
            } catch (Exception e) {
                thumbs.remove(path);
                Toast.makeText(MyApp.getInstance(), "添加" + paths + "失败", Toast.LENGTH_SHORT).show();
            }
        }
        return thumbs;
    }


    /**
     * 获取视频的缩略图
     * 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
     * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
     *
     * @param videoPath 视频的路径
     * @param width     指定输出视频缩略图的宽度
     * @param height    指定输出视频缩略图的高度度
     * @param kind      参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
     *                  其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
     * @return 指定大小的视频缩略图
     */
    public static Bitmap getVideoThumbnail(String videoPath, int width, int height,
                                           int kind) {
        Bitmap bitmap = null;
        // 获取视频的缩略图
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }
}
