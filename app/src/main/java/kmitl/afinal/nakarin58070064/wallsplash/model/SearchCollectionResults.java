package kmitl.afinal.nakarin58070064.wallsplash.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchCollectionResults implements Parcelable {

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("results")
    @Expose
    private List<Collection> results = null;

    protected SearchCollectionResults(Parcel in) {
        if (in.readByte() == 0) {
            total = null;
        } else {
            total = in.readInt();
        }
        if (in.readByte() == 0) {
            totalPages = null;
        } else {
            totalPages = in.readInt();
        }
        results = in.createTypedArrayList(Collection.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (total == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(total);
        }
        if (totalPages == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(totalPages);
        }
        dest.writeTypedList(results);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SearchCollectionResults> CREATOR = new Creator<SearchCollectionResults>() {
        @Override
        public SearchCollectionResults createFromParcel(Parcel in) {
            return new SearchCollectionResults(in);
        }

        @Override
        public SearchCollectionResults[] newArray(int size) {
            return new SearchCollectionResults[size];
        }
    };

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<Collection> getResults() {
        return results;
    }

    public void setResults(List<Collection> results) {
        this.results = results;
    }
}
