package me.ialistannen.isbnlookup.util;

import android.os.Parcel;
import android.os.Parcelable;
import me.ialistannen.isbnlookuplib.isbn.Isbn;
import me.ialistannen.isbnlookuplib.isbn.IsbnType;

/**
 * A Parcelable {@link Isbn} Wrapper
 */
public class ParcelableIsbn extends Isbn implements Parcelable {

  /**
   * @param digits The digits of the isbn
   * @param type The type of the isbn
   */
  private ParcelableIsbn(short[] digits, IsbnType type) {
    super(digits, type);
  }

  /**
   * @param isbn The {@link Isbn} to wrap
   * @return A {@link ParcelableIsbn} wrapping the ISBN.
   */
  public static ParcelableIsbn of(Isbn isbn) {
    return new ParcelableIsbn(isbn.getDigits(), isbn.getType());
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel out, int flags) {
    byte[] digitsBytes = getDigitsBytes();

    out.writeInt(digitsBytes.length);
    out.writeByteArray(digitsBytes);
    out.writeString(getType().name());
  }

  private byte[] getDigitsBytes() {
    short[] digits = getDigits();
    byte[] bytes = new byte[digits.length];

    for (int i = 0; i < digits.length; i++) {
      bytes[i] = (byte) digits[i];
    }

    return bytes;
  }

  public static Parcelable.Creator<ParcelableIsbn> CREATOR = new Creator<ParcelableIsbn>() {
    @Override
    public ParcelableIsbn createFromParcel(Parcel parcel) {
      byte[] digitsBytes = new byte[parcel.readInt()];
      parcel.readByteArray(digitsBytes);
      IsbnType isbnType = IsbnType.valueOf(parcel.readString());

      return new ParcelableIsbn(byteToShort(digitsBytes), isbnType);
    }

    private short[] byteToShort(byte[] data) {
      short[] shorts = new short[data.length];

      for (int i = 0; i < data.length; i++) {
        shorts[i] = data[i];
      }

      return shorts;
    }


    @Override
    public ParcelableIsbn[] newArray(int size) {
      return new ParcelableIsbn[size];
    }
  };
}
