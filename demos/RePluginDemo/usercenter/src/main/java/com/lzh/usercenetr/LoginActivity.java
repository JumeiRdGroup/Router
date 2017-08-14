package com.lzh.usercenetr;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.lzh.compiler.parceler.Parceler;
import com.lzh.compiler.parceler.annotation.Arg;
import com.lzh.nonview.router.anno.RouterRule;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@RouterRule("login")
public class LoginActivity extends Activity {

    @Arg
    String username;
    @Arg
    String password;

    @BindView(R.id.username)
    EditText name;
    @BindView(R.id.password)
    EditText word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Parceler.toEntity(this, getIntent());
        ButterKnife.bind(this);
        name.setText(username);
        word.setText(password);

    }

    @OnClick(R.id.login)
    void login() {
        finish();
    }
}
