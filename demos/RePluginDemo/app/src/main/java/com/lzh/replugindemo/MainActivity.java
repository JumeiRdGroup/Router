package com.lzh.replugindemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.anno.RouterRule;

// 因为指定了baseUrl。 所以这里会使用baseUrl做组合。
@RouterRule("main")
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        Button btn = (Button) v;
        Router.create(btn.getText().toString()).open(this);
    }
}
