package com.makarenko.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * ObjectConverter work only primitives as short and int
 */
public class ObjectConverter {

  private final StoreByte storeByte;
  private Object object;

  public ObjectConverter(StoreByte storeByte) {
    this.storeByte = storeByte;
  }

  public void serializeObject(Object obj) {
    this.object = obj;
    Field[] fields = this.object.getClass().getDeclaredFields();
    for (Field field : fields) {
      field.setAccessible(true);
      try {
        if (field.getType().getTypeName().equals("short")) {
          storeByte.writeShort(field.getShort(obj));
        }
        if (field.getType().getTypeName().equals("int")) {
          storeByte.writeInt(field.getInt(obj));
        }
      } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public Object deserializeObject() {
    Object read = null;
    Class clazz = null;
    try {
      clazz = Class.forName(this.object.getClass().getName());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    try {
      short readShort = 0;
      int readInt = 0;
      Class[] params = new Class[2];

      Field[] fields = this.object.getClass().getDeclaredFields();
      for (Field field : fields) {
        if (field.getType().getTypeName().equals("short")) {
          readShort = storeByte.readShort();
          params[0] = short.class;
        }
        if (field.getType().getTypeName().equals("int")) {
          readInt = storeByte.readInt();
          params[1] = int.class;
        }
      }

      read = clazz.getConstructor(params).newInstance(readShort, readInt);
    } catch (InstantiationException | IllegalAccessException
        | InvocationTargetException | NoSuchMethodException e) {
      e.printStackTrace();
    }

    return read;
  }

}
