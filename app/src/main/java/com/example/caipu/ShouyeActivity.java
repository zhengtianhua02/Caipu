package com.example.caipu;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.ArrayList;

import adapter.MyCaipuListAdapter;
import adapter.MyPagerAdapter;
import entity.CaiEntity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ShouyeActivity extends Activity{
    String URL="http://apis.juhe.cn/cook/query?key=654ced8dc65dd2d1bbb8bb7ad8a68bbe&menu=肉&pn=3";
   // http://apis.juhe.cn/cook/query?key=654ced8dc65dd2d1bbb8bb7ad8a68bbe&menu=肉&rn=10&pn=3
    //String jiansuo="习近平";
    ViewPager viewpager;
    int[] myDrable={R.drawable.meishi2,R.drawable.meishi3,R.drawable.meishi4};
    ArrayList<ImageView> images;
    LinearLayout dot_container;
    int prviousPostion=0;
    int currention;
    Handler handler;

    ArrayList<CaiEntity> caipu_array=new ArrayList<CaiEntity>();
    ListView listview;
    MyCaipuListAdapter myCaipuListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shouye);
        init();
        loadData(); //加载菜谱数据

        Message msg=new Message();
        msg.what=10;
        handler.sendMessageDelayed(msg,1000);

        listview=(ListView)findViewById(R.id.listView);
        myCaipuListAdapter=new MyCaipuListAdapter(this,caipu_array);
        listview.setAdapter(myCaipuListAdapter);

        //listview的点击事件
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(ShouyeActivity.this,CaipuDetailActivity.class);
                intent.putExtra("caipu_datail",caipu_array.get(i));
                startActivity(intent);
            }
        });
        myCaipuListAdapter.notifyDataSetChanged();
    }
    public void init(){
        viewpager=(ViewPager)findViewById(R.id.viewPager);
        dot_container=(LinearLayout)findViewById(R.id.dots);
        images=new ArrayList<ImageView>();
        //创建图片
        for(int i=0;i<myDrable.length;i++){
            ImageView imageview=new ImageView(this);
            //imageview.setImageResource(myDrable[i]);
            imageview.setBackgroundResource(myDrable[i]);
            images.add(imageview);
        }
        //创建点点
        for(int i=0;i<myDrable.length;i++){
            ImageView dot=new ImageView(this);
            dot.setImageResource(R.drawable.dot_selector);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(15,15);
            params.leftMargin=10;
            dot.setEnabled(false);
            dot_container.addView(dot,params);
        }
        dot_container.getChildAt(0).setEnabled(true);
        MyPagerAdapter mypagerAdapter=new MyPagerAdapter(images);
        viewpager.setAdapter(mypagerAdapter);

        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                currention=position%dot_container.getChildCount();
                dot_container.getChildAt(currention).setEnabled(true);
                dot_container.getChildAt(prviousPostion).setEnabled(false);
                prviousPostion=currention;
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==10){
                    viewpager.setCurrentItem(viewpager.getCurrentItem()+1);
                }
              //  handler.sendMessageDelayed(msg,1000);
                handler.sendEmptyMessageDelayed(10,1000);
            }
        };


    }
    public void loadData(){
        new AsyncTask<Void,Void,String>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {
                String jsonData;
                OkHttpClient   okhttpClient=new OkHttpClient();
                Request.Builder requestBuilder=new Request.Builder();
                Request request=requestBuilder.url(URL).build();
                Response response=null;
                String json=null;
                try {
                    response=okhttpClient.newCall(request).execute();
                    json=response.body().string();
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println("okhttp请求的数据为："+json);
                return json;
            }

            @Override
            protected void onPostExecute(String json) {
                System.out.println("okhttp请求的数据为："+json);
                Gson gson=new Gson();

                JsonData  allCaipu=gson.fromJson(json,JsonData.class);
                CaiEntity caiEntity;

                for(JsonData.ResultBean.DataBean  cai: allCaipu.getResult().getData()){
                    caiEntity=new CaiEntity();
                    caiEntity.setId(Integer.valueOf(cai.getId()));
                    caiEntity.setTitle(cai.getTitle());
                    caiEntity.setImtro(cai.getImtro().toString());
                    caiEntity.setAlbums(cai.getAlbums().get(0));


                    //步骤
                    String step="";
                    for(int i=0;i<cai.getSteps().size();i++){
                        step=step+cai.getSteps().get(i).getStep();
                    }
                    caiEntity.setStep(step);

                    caipu_array.add(caiEntity);

                }
                myCaipuListAdapter.notifyDataSetChanged();


                System.out.println("caipu_array的所有菜谱数据为："+caipu_array);
            }
        }.execute();
    }

    @Override
    protected void onDestroy() {
        // 回收该页面缓存在内存中的图片
        MyCaipuListAdapter.imageLoader.clearDiskCache();

        super.onDestroy();
    }
}
