package com.example.administrator.bmobtest;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Administrator on 2017/3/6.
 */

public class ActivityB extends AppCompatActivity{
    private static final int GET_IMAGE = 1001;
    private ImageView mImageView;
    private EditText edtName,edtId;
    private Button btnSave,btnCho;
    private onItemClickListener mOnItemClickListener;
    private String path;

    String mPath;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

        String[] permissions=new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
        if(ContextCompat.checkSelfPermission(ActivityB.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(permissions,1001);
        }

        initViews();

        getData();

    }

    private void getData() {
        String image=getIntent().getStringExtra("image");
        String name=getIntent().getStringExtra("name");
        String id=getIntent().getStringExtra("id");

        mImageView.setImageBitmap(BitmapFactory.decodeFile(image));
        edtName.setText(name);
        edtId.setText(id);

    }

    private void initViews() {
        mImageView= (ImageView) findViewById(R.id.iv_image);
        edtName= (EditText) findViewById(R.id.edt_Name);
        edtId= (EditText) findViewById(R.id.edt_id);
        btnSave= (Button) findViewById(R.id.btn_save);
        btnCho= (Button) findViewById(R.id.btn_choose);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*mImageView.setDrawingCacheEnabled(true);
                Bitmap bitmap=Bitmap.createBitmap(mImageView.getDrawingCache());
                mImageView.setDrawingCacheEnabled(false);*/

                BmobFile bmobfile=new BmobFile(new File(path));
                bmobfile.uploadblock(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        String name=edtName.getText().toString().trim();
                        String id=edtId.getText().toString().trim();
                        String image=path;

                        Student stu=new Student();
                        stu.setImage(image);
                        stu.setName(name);
                        stu.setId(id);

                        stu.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if(e==null){
                                    Toast.makeText(ActivityB.this, "添加成功", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ActivityB.this,MainActivity.class));
                                } else {
                                    Toast.makeText(ActivityB.this, "添加失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

            }
        });


        btnCho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,GET_IMAGE);

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==GET_IMAGE){
            try{
            Uri uri=data.getData();
            ContentResolver content=this.getContentResolver();
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(content,uri);
            mImageView.setImageBitmap(bitmap);

            String[] mediastore={MediaStore.Images.Media.DATA};
            Cursor cur=managedQuery(uri,mediastore,null,null,null);
            int index=cur.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cur.moveToFirst();
            path=cur.getString(index);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

                Log.e("image--error",e.toString());
            }

        }



    }

}
