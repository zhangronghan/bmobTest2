package com.example.administrator.bmobtest;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/3/6.
 */

public class Student extends BmobObject{
    String image;
    String name;
    String id;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
