package com.example.caipu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import entity.CaiEntity;

public class CaipuDetailActivity extends Activity {
    CaiEntity caiEntity;
    // 创建ImageLoader对象
    public static  ImageLoader imageLoader = ImageLoader.getInstance();

    ImageView detail_albums;
    TextView detail_title;
    TextView detail_imtro;
    TextView step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.caipu_detail);
        caiEntity=(CaiEntity)this.getIntent().getSerializableExtra("caipu_datail");


        System.out.println("listview传递过来的值为："+caiEntity);
        init();
        initLoaderImager();
        initData();
    }
    public void init(){
        detail_albums=(ImageView)findViewById(R.id.detail_albums);
        detail_title=(TextView) findViewById(R.id.detail_title);
        detail_imtro=(TextView) findViewById(R.id.detail_imtro);
        step=(TextView) findViewById(R.id.step);
    }
    public void initLoaderImager(){
        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration =ImageLoaderConfiguration.createDefault(this);

        ImageLoader.getInstance().init(configuration);
    }
    public void initData(){
        detail_title.setText(caiEntity.getTitle().toString());
        detail_imtro.setText(caiEntity.getImtro().toString());

        DisplayImageOptions options=new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_launcher_background)
                .showImageForEmptyUri(R.drawable.ic_launcher_background)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_launcher_background)// 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)// 设置下载的图片是否缓存在SD卡中
                .build();// 创建DisplayImageOptions对象
        imageLoader.displayImage(caiEntity.getAlbums().toString(),detail_albums,options);

        step.setText(caiEntity.getStep().toString());
    }
}
