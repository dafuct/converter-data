package com.makarenko.entity;

public class User {

  private short value;
  private int anInt;

  public User(short value, int anInt) {
    this.value = value;
    this.anInt = anInt;
  }

  public short getValue() {
    return value;
  }

  public void setValue(short value) {
    this.value = value;
  }

  public int getAnInt() {
    return anInt;
  }

  public void setAnInt(int anInt) {
    this.anInt = anInt;
  }

  @Override
  public String toString() {
    return "User{" +
        "value=" + value +
        ", anInt=" + anInt +
        '}';
  }
}
