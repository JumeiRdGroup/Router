package com.lzh.remote;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.anno.RouterRule;

@RouterRule("main")
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        Button btn = (Button) view;
        Router.create(btn.getText().toString()).open(this);
    }
}
