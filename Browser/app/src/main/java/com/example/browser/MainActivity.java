package com.example.browser;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        final Intent intent = new Intent(MainActivity.this, Web.class);
        final EditText website = findViewById(R.id.website);
        Button go = findViewById(R.id.go);
        Button baidu = findViewById(R.id.baidu);
        Button google = findViewById(R.id.google);
        Button bing = findViewById(R.id.bing);
        Button tencent = findViewById(R.id.tencent);
        Button sina = findViewById(R.id.sina);
        Button yahoo = findViewById(R.id.yahoo);
        Button qihu = findViewById(R.id.qihu);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(website.getText())) {
                    intent.putExtra("website", website.getText().toString());
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "you must enter a website", Toast.LENGTH_SHORT).show();
                }
            }
        });
        baidu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("website", "www.baidu.com");
                startActivity(intent);
            }
        });
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("website", "www.google.com");
                startActivity(intent);
            }
        });
        bing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("website", "www.bing.com");
                startActivity(intent);
            }
        });
        tencent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("website", "www.qq.com");
                startActivity(intent);
            }
        });
        sina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("website", "www.sina.com.cn");
                startActivity(intent);
            }
        });
        yahoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("website", "www.yahoo.com");
                startActivity(intent);
            }
        });
        qihu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("website", "www.so.com");
                startActivity(intent);
            }
        });
    }
}
