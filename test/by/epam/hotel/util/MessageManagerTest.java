package by.epam.hotel.util;

import static org.testng.Assert.assertEquals;

import java.util.Locale;

import org.testng.annotations.Test;

public class MessageManagerTest {
  @Test
  public void f() {
	  String  message = MessageManager.getProrerty("message.loginerror", new Locale("ru","RU"));
	  assertEquals("Неверный логин или пароль.", message);
  }
}
