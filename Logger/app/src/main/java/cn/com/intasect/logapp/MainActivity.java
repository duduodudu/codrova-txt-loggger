package cn.com.intasect.logapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化
        IscTxtLoggerHelper.getInstance().init(getApplicationContext(), this);
        writeLog(false);

        findViewById(R.id.button_log).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeLog(true);
            }
        });
    }

    private void writeLog(boolean isClickByButton) {
        SimpleDateFormat simpleFormatter = new SimpleDateFormat("HH:mm:ss");
        String now = simpleFormatter.format(new Date(System.currentTimeMillis()));
        if (isClickByButton) {
            IscTxtLoggerHelper.getInstance().logInfo("点击按钮写入日志" + now);
            IscTxtLoggerHelper.getInstance().logNetwork("点击按钮写入日志" + now);
        } else {
            IscTxtLoggerHelper.getInstance().logInfo("程序onCreate写入日志" + now);
            IscTxtLoggerHelper.getInstance().logNetwork("程序onCreate写入日志" + now);
        }

    }


}
