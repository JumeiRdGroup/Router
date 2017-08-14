package com.lzh.app.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.anno.RouterRule;

@RouterRule("main")
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        TextView txt = (TextView) v;
        Router.create(txt.getText().toString()).open(this);
    }
}
