package com.example.acer.collectionplus.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v7.widget.AppCompatImageView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.acer.collectionplus.Adapter.UserAdapter;

import com.example.acer.collectionplus.Helper.SharedHelper;
import com.example.acer.collectionplus.R;

import com.example.acer.collectionplus.UIUtils.ImageUtils;
import com.example.acer.collectionplus.ViewModel.UserVM;
import com.example.acer.collectionplus.databinding.FragmentMainBinding;
import com.example.acer.collectionplus.databinding.FragmentUserBinding;
import com.leon.lib.settingview.LSettingItem;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;
import static com.example.acer.collectionplus.View.PhotoUtils.getDataColumn;
import static com.example.acer.collectionplus.View.PhotoUtils.isDownloadsDocument;
import static com.example.acer.collectionplus.View.PhotoUtils.isExternalStorageDocument;
import static com.example.acer.collectionplus.View.PhotoUtils.isGooglePhotosUri;
import static com.example.acer.collectionplus.View.PhotoUtils.isMediaDocument;


public class UserFragment extends Fragment implements IUserFragmentView
{

    public static final String TAG = "UserFragment";
    private  FragmentUserBinding binding;

    private UserVM userVM;
    //定义
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;

    private ImageView photo;
    //private LSettingItem  change_Item;
    private ImageView change_image;


    private static final int  CODE_GALLERY_REQUEST = 0xa0;
    private static final int  CODE_CAMERA_REQUEST  = 0xa1;
    private static final int  CODE_RESULT_REQUEST  = 0xa2;
    private              File fileUri              = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    private              File fileCropUri          = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private Uri imageUri;
    private Uri cropImageUri;

    private OnButtonClick onButtonClick;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("aaa","进来啦");
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_user,container,false);

        this.userVM = new UserVM(this);
        //将binding传给VM层

        userVM.getbinding(binding);

        //修改用户头像部分
        photo=binding.userImage;
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showChoosePicDialog(v);
            }
        });
        //点击跳转修改信息界面
        //设置点击事件
        change_image=binding.setButton;
       change_image.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getActivity(), ChangeActivity.class);
               startActivity(intent);

           }
       });

        //为fragment绑定用户界面
        return binding.getRoot();
    }

    /*------------------------------------------------------*/
    /**
     * 显示修改头像的对话框
     */
    public void showChoosePicDialog(final View v) {
        Log.d("找到方法","true");
        Log.d("绑定activity",getActivity().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("设置头像");
        String[] items = { "选择本地照片", "拍照" };
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        getPhoto(v);

                        break;
                    case TAKE_PICTURE: // 拍照
                        takePhoto(v);

                        break;
                }

            }
        });
        builder.create().show();
    }

    /**
     * 拍照
     * @param view
     */
    public void takePhoto(View view) {
        Log.d("aaa","take_photo");
        if (hasSdcard()) {
            imageUri = Uri.fromFile(fileUri);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                //通过FileProvider创建一个content类型的Uri
                imageUri = FileProvider.getUriForFile(getActivity(), "com.MainActivity.provider", fileUri);

            PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
        } else {
            Toast.makeText(getActivity(), "设备没有SD卡！", Toast.LENGTH_SHORT).show();
            Log.e("asd", "设备没有SD卡");
        }
    }
/**
 * 修改头像方法类---------------------------------------------------------------------------
 */
    /**
     * 调用系统相册
     * @param view
     */
    public void getPhoto(View view) {
        PhotoUtils.openPic(UserFragment.this,CODE_GALLERY_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int output_X = 480, output_Y = 480;
        Log.d("onresult","success");

        switch (requestCode) {
            case CODE_CAMERA_REQUEST://拍照完成回调
                cropImageUri = Uri.fromFile(fileCropUri);
                PhotoUtils.cropImageUri(this, imageUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                break;
            case CODE_GALLERY_REQUEST://访问相册完成回调
                if (hasSdcard()) {
                    cropImageUri = Uri.fromFile(fileCropUri);
                    Log.d("相册回调","success");
                    Log.d("getpath",PhotoUtils.getPath(getActivity(), data.getData()));
                    Uri newUri = Uri.parse(PhotoUtils.getPath(getActivity(), data.getData()));

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                        newUri = FileProvider.getUriForFile(getActivity(), "com.MainActivity.provider", new File(newUri.getPath()));
                    PhotoUtils.cropImageUri(this, newUri, cropImageUri, 1, 1, output_X, output_Y, CODE_RESULT_REQUEST);
                } else {
                    Toast.makeText(getActivity(), "设备没有SD卡!", Toast.LENGTH_SHORT).show();
                }
                break;
            case CODE_RESULT_REQUEST:
                Bitmap bitmap = PhotoUtils.getBitmapFromUri(cropImageUri, getActivity());
                if (bitmap != null) {
                    showImages(bitmap);
                }
                break;
        }

    }

    /**
     * 展示图片
     * @param bitmap
     */
    private void showImages(Bitmap bitmap) {
        photo.setImageBitmap(bitmap);
    }


    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 点击事件
     *
     */
    public OnButtonClick getOnButtonClick() {
        return onButtonClick;
    }
    public void setOnButtonClick(OnButtonClick onButtonClick) {
        this.onButtonClick = onButtonClick;
    }
    public interface OnButtonClick{
        public void onClick(View view);
    }
    //-------------------------------------------------------------------
    @Override
    public void loadStart(int loadType) {

    }

    @Override
    public void loadComplete() {

    }

    @Override
    public void loadFailure(String message) {

    }
}
