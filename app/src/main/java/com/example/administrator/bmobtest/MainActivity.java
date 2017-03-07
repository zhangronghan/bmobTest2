package com.example.administrator.bmobtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MainActivity extends AppCompatActivity implements onDelListener,onItemClickListener{
    private RecyclerView mRecyclerView;
    private Button btnAdd;
    private MyAdapter myAdapter;
    private onItemClickListener mOnItemClickListener;
    private List<Student> stuList=new ArrayList<Student>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bmob.initialize(this,"5fb59b4c3c9f91b5de88d35c51cbb0a8");

        initViews();

        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        stuList=findAll();
        myAdapter=new MyAdapter(stuList,MainActivity.this);
        this.myAdapter.setOnItemClickListener(this);

        mRecyclerView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }


    private void initViews() {
        mRecyclerView= (RecyclerView) findViewById(R.id.recyclerview);
        btnAdd= (Button) findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ActivityB.class);
                startActivity(intent);

            }
        });

    }



    private List<Student> findAll() {
        stuList.clear();
        BmobQuery<Student> query=new BmobQuery<Student>();

        query.findObjects(new FindListener<Student>() {
            @Override
            public void done(List<Student> list, BmobException e) {
                if(e==null){
                    for(Student stu:list){
                        stuList.add(stu);
                    }
                    myAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "出错", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return stuList;
    }

    @Override
    public void refresh() {
        stuList=findAll();
    }

    @Override
    public void onItemClick(View view, int position) {
        Student stu=stuList.get(position);

        String image=stu.getImage();
        String name=stu.getName();
        String id=stu.getId();

        Intent intent=new Intent(MainActivity.this,ActivityB.class);
        intent.putExtra("name",name);
        intent.putExtra("image",image);
        intent.putExtra("id",id);
        startActivity(intent);
    }
}
