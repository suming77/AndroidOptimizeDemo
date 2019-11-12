package com.example.androidoptimizedemo.ImageOpt;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.androidoptimizedemo.R;
import com.example.androidoptimizedemo.utils.PermissionUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @创建者 mingyan.su
 * @创建时间 2019/11/8 9:44
 * @类描述 ${TODO}图片优化
 */
public class ImageOptActivity extends AppCompatActivity implements PermissionUtil.RequestPermissionCallBack,
        View.OnClickListener {
    private static final String TAG = ImageOptActivity.class.getSimpleName();

    private ImageView mIv_quality;
    private ImageView mIv_samplingRate;
    private ImageView mIv_sizeCompress;
    private ImageView mIv_matrix;

    //权限
    private String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_opt);

        findViewById(R.id.btn_quality_compress).setOnClickListener(this);
        findViewById(R.id.btn_sampling_rate_compress).setOnClickListener(this);
        findViewById(R.id.btn_size_compress).setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);

        mIv_quality = findViewById(R.id.iv_quality);
        mIv_samplingRate = findViewById(R.id.iv_samplingRate);
        mIv_sizeCompress = findViewById(R.id.iv_sizeCompress);
        mIv_matrix = findViewById(R.id.iv_matrix);

        //获取权限
        PermissionUtil.checkPermission(this, permissions, 1001, this);
    }

    @Override
    public void onClick(View v) {
        //获取图片
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.woman);
        Log.e(TAG, "压缩前：" + getBitmapSize(bitmap));

        //存放文件相关
        File sdDir = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        File shareImgDir = new File(sdDir + File.separator + "imageOpt");
        if (!shareImgDir.exists()) {
            shareImgDir.mkdir();
        }

        switch (v.getId()) {
            case R.id.btn_quality_compress://质量压缩
                qualityCompress(bitmap, new File(shareImgDir, "imageOpt_woman" + ".JPG"));
                break;
            case R.id.btn_sampling_rate_compress://采样率压缩
                samplingRateCompress("", new File(shareImgDir, "imageOpt_woman2" + ".JPG"));
                break;
            case R.id.btn_size_compress://尺寸压缩
                sizeCompress(bitmap, new File(shareImgDir, "imageOpt_woman3" + ".JPG"));
                break;
            case R.id.tv_back://返回
                finish();
                break;
        }
    }

    /**
     * 质量压缩
     * 设置Bitmap options属性，降低图片质量，图片像素不会减少
     *
     * @param bitmap 需要压缩的Bitmap图片对象
     * @param file   压缩后图片保存的位置
     */
    private void qualityCompress(Bitmap bitmap, File file) {
        int quality = 80;//0-100,数值越小表示质量越低，100表示不压缩
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //把压缩后的数据放到bos中
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);//Bitmap.CompressFormat.PNG表示图片压缩的格式
        try {
            //创建文件输出流
            FileOutputStream fos = new FileOutputStream(file);
            //将bos中的数据写入到fos中
            fos.write(bos.toByteArray());
            Log.e(TAG, "质量压缩后：" + fos.getChannel().size());
            mIv_quality.setImageBitmap(BitmapFactory.decodeFile(file.toString()));
            fos.flush();
            fos.close();
            bos.close();

            // 插入图库
            String[] strings = file.getName().split("/");
            String bitName = strings[strings.length - 1];

            Log.e(TAG, "bitName===" + bitName);
            Log.e(TAG, "file.getAbsolutePath()--" + file.getAbsolutePath() + "  | path==" + file.getPath());
            MediaStore.Images.Media.insertImage(this.getApplicationContext().getContentResolver(),
                    file.getAbsolutePath(), bitName, null);
            MediaScannerConnection.scanFile(this.getApplicationContext(), new String[]{file.getAbsolutePath()}, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 发送广播，通知刷新图库的显示
        this.getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
    }

    /**
     * 采样率压缩
     *
     * @param path 文件路径
     * @param file 压缩后图片保存的位置
     */
    private void samplingRateCompress(String path, File file) {
        //数值越高，像素越低
        int inSampleSize = 3;
        //设置采样率
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;// true时不会真正加载图片，而是得到图片的宽高信息
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.woman, options);
//        Bitmap bitmap = BitmapFactory.decodeResource(path, options);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //把压缩后的数据放到bos中
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        try {
            //创建文件输出流
            FileOutputStream fos = new FileOutputStream(file);
            //将bos中的数据写入到fos中
            fos.write(bos.toByteArray());
            Log.e(TAG, "采样率压缩后：" + fos.getChannel().size());
            mIv_samplingRate.setImageBitmap(BitmapFactory.decodeFile(file.toString()));
            setMatrix(bitmap, mIv_matrix);

            fos.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 尺寸压缩：通过缩放图片像素来减少图片占用内存大小
     *
     * @param bitmap 需要压缩的Bitmap图片对象
     * @param file   压缩后图片保存的位置
     */
    private void sizeCompress(Bitmap bitmap, File file) {
        //压缩尺寸倍数，值越大，尺寸越小
        int ratio = 3;
        //压缩Bitmap得到对应的尺寸
        Bitmap result = Bitmap.createBitmap(bitmap.getWidth() / ratio, bitmap.getHeight() / ratio, Bitmap.Config.ARGB_8888);
        //创建画笔
        Canvas canvas = new Canvas(result);
        //创建矩形
        RectF rectF = new RectF(0, 0, bitmap.getWidth() / ratio, bitmap.getHeight() / ratio);
        //将原图画在缩放之后的矩形之上
        canvas.drawBitmap(result, null, rectF, null);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //把压缩后的数据放到bos中
        result.compress(Bitmap.CompressFormat.JPEG, 100, bos);

        try {
            //创建文件输出流
            FileOutputStream fos = new FileOutputStream(file);
            //将bos中的数据写入到fos中
            fos.write(bos.toByteArray());
            Log.e(TAG, "尺寸压缩后：" + fos.getChannel().size());
            mIv_sizeCompress.setImageBitmap(BitmapFactory.decodeFile(file.toString()));
            fos.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取bitmap大小
     *
     * @param bitmap
     * @return
     */
    private int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            return bitmap.getByteCount();
        }
        // 在低版本中用一行的字节x高度
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    /**
     * 设置矩阵
     *
     * @param bitmap    图片
     * @param imageView 图片控件
     */
    private void setMatrix(Bitmap bitmap, ImageView imageView) {
        Matrix matrix = new Matrix();
//        matrix.setScale(2, 2, 0f, 0f);
//        canvas.concat(matrix);
//        canvas.drawBitmap(bitmap, 0, 0, paint);
//        //或者
//        canvas.drawBitmap(bitmap, matrix, paint);

        matrix.postScale(2, 2, 0, 0);
        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        imageView.setImageMatrix(matrix);
        imageView.setImageBitmap(bitmap);
    }

    //4.重写权限回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtil.onRequestPermissionsResult(this, requestCode, permissions, grantResults, this);
    }

    @Override
    public void granted() {
        Toast.makeText(this, "获取权限成功，执行正常操作", Toast.LENGTH_LONG).show();
    }

    @Override
    public void denied() {
        Toast.makeText(this, "获取权限失败，正常功能受到影", Toast.LENGTH_LONG).show();
    }
}