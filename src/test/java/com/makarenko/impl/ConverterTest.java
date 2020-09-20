package com.makarenko.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.makarenko.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConverterTest {

  @Mock
  private StoreByte storeByte;
  @Mock
  private ObjectConverter objectConverter;

  private Converter converter;

  @BeforeEach
  void setUp() {
    converter = new Converter(storeByte, objectConverter);
  }

  @Test
  void testShort() {
    short value = 4550;
    converter.serialization(value);
    verify(storeByte).writeShort(value);

    when(converter.deserialization()).thenReturn(value);
    short deserializationValue = (short) converter.deserialization();
    verify(storeByte).readShort();

    assertEquals(value, deserializationValue);
  }

  @Test
  void testInteger() {
    int value = 456789;
    converter.serialization(value);
    verify(storeByte).writeInt(value);

    when(converter.deserialization()).thenReturn(value);
    int deserializationValue = (int) converter.deserialization();
    verify(storeByte).readInt();

    assertEquals(value, deserializationValue);
  }

  @Test
  void testLong() {
    long value = 455056565L;
    converter.serialization(value);
    verify(storeByte).writeLong(value);

    when(converter.deserialization()).thenReturn(value);
    long deserializationValue = (long) converter.deserialization();
    verify(storeByte).readLong();

    assertEquals(value, deserializationValue);
  }

  @Test
  void testFloat() {
    float value = 4550565.566F;
    converter.serialization(value);
    verify(storeByte).writeFloat(value);

    when(converter.deserialization()).thenReturn(value);
    float deserializationValue = (float) converter.deserialization();
    verify(storeByte).readFloat();

    assertEquals(value, deserializationValue);
  }

  @Test
  void testDouble() {
    double value = 455056565.7890;
    converter.serialization(value);
    verify(storeByte).writeDouble(value);

    when(converter.deserialization()).thenReturn(value);
    double deserializationValue = (double) converter.deserialization();
    verify(storeByte).readDouble();

    assertEquals(value, deserializationValue);
  }

  @Test
  void testChar() {
    char value = 'm';
    converter.serialization(value);
    verify(storeByte).writeChar(value);

    when(converter.deserialization()).thenReturn(value);
    char deserializationValue = (char) converter.deserialization();
    verify(storeByte).readChar();

    assertEquals(value, deserializationValue);
  }

  @Test
  void testString() {
    String value = "hello";
    converter.serialization(value);
    verify(storeByte).writeString(value);

    when(converter.deserialization()).thenReturn(value);
    String deserializationValue = (String) converter.deserialization();
    verify(storeByte).readString();

    assertEquals(value, deserializationValue);
  }

  @Test
  void testNull() {
    assertThrows(NullPointerException.class, () -> {
      converter.serialization(null);
    });
  }

  @Test
  void testUser() {
    short shortValue = 128;
    int intValue = 3456;
    User user = new User(shortValue, intValue);
    converter.serialization(user);
    verify(objectConverter).serializeObject(user);

    when(converter.deserialization()).thenReturn(user);
    User deserializationUser = (User) converter.deserialization();
    verify(objectConverter).deserializeObject();

    assertEquals(user.getValue(), deserializationUser.getValue());
  }
}
