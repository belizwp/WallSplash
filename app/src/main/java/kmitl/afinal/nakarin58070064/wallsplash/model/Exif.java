package kmitl.afinal.nakarin58070064.wallsplash.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Exif implements Parcelable {

    @SerializedName("make")
    @Expose
    private String make;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("exposure_time")
    @Expose
    private String exposureTime;
    @SerializedName("aperture")
    @Expose
    private String aperture;
    @SerializedName("focal_length")
    @Expose
    private String focalLength;
    @SerializedName("iso")
    @Expose
    private Integer iso;

    protected Exif(Parcel in) {
        make = in.readString();
        model = in.readString();
        exposureTime = in.readString();
        aperture = in.readString();
        focalLength = in.readString();
        if (in.readByte() == 0) {
            iso = null;
        } else {
            iso = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(make);
        dest.writeString(model);
        dest.writeString(exposureTime);
        dest.writeString(aperture);
        dest.writeString(focalLength);
        if (iso == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(iso);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Exif> CREATOR = new Creator<Exif>() {
        @Override
        public Exif createFromParcel(Parcel in) {
            return new Exif(in);
        }

        @Override
        public Exif[] newArray(int size) {
            return new Exif[size];
        }
    };

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getExposureTime() {
        return exposureTime;
    }

    public void setExposureTime(String exposureTime) {
        this.exposureTime = exposureTime;
    }

    public String getAperture() {
        return aperture;
    }

    public void setAperture(String aperture) {
        this.aperture = aperture;
    }

    public String getFocalLength() {
        return focalLength;
    }

    public void setFocalLength(String focalLength) {
        this.focalLength = focalLength;
    }

    public Integer getIso() {
        return iso;
    }

    public void setIso(Integer iso) {
        this.iso = iso;
    }
}
