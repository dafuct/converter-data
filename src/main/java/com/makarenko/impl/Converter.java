package com.makarenko.impl;

import java.util.Objects;

public class Converter {

  private final StoreByte storeByte;
  private final ObjectConverter objectConverter;

  public Converter(StoreByte storeByte, ObjectConverter objectConverter) {
    this.storeByte = storeByte;
    this.objectConverter = objectConverter;
  }

  private Object object;

  public void serialization(Object obj) {
    Objects.requireNonNull(obj);
    this.object = obj;
    if (obj.getClass().isAssignableFrom(Short.class)) {
      storeByte.writeShort((Short) this.object);
    }
    if (obj.getClass().isAssignableFrom(Integer.class)) {
      storeByte.writeInt((Integer) this.object);
    }
    if (obj.getClass().isAssignableFrom(Long.class)) {
      storeByte.writeLong((Long) this.object);
    }
    if (obj.getClass().isAssignableFrom(Float.class)) {
      storeByte.writeFloat((Float) this.object);
    }
    if (obj.getClass().isAssignableFrom(Double.class)) {
      storeByte.writeDouble((Double) this.object);
    }
    if (obj.getClass().isAssignableFrom(Character.class)) {
      storeByte.writeChar((Character) this.object);
    }
    if (obj.getClass().isAssignableFrom(String.class)) {
      storeByte.writeString((String) this.object);
    }

    objectConverter.serializeObject(obj);
  }

  public Object deserialization() {
    if (this.object.getClass().isAssignableFrom(Short.class)) {
      return storeByte.readShort();
    }
    if (this.object.getClass().isAssignableFrom(Integer.class)) {
      return storeByte.readInt();
    }
    if (this.object.getClass().isAssignableFrom(Long.class)) {
      return storeByte.readLong();
    }
    if (this.object.getClass().isAssignableFrom(Float.class)) {
      return storeByte.readFloat();
    }
    if (this.object.getClass().isAssignableFrom(Double.class)) {
      return storeByte.readDouble();
    }
    if (this.object.getClass().isAssignableFrom(Character.class)) {
      return storeByte.readChar();
    }
    if (this.object.getClass().isAssignableFrom(String.class)) {
      return storeByte.readString();
    }

    return objectConverter.deserializeObject();
  }
}
