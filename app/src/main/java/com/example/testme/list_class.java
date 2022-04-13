package com.example.testme;

import android.graphics.drawable.Drawable;

public class list_class {
    String app_name;
    int color_code;


    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public int getColor_code() {
        return color_code;
    }

    public void setColor_code(int color_code) {
        this.color_code = color_code;
    }

    public list_class(String app_name, int color_code) {
        this.app_name = app_name;
        this.color_code = color_code;
    }
}
