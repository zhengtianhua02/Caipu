package com.example.caipu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import login.register.LoginActivity;
import login.register.LoginActivity;

public class MainActivity extends Activity {
    TextView skip;
    Handler handler;
    int time_count=2;
    Message msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        init();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==10 && time_count>0){
                    skip.setText("跳过 "+(time_count--));
                }else if(time_count==0){
                    Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    time_count--;
                    skip.setText("跳过 ");
                    handler.removeMessages(msg.what);
                }else {

                }
                handler.sendEmptyMessageDelayed(msg.what,1000);
            }
        };
       msg =new Message();
        msg.what=10;
        handler.sendEmptyMessageDelayed(msg.what,1000);
    }
    public void init(){
        skip=(TextView)findViewById(R.id.skip);
    }
    public void mySkip(View view){
        handler.removeMessages(msg.what);
        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();//调用此方法的时候，系统只是将最上面的Activity移出了栈，并没有及时的调用onDestory（）方法
    }
}
