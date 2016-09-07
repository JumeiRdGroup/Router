package com.lzh.nonview.router.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Set;

public class ParamsActivity extends AppCompatActivity {
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
}
