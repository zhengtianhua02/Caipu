package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.caipu.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.ArrayList;

import entity.CaiEntity;

/**
 * Created by zth on 2018/1/5.
 */

public class MyCaipuListAdapter extends BaseAdapter{

    // 创建ImageLoader对象
    public static  ImageLoader imageLoader = ImageLoader.getInstance();

    LayoutInflater inflater;
    Context con;
    ArrayList<CaiEntity> caipu_array=new ArrayList<CaiEntity>();
    public MyCaipuListAdapter(Context con,ArrayList<CaiEntity> caipu_array) {
        this.con=con;
        this.caipu_array=caipu_array;
        inflater=LayoutInflater.from(con);
        initLoaderImager();
    }
    public void initLoaderImager(){
        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration configuration =ImageLoaderConfiguration.createDefault(con);

        ImageLoader.getInstance().init(configuration);
    }
    @Override
    public int getCount() {
        return caipu_array.size();
    }

    @Override
    public Object getItem(int i) {
        return caipu_array.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view==null){
            viewHolder=new ViewHolder();
            view=inflater.inflate(R.layout.listview_layout,null);
            viewHolder.title=view.findViewById(R.id.tv_title);
            viewHolder.imtro=view.findViewById(R.id.tv_imtro);
            viewHolder.iv_albums=view.findViewById(R.id.iv_albums);
            view.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)view.getTag();
        }
        CaiEntity cai=caipu_array.get(i);
        viewHolder.title.setText(cai.getTitle());
        viewHolder.imtro.setText(cai.getImtro());
        //
        //viewHolder.iv_albums.setImageBitmap();

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher_background)// 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_launcher_background)// 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_launcher_background)// 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)// 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                .build();// 创建DisplayImageOptions对象

        // 使用ImageLoader加载图片
        imageLoader.displayImage(cai.getAlbums(),viewHolder.iv_albums,options);

        System.out.println("viewHolder.title.setText的内容"+ viewHolder.title.getText().toString());
        return view;
    }



    public class ViewHolder{
        TextView title;
        TextView imtro;
        ImageView iv_albums;
    }
}
