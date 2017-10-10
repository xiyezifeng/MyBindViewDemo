package com.yekong.bindview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.ViewBinder;
import com.yekong.myviewbinder.InjectView;

public class MainActivity_1 extends AppCompatActivity {
    @ViewBinder(R.id.text)
    TextView text;
    @ViewBinder(R.id.text_1)
    TextView text_1;
    @ViewBinder(R.id.text_2)
    TextView text_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_1);
        InjectView.bind(this);
        text.setText("你好啊");
        text_1.setText("这是中国");
    }

}
