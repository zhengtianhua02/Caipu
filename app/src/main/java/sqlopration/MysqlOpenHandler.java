package sqlopration;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zth on 2018/1/2.
 */

public class MysqlOpenHandler extends SQLiteOpenHelper{
    public MysqlOpenHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        //name :数据库名称 factory：游标，version 版本
        super(context, name, factory, version);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建数据表
        sqLiteDatabase.execSQL("create table login_table (login_id INTEGER  AUTO_INCREMENT,telNum varchar(11) not null,password varchar(20) not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //更新
    }
}
