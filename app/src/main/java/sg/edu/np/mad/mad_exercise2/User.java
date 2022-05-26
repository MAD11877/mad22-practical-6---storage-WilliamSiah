package sg.edu.np.mad.mad_exercise2;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String name;
    private String description;
    private int ID;
    private boolean followed;

    public User(String name, String description, int ID, boolean followed) {
        this.name = name;
        this.description = description;
        this.ID = ID;
        this.followed = followed;
    }

    protected User(Parcel in) {
        name = in.readString();
        description = in.readString();
        ID = in.readInt();
        followed = in.readByte() != 0;
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

    public String getNameOfUser() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getID() {
        return ID;
    }

    public boolean isFollowed() {
        return followed;
    }

    public void setFollowed(boolean followed) {
        this.followed = followed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeInt(ID);
        parcel.writeByte((byte) (followed ? 1 : 0));
    }
}
