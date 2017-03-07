package com.example.administrator.bmobtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017/3/6.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private List<Student> stuList;
    private onDelListener mListener;
    private onItemClickListener mOnItemClickListener;

    public MyAdapter(List<Student> stuList,onDelListener mListener) {
        this.stuList = stuList;
        this.mListener=mListener;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view,mOnItemClickListener);
        return myViewHolder;
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.mOnItemClickListener=listener;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Student stu=stuList.get(position);
        String imagePath=stu.getImage();
       /*File file=new File(imagePath);*/
        Bitmap bitmap= BitmapFactory.decodeFile(imagePath);
        holder.mImageView.setImageBitmap(bitmap);
        holder.tvStuName.setText(stu.getName());
        holder.tvStuId.setText(stu.getId());

        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stu.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        mListener.refresh();
                    }
                });

            }
        });


    }

    @Override
    public int getItemCount() {
        return stuList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mImageView,ivDel;
        private TextView tvStuName,tvStuId;
        private onItemClickListener listener;

        public MyViewHolder(View itemView,onItemClickListener listener) {
            super(itemView);
            ivDel= (ImageView) itemView.findViewById(R.id.iv_del);
            mImageView= (ImageView) itemView.findViewById(R.id.iv_stu);
            tvStuName= (TextView) itemView.findViewById(R.id.tv_stuName);
            tvStuId= (TextView) itemView.findViewById(R.id.tv_stuId);
            this.listener=listener;
            itemView.setOnClickListener(this);
        }

        public void onClick(View v){
            if(listener!=null){
                listener.onItemClick(v,getPosition());
            }
        }

    }



}
