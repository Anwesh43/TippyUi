package com.anwesome.ui.tippyui;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by anweshmishra on 15/01/17.
 */
public class TippyUiUtil {
    public static void createTippyUi(Context context,String text) {
        TippyUi tippyUi = new TippyUi(context);
        tippyUi.setMessageText(text);
        ((Activity)context).addContentView(tippyUi,new ViewGroup.LayoutParams(200,200));
    }
    public static void createTippyUi(Context context,String text,float x,float y) {
        TippyUi tippyUi = new TippyUi(context);
        tippyUi.setX(x);
        tippyUi.setY(y);
        tippyUi.setMessageText(text);
        ((Activity)context).addContentView(tippyUi,new ViewGroup.LayoutParams(200,200));
    }

}
