package kmitl.afinal.nakarin58070064.wallsplash.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity
public class MyCollection implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String desc;

    public MyCollection() {
    }

    protected MyCollection(Parcel in) {
        id = in.readInt();
        title = in.readString();
        desc = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(desc);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MyCollection> CREATOR = new Creator<MyCollection>() {
        @Override
        public MyCollection createFromParcel(Parcel in) {
            return new MyCollection(in);
        }

        @Override
        public MyCollection[] newArray(int size) {
            return new MyCollection[size];
        }
    };

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
