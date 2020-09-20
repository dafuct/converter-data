package com.makarenko.impl;

public class StoreByte {

  private byte[] array;
  private int writePosition = 0;
  private int readIndex = 0;

  public StoreByte() {
    this.array = new byte[0];
  }

  private void ensureCapacity(int len) {
    if (len > array.length - writePosition) {
      int newLen = len + writePosition > (array.length << 1) ? len + writePosition + array.length
          : (array.length << 1);
      byte[] tmp = new byte[newLen];
      System.arraycopy(array, 0, tmp, 0, array.length);
      array = tmp;
    }
  }

  public byte[] toArray() {
    byte[] result = new byte[writePosition];
    System.arraycopy(array, 0, result, 0, writePosition);
    return result;
  }

  public void writeInt(int i) {
    ensureCapacity(4);
    array[writePosition] = (byte) (i >> 24);
    array[writePosition + 1] = (byte) (i >> 16);
    array[writePosition + 2] = (byte) (i >> 8);
    array[writePosition + 3] = (byte) i;
    writePosition += 4;
  }

  public int readInt() {
    int i = (array[readIndex] & 255) << 24;
    i |= (array[readIndex + 1] & 255) << 16;
    i |= (array[readIndex + 2] & 255) << 8;
    i |= array[readIndex + 3] & 255;
    this.readIndex += 4;
    return i;
  }

  public void writeShort(short s) {
    ensureCapacity(2);
    array[writePosition] = (byte) (s >> 8);
    array[writePosition + 1] = (byte) s;
    writePosition += 2;
  }

  public short readShort() {
    short s = (short) ((array[readIndex] & 255) << 8);
    s = (short) (s | array[readIndex + 1] & 255);
    this.readIndex += 2;
    return s;
  }

  public void writeLong(long l) {
    ensureCapacity(8);
    array[writePosition] = (byte) ((int) (l >> 56));
    array[writePosition + 1] = (byte) ((int) (l >> 48));
    array[writePosition + 2] = (byte) ((int) (l >> 40));
    array[writePosition + 3] = (byte) ((int) (l >> 32));
    array[writePosition + 4] = (byte) ((int) (l >> 24));
    array[writePosition + 5] = (byte) ((int) (l >> 16));
    array[writePosition + 6] = (byte) ((int) (l >> 8));
    array[writePosition + 7] = (byte) ((int) l);
    writePosition += 8;
  }

  public long readLong() {
    long l = (long) array[readIndex] << 56 | ((long) array[readIndex + 1] & 255L) << 48
        | ((long) array[readIndex + 2] & 255L) << 40 | ((long) array[readIndex + 3] & 255L) << 32
        | ((long) array[readIndex + 4] & 255L) << 24 | ((long) array[readIndex + 5] & 255L) << 16
        | ((long) array[readIndex + 6] & 255L) << 8 | (long) array[readIndex + 7] & 255L;
    this.readIndex += 8;
    return l;
  }

  public void writeFloat(float f) {
    writeInt(Float.floatToRawIntBits(f));
  }

  public float readFloat() {
    int i = readInt();
    return Float.intBitsToFloat(i);
  }

  public void writeDouble(double d) {
    writeLong(Double.doubleToRawLongBits(d));
  }

  public double readDouble() {
    long l = readLong();
    return Double.longBitsToDouble(l);
  }

  public void writeChar(char c) {
    if (c <= 251) {
      ensureCapacity(1);
      array[writePosition] = (byte) c;
      ++writePosition;
    } else if (c <= 255) {
      ensureCapacity(2);
      array[writePosition] = -4;
      array[writePosition + 1] = (byte) c;
      writePosition += 2;
    } else {
      ensureCapacity(3);
      array[writePosition] = -3;
      array[writePosition + 1] = (byte) (c >>> 8);
      array[writePosition + 2] = (byte) c;
      writePosition += 3;
    }
  }

  public char readChar() {
    int length = array[readIndex++] & 255;
    if (length <= 251) {
      return (char) length;
    } else if (length == 252) {
      length = array[readIndex++] & 255;
      return (char) length;
    } else if (length == 253) {
      length = (array[readIndex++] & 255) << 8;
      length |= array[readIndex++] & 255;
      return (char) length;
    } else {
      throw new IllegalArgumentException();
    }
  }

  public void writeString(String value) {
    if (value == null) {
      writeInt(-1);
    } else {
      int length = value.length();
      writeInt(length);
      ensureCapacity(3 * length);
      for (int i = 0; i < length; ++i) {
        writeChar(value.charAt(i));
      }
    }
  }

  public String readString() {
    int length = readInt();
    if (length == -1) {
      return null;
    } else {
      char[] src = new char[length];
      for (int i = 0; i < length; ++i) {
        src[i] = this.readChar();
      }
      return new String(src);
    }
  }
}
