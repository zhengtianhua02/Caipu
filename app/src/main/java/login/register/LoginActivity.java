package login.register;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caipu.R;
import com.example.caipu.ShouyeActivity;

import sqlopration.MysqlOpenHandler;

public class LoginActivity extends Activity {
    EditText telNum;
    EditText password;
    TextView skip;
    static String DT_NAME="myCaipu_sqlite";
    static int VERSION=1;

    static MysqlOpenHandler mysqlOpen;
    //SQLiteDatabase sqliteDt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        mysqlOpen=new MysqlOpenHandler(this,DT_NAME,null,VERSION);
        init();

    }
    public void init(){
        telNum=(EditText)findViewById(R.id.telNum);
        password=(EditText)findViewById(R.id.password);
        skip=(TextView)findViewById(R.id.skip);
    }
    public void myLogin(View view){

        if(telNum.getText().toString().equals("") && password.getText().toString().equals("") ){
            Toast.makeText(this,"账号密码不能为空",Toast.LENGTH_SHORT).show();
        }else {
            boolean denglu=login_query(telNum.getText().toString(),password.getText().toString());
            if(denglu) {

                Toast.makeText(this, "成功登陆", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this,"账号或密码错误",Toast.LENGTH_LONG).show();
            }
        }
    }
    public void myRegister(View view){
       Intent intent= new Intent(LoginActivity.this,RegisterActivity.class);
       this.startActivity(intent);
    }

    //登陆验证
    public boolean login_query(String telN,String pass){
        SQLiteDatabase sqliteDt=mysqlOpen.getReadableDatabase();
        Cursor cursor=sqliteDt.rawQuery("select * from login_table where telNum="+telN,null);
        while(cursor.moveToNext()){
            if(cursor.getString(cursor.getColumnIndex("telNum"))==null){
                return false;
            }else if(cursor.getString(cursor.getColumnIndex("telNum")).equals(telNum.getText().toString())
                    && cursor.getString(cursor.getColumnIndex("password")).equals(password.getText().toString())){
                Intent intent=new Intent(this, ShouyeActivity.class);
                startActivity(intent);
                this.finish();
                return true;
            }
        }
        return false;
    }

}
