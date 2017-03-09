package com.example.administrator.bmobtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

public class loginActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText edtUser;
    private EditText edtPass;
    private TextView tvReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();


    }

    private void initViews() {
        btnLogin= (Button) findViewById(R.id.btn_login);
        edtUser= (EditText) findViewById(R.id.edt_username);
        edtPass= (EditText) findViewById(R.id.edt_pass);
        tvReg= (TextView) findViewById(R.id.tv_register);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=edtUser.getText().toString().trim();
                String password=edtPass.getText().toString().trim();

                BmobUser.loginByAccount(name, password, new LogInListener<BmobUser>() {
                    @Override
                    public void done(BmobUser user, BmobException e) {
                        if(user != null){
                            Toast.makeText(loginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
            }
        });

        tvReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginActivity.this,registerActivity.class));
            }
        });



    }
}
