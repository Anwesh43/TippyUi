package com.anwesome.ui.tippyuidemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.anwesome.ui.tippyui.TippyUiUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TippyUiUtil.createTippyUi(this,"Hello world i am here lets chat about code. I want to learn more and more");
    }

}
