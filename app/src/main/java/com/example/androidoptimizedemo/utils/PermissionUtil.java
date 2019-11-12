package com.example.androidoptimizedemo.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 mingyan.su
 * @创建时间 2018/10/23 11:05
 * @类描述 ${TODO}权限工具类
 * 没有做小米，vivo手机权限处理
 */
public class PermissionUtil {

    private static StringBuilder sPermissionNames;//提示框显示的权限名称

    /**
     * 检测权限
     *
     * @param activity
     * @param permissions //请求的权限组
     * @param requestCode //请求码
     */
    public static void checkPermission(final Activity activity, final String[] permissions,
                                       final int requestCode, RequestPermissionCallBack permissionInterface) {
        List<String> deniedPermissions = findDeniedPermissions(activity, permissions);
        if (deniedPermissions != null && deniedPermissions.size() > 0) {
            //大于0,表示有需要的权限但没有申请
            PermissionUtil.requestContactsPermissions(activity,
                    deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
        } else {
            //已经拥有权限
            permissionInterface.granted();
        }
    }

    /**
     * 请求权限
     */
    public static void requestContactsPermissions(final Activity activity, final String[] permissions,
                                                  final int requestCode) {
        //第一次默认返回false,如果点击了禁止授权(没勾选不在提示)则返回true,可以直接提示重新获取权限框
        // 如果点击了禁止授权，并且勾选了不再提示框则返回false，这种情况可以留到回调再处理
        if (shouldShowPermissions(activity, permissions)) {//不在访问权限，直接跳转到设置权限页面
            new AlertDialog.Builder(activity)
                    .setMessage("【用户曾经拒绝过你的请求，所以这次发起请求时解释一下】\r\n" +
                            "您好，需要如下权限：\r\n" +
                            sPermissionNames +
                            " 请允许，否则将影响部分功能的正常使用。")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(activity, permissions, requestCode);
                        }
                    }).show();
        } else {
            //直接请求权限
            //如果用户点了不再询问,即使调用请求权限也不会出现请求权限的对话框，可以在回调中处理
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
        }
    }

    /**
     * 判断请求权限是否成功
     *
     * @param grantResults
     * @return
     */
    public static boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 在传入的权限中，判断是否授权
     *
     * @param activity
     * @param permission 权限
     * @return 没有授权的权限
     */
    public static List<String> findDeniedPermissions(Activity activity, String... permission) {
        //存储没有授权的权限
        List<String> denyPermissions = new ArrayList<>();
        for (String value : permission) {
            //判断改权限是否已授权
            if (ContextCompat.checkSelfPermission(activity, value) != PackageManager.PERMISSION_GRANTED) {
                //没有权限 就添加
                denyPermissions.add(value);
            }
        }
        return denyPermissions;
    }

    /**
     * 检测这些权限中是否有 没有授权需要提示的
     *
     * @param activity
     * @param permission
     * @return 是否需要提示
     */
    public static boolean shouldShowPermissions(final Activity activity, String... permission) {
        sPermissionNames = new StringBuilder();
        for (String value : permission) {
            sPermissionNames = sPermissionNames.append(value + "\r\n");
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 申请权限返回方法
     */
    public static void onRequestPermissionsResult(final Activity activity, final int requestCode, @NonNull String[] permissions,
                                                  @NonNull int[] grantResults, final RequestPermissionCallBack permissionInterface) {
        if (PermissionUtil.verifyPermissions(grantResults)) {//允许权限，有权限
            if (permissionInterface != null) {
                permissionInterface.granted();
            }
        } else {
            //拒绝了权限，没有权限
            //第一次默认返回false,当用户之前已经请求过该权限并且拒绝了授权但没有勾选了不再提示返回true，false表示禁止了权限并且勾选了不再提示
            if (PermissionUtil.shouldShowPermissions(activity, permissions)) {
                //用户拒绝权限请求，但未选中“不再提示”选项
                if (permissionInterface != null) {
                    permissionInterface.denied();
                }
            } else {
                //用户拒绝权限请求，并且勾选了不再提示
                new AlertDialog.Builder(activity)
                        .setMessage("【用户选择了不在提示按钮，或者系统默认不在提示（如MIUI）。" +
                                "引导用户到应用设置页去手动授权,注意提示用户具体需要哪些权限】\r\n" +
                                "获取相关权限失败:\r\n" +
                                sPermissionNames +
                                "将导致部分功能无法正常使用，需要到设置页面手动授权")
                        .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startApplicationDetailsSettings(activity, requestCode);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (permissionInterface != null) {
                                    permissionInterface.denied();
                                }
                            }
                        }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if (permissionInterface != null) {
                            permissionInterface.denied();
                        }
                    }
                }).show();
            }
        }
    }

    /**
     * 打开app详细设置界面<br/>
     * <p>
     * 在 onActivityResult() 中没有必要对 resultCode 进行判断，因为用户只能通过返回键才能回到我们的 App 中，<br/>
     * 所以 resultCode 总是为 RESULT_CANCEL，所以不能根据返回码进行判断。<br/>
     * 在 onActivityResult() 中还需要对权限进行判断，因为用户有可能没有授权就返回了！<br/>
     */
    public static void startApplicationDetailsSettings(@NonNull Activity activity, int requestCode) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, requestCode);
    }


    /**
     * 权限请求结果回调接口
     */
    public interface RequestPermissionCallBack {
        /**
         * 同意授权
         */
        public void granted();

        /**
         * 取消授权
         */
        public void denied();
    }
}