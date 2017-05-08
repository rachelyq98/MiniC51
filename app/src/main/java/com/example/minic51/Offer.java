package com.example.minic51;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

/**
 * Created by rachelliu on 2017-05-08.
 */

public class Offer {
    private String name;
    private Bitmap icon;

    public Offer (String name, String iconName){
        this.name = name;
        convert(iconName);
    }

    public String getName() {
        return name;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(String iconName) {
        convert(iconName);
    }

    private void convert (String iconName){
        byte [] iconBytes = Base64.decode(iconName, Base64.DEFAULT);
        this.icon = BitmapFactory.decodeByteArray(iconBytes, 0, iconBytes.length);
    }
}
