package gallery.com.galleryandroid.model;

import android.os.Parcel;
import android.os.Parcelable;

public class GalleryData implements Parcelable {
    private String media = "";
    private String type = "";
    private boolean select;

    public GalleryData(String media, String type, boolean select) {
        this.media = media;
        this.type = type;
        this.select = select;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.media);
        dest.writeString(this.type);
        dest.writeByte(this.select ? (byte) 1 : (byte) 0);
    }

    protected GalleryData(Parcel in) {
        this.media = in.readString();
        this.type = in.readString();
        this.select = in.readByte() != 0;
    }

    public static final Parcelable.Creator<GalleryData> CREATOR = new Parcelable.Creator<GalleryData>() {
        @Override
        public GalleryData createFromParcel(Parcel source) {
            return new GalleryData(source);
        }

        @Override
        public GalleryData[] newArray(int size) {
            return new GalleryData[size];
        }
    };
}
