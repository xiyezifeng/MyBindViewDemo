package com.yekong.bindview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ViewBinder;
import com.example.ViewClick;
import com.yekong.myviewbinder.InjectView;

public class MainActivity extends AppCompatActivity {
    @ViewBinder(R.id.text)
    TextView text;
    @ViewBinder(R.id.text_1)
    TextView text_1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InjectView.bind(this);
        text.setText("你好啊");
        text_1.setText("这是中国");
    }

    @ViewClick({R.id.text,R.id.text_1})
    public void onclick(View v){
        switch (v.getId()) {
            case R.id.text:
                Toast.makeText(this, "text", Toast.LENGTH_SHORT).show();
                break;
            case R.id.text_1:
                Toast.makeText(this, "text_1", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        InjectView.unBind(this);
    }
}
