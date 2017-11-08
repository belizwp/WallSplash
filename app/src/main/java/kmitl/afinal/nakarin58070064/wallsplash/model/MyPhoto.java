package kmitl.afinal.nakarin58070064.wallsplash.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(foreignKeys = @ForeignKey(entity = MyCollection.class,
        parentColumns = "id",
        childColumns = "current_collection"))
public class MyPhoto implements Parcelable {

    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name = "current_collection")
    private int currentCollection;

    private String userId;
    private String userName;
    private String userImage;

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
        this.userImage = photo.getUser().getProfileImage().getMedium();
        this.imageSmall = photo.getUrls().getSmall();
        this.imageRegular = photo.getUrls().getRegular();
        this.imageFull = photo.getUrls().getFull();
    }

    protected MyPhoto(Parcel in) {
        id = in.readString();
        currentCollection = in.readInt();
        userId = in.readString();
        userName = in.readString();
        userImage = in.readString();
        imageSmall = in.readString();
        imageRegular = in.readString();
        imageFull = in.readString();
        linkHtml = in.readString();
        linkDownload = in.readString();
    }

    public static final Creator<MyPhoto> CREATOR = new Creator<MyPhoto>() {
        @Override
        public MyPhoto createFromParcel(Parcel in) {
            return new MyPhoto(in);
        }

        @Override
        public MyPhoto[] newArray(int size) {
            return new MyPhoto[size];
        }
    };

    public Photo getPhoto() {
        Photo photo = new Photo();
        photo.setId(id);
        photo.getUser().setId(userId);
        photo.getUser().setName(userName);
        photo.getUser().getProfileImage().setMedium(userImage);
        photo.getUrls().setSmall(imageSmall);
        photo.getUrls().setRegular(imageRegular);
        photo.getUrls().setFull(imageFull);
        photo.getLinks().setHtml(linkHtml);
        photo.getLinks().setDownload(linkDownload);

        return photo;
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

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(currentCollection);
        dest.writeString(userId);
        dest.writeString(userName);
        dest.writeString(userImage);
        dest.writeString(imageSmall);
        dest.writeString(imageRegular);
        dest.writeString(imageFull);
        dest.writeString(linkHtml);
        dest.writeString(linkDownload);
    }
}
