package kmitl.afinal.nakarin58070064.wallsplash.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(foreignKeys = @ForeignKey(entity = MyCollection.class,
        parentColumns = "id",
        childColumns = "current_collection"))
public class MyPhoto {

    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name = "current_collection")
    private int currentCollection;

    private String userId;
    private String userName;

    private String imageSmall;
    private String imageRegular;
    private String imageFull;

    private String linkHtml;
    private String linkDownload;

    public MyPhoto() {

    }

    public MyPhoto(Photo photo) {
        this.id = photo.getId();
        this.userId = photo.getUser().getId();
        this.userName = photo.getUser().getName();
        this.imageSmall = photo.getUrls().getSmall();
        this.imageRegular = photo.getUrls().getRegular();
        this.imageFull = photo.getUrls().getFull();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCurrentCollection() {
        return currentCollection;
    }

    public void setCurrentCollection(int currentCollection) {
        this.currentCollection = currentCollection;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImageSmall() {
        return imageSmall;
    }

    public void setImageSmall(String imageSmall) {
        this.imageSmall = imageSmall;
    }

    public String getImageRegular() {
        return imageRegular;
    }

    public void setImageRegular(String imageRegular) {
        this.imageRegular = imageRegular;
    }

    public String getImageFull() {
        return imageFull;
    }

    public void setImageFull(String imageFull) {
        this.imageFull = imageFull;
    }

    public String getLinkHtml() {
        return linkHtml;
    }

    public void setLinkHtml(String linkHtml) {
        this.linkHtml = linkHtml;
    }

    public String getLinkDownload() {
        return linkDownload;
    }

    public void setLinkDownload(String linkDownload) {
        this.linkDownload = linkDownload;
    }
}
