package kmitl.afinal.nakarin58070064.wallsplash.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("portfolio_url")
    @Expose
    private String portfolioUrl;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("total_likes")
    @Expose
    private Integer totalLikes;
    @SerializedName("total_photos")
    @Expose
    private Integer totalPhotos;
    @SerializedName("total_collections")
    @Expose
    private Integer totalCollections;
    @SerializedName("profile_image")
    @Expose
    private ProfileImage profileImage;
    @SerializedName("links")
    @Expose
    private Links links;

    protected User(Parcel in) {
        id = in.readString();
        username = in.readString();
        name = in.readString();
        portfolioUrl = in.readString();
        bio = in.readString();
        location = in.readString();
        if (in.readByte() == 0) {
            totalLikes = null;
        } else {
            totalLikes = in.readInt();
        }
        if (in.readByte() == 0) {
            totalPhotos = null;
        } else {
            totalPhotos = in.readInt();
        }
        if (in.readByte() == 0) {
            totalCollections = null;
        } else {
            totalCollections = in.readInt();
        }
        profileImage = in.readParcelable(ProfileImage.class.getClassLoader());
        links = in.readParcelable(Links.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(username);
        dest.writeString(name);
        dest.writeString(portfolioUrl);
        dest.writeString(bio);
        dest.writeString(location);
        if (totalLikes == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(totalLikes);
        }
        if (totalPhotos == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(totalPhotos);
        }
        if (totalCollections == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(totalCollections);
        }
        dest.writeParcelable(profileImage, flags);
        dest.writeParcelable(links, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortfolioUrl() {
        return portfolioUrl;
    }

    public void setPortfolioUrl(String portfolioUrl) {
        this.portfolioUrl = portfolioUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(Integer totalLikes) {
        this.totalLikes = totalLikes;
    }

    public Integer getTotalPhotos() {
        return totalPhotos;
    }

    public void setTotalPhotos(Integer totalPhotos) {
        this.totalPhotos = totalPhotos;
    }

    public Integer getTotalCollections() {
        return totalCollections;
    }

    public void setTotalCollections(Integer totalCollections) {
        this.totalCollections = totalCollections;
    }

    public ProfileImage getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ProfileImage profileImage) {
        this.profileImage = profileImage;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }
}
