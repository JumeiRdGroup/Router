package com.lzh.nonview.router.demo;

import android.os.Bundle;
import android.widget.TextView;

import com.lzh.nonview.router.Router;
import com.lzh.nonview.router.demo.base.BaseActivity;

import java.util.Set;

import butterknife.OnClick;

public class ParamsActivity extends BaseActivity {
    TextView params;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_params);
        params = (TextView) findViewById(R.id.params);
        if (getIntent() == null || getIntent().getExtras() == null) {
            return;
        }
        Bundle extras = getIntent().getExtras();
        StringBuilder buffer = new StringBuilder();
        Set<String> keySet = extras.keySet();
        for (String key : keySet) {
            Object value = extras.get(key);
            assert value != null;
            buffer.append(key).append("=").append(value).append("=").append(value.getClass().getSimpleName()).append("\r\n");
        }
        params.setText(buffer.toString());
    }

    @OnClick(R.id.toMain)
    void onToMainClick() {
        Router.create("haoge://haoge.com/main").open(this);
    }
}
