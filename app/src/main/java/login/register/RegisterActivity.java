package login.register;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.example.caipu.R;

import sqlopration.MysqlOpenHandler;

public class RegisterActivity extends Activity {
    EditText telNum;//手机号码
    EditText  password;//密码
    EditText  repassword;//重复密码
    EditText  register_yanZM;//重复密码
//    EventHandler eventHandler;//事件接收器

    MysqlOpenHandler mysqlOpenHandler=new MysqlOpenHandler(this,LoginActivity.DT_NAME,null,LoginActivity.VERSION);
    SQLiteDatabase sqliteDt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        init();
        //initEvent();
    }
    public void init(){
        telNum=(EditText)findViewById(R.id.register_telNum);
        password=(EditText)findViewById(R.id.register_password);
//        repassword=(EditText)findViewById(R.id.register_repassword);
//        register_yanZM=(EditText)findViewById(R.id.register_yanZM);
    }
    /*
    public void initEvent(){
        eventHandler=new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object o) {
                if(result==SMSSDK.RESULT_COMPLETE){//回调完成
                        if(event==SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){//提交验证码成功
                                //把账号、密码存入到数据库中
                                insert_account();
                                //finish();
                            select_account();//账号查询
                        }else {
                            //提交验证码失败
                            System.out.println("提交验证码失败");
                        }
                }
            }
        };
    }
    */
    //获取验证码(未使用)
    public void sendYanZ(View view){
      //  SMSSDK.getVerificationCode("+86",telNum.getText().toString());
    }
    //注册
    public void myRegister(View view){
      //  SMSSDK.submitVerificationCode("+86",telNum.getText().toString(),password.getText().toString());
        if(!(telNum.getText().toString().equals("")) &&  !(password.getText().toString().equals(""))){
            insert_account();
        }else{
            Toast.makeText(this,"账号或密码不能为空",Toast.LENGTH_SHORT).show();
        }



    }
    //把账号、密码存入到数据库中
    public void insert_account(){
        sqliteDt=mysqlOpenHandler.getWritableDatabase();
        boolean value=yanZheng_account();
        if(value){
            sqliteDt.execSQL("insert into login_table(telNum,password) values(?,?)",new Object[]{telNum.getText().toString(), password.getText().toString()});
            select_account();
            Intent intent=new Intent(this,LoginActivity.class);
            startActivity(intent);
        }else {
            //Toast.makeText(this,"账号已注册",Toast.LENGTH_LONG).show();
        }
    }
    public void select_account(){
        sqliteDt=mysqlOpenHandler.getReadableDatabase();
        //Cursor cursor=sqliteDt.rawQuery("select * from login_table where telNum="+telNum.getText().toString(),null);
        Cursor cursor=sqliteDt.rawQuery("select * from login_table ",null);
        String account=null;
        String pass=null;
        while(cursor.moveToNext()){
            account=cursor.getString(cursor.getColumnIndex("telNum"));
            pass=cursor.getString(cursor.getColumnIndex("password"));
            if(account.equals(telNum.getText().toString()) && pass.equals(password.getText().toString())){
                Toast.makeText(this,"账号注册成功",Toast.LENGTH_LONG).show();
            }
        }
        System.out.println("查询到的账号是："+account+pass);
    }

    //验证账号密码是否已注册
    public boolean yanZheng_account(){
        sqliteDt=mysqlOpenHandler.getReadableDatabase();
        //Cursor cursor=sqliteDt.rawQuery("select * from login_table where telNum="+telNum.getText().toString(),null);
        Cursor cursor=sqliteDt.rawQuery("select * from login_table ",null);
        while(cursor.moveToNext()){
            if(telNum.getText().toString().equals(cursor.getString(cursor.getColumnIndex("telNum")))){
                Toast.makeText(this,"账号已注册",Toast.LENGTH_LONG).show();
                return false;
            }else {
                return true;
            }
        }
        return true;

    }
}
