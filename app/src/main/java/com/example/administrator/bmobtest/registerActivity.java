package com.example.administrator.bmobtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class registerActivity extends AppCompatActivity {
    private EditText edtName;
    private EditText edtPhone;
    private EditText edtNum;
    private EditText edtPass;
    private Button btnNum;
    private Button btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();

    }


    private void initViews() {
        edtName = (EditText) findViewById(R.id.edt_name1);
        edtPhone = (EditText) findViewById(R.id.edt_phone1);
        edtNum = (EditText) findViewById(R.id.edt_num1);
        edtPass = (EditText) findViewById(R.id.edt_password1);
        btnNum = (Button) findViewById(R.id.btn_num1);
        btnReg = (Button) findViewById(R.id.btn_reg1);

        btnNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = edtPhone.getText().toString().trim();
                BmobSMS.requestSMSCode(phone, "bmobTest", new QueryListener<Integer>() {
                    @Override
                    public void done(Integer smsId, BmobException e) {
                        if (e == null) {//验证码发送成功
                            Log.i("smile", "短信id：" + smsId);//用于查询本次短信发送详情
                        }
                    }
                });
            }
        });


        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone = edtPhone.getText().toString().trim();
                String num = edtNum.getText().toString().trim();

                BmobSMS.verifySmsCode(phone, num, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Log.d("bmobtest", "验证成功");

                            BmobUser user = new BmobUser();
                            String name = edtName.getText().toString().trim();
                            String password = edtPass.getText().toString().trim();
                            user.setUsername(name);
                            user.setPassword(password);
                            user.setMobilePhoneNumber(phone);
                            user.setMobilePhoneNumberVerified(true);

                            user.signUp(new SaveListener<BmobUser>() {
                                @Override
                                public void done(BmobUser user, BmobException e) {
                                    if (e == null) {
                                        Toast.makeText(registerActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                        /*startActivity(new Intent(registerActivity.this, loginActivity.class));*/
                                    }
                                }
                            });
                        } else {
                            Log.i("验证失败", "code=" + e.getErrorCode() + "  msg=" + e.getLocalizedMessage());
                        }

                    }
                });

            }
        });
    }

}





