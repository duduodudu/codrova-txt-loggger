package cn.com.intasect.logapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SimpleDateFormat simpleFormatter = new SimpleDateFormat("HH:mm:ss");
        String now = simpleFormatter.format(new Date(System.currentTimeMillis()));
        IscTxtLoggerHelper.getInstance().init(getApplicationContext(),this);
        IscTxtLoggerHelper.getInstance().logInfo("测试信息" + now);
        IscTxtLoggerHelper.getInstance().logNetwork("测试信息" + now);
    }


}
