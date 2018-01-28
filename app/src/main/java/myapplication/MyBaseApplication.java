package myapplication;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by zth on 2018/1/5.
 */

public class MyBaseApplication extends Application {
    private static MyBaseApplication myBaseApplication=null;

    public MyBaseApplication() {
        if(myBaseApplication==null){
            myBaseApplication=this;
        }
    }
    public MyBaseApplication getInstance(){
        return myBaseApplication;
    }

    @Override
    public void onCreate() {
        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration =ImageLoaderConfiguration.createDefault(this);

        ImageLoader.getInstance().init(configuration);
    }
}
