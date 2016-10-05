package io.github.aaronfields.gridimagegallery;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aaronfields on 10/5/16.
 */

public class Photos {

    public int page;
    public int pages;
    public int perpage;
    public long total;
    @SerializedName("photo")
    @Expose public List<Photo> photo;
}

//    public Photos (int page, int pages, int perpage, long total, ArrayList<Photo> photo) {
//        this.page = page;
//        this.pages = pages;
//        this.perpage = perpage;
//        this.total = total;
//        this.photo = photo;
//    }

//    public int getPage() {
//        return page;
//    }
//
//    public void setPage(int page) {
//        this.page = page;
//    }
//
//    public int getPages() {
//        return pages;
//    }
//
//    public void setPages(int pages) {
//        this.pages = pages;
//    }
//
//    public int getPerpage() {
//        return perpage;
//    }
//
//    public void setPerpage(int perpage) {
//        this.perpage = perpage;
//    }
//
//    public long getTotal() {
//        return total;
//    }
//
//    public void setTotal(long total) {
//        this.total = total;
//    }
//
//    public ArrayList<Photo> getPhoto() {
//        return photo;
//    }
//
//    public void setPhoto(ArrayList<Photo> photo) {
//        this.photo = photo;
//    }
//}