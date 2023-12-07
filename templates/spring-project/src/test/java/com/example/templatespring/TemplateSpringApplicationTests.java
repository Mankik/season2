package com.example.templatespring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// https://junit.org/junit5/docs/current/user-guide/
@SpringBootTest
class TemplateSpringApplicationTests {
  @Test
  void contextLoads() {
  }

  @Test
  void testAssertTrue() {
    assertTrue(1 == 1);
  }

  @Test
  void testAssertFalse() {
    assertFalse(1 != 1);
  }

  @Test
  void testAssertNull() {
    assertNull(null);
  }

  @Test
  void testAssertNotNull() {
    assertNotNull(1);
  }

  @Test
  void testAssertEquals() {
    assertEquals(1, 0 + 1);
  }

  @Test
  void testAssertThrows() {
    assertThrows(ArithmeticException.class, () -> {
      int i = 1 / 0;
    });
  }
}
