package entity;

import java.io.Serializable;

/**
 * Created by zth on 2018/1/4.
 */

public class CaiEntity implements Serializable{
    int id;
    String title;//标题
    String albums;//图片
    String imtro;//介绍
    String step;//步骤
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbums() {
        return albums;
    }

    public void setAlbums(String albums) {
        this.albums = albums;
    }

    public String getImtro() {
        return imtro;
    }

    public void setImtro(String imtro) {
        this.imtro = imtro;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "CaiEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", albums='" + albums + '\'' +
                ", imtro='" + imtro + '\'' +
                ", step='" + step + '\'' +
                '}';
    }
}
