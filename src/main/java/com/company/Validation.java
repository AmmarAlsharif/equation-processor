package com.company;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;
import static java.lang.Character.isLetterOrDigit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

  private static final Pattern CONSECUTIVE_OPERATIONS_PATTERN =
      Pattern.compile("[*/^][ ]*[*/^]|[-+][ ]*[-+][ ]*[-+]|[-+][ ]*[*/^]");
  private static final Pattern SPACED_NUMBERS_PATTERN = Pattern.compile("\\d[ ]+\\d");

  public static void validate(String equation) {
    Matcher matcher = SPACED_NUMBERS_PATTERN.matcher(equation);
    if (matcher.find()) {
      throw new IllegalArgumentException("Syntax error, space separated numbers are not allowed");
    }

    matcher = CONSECUTIVE_OPERATIONS_PATTERN.matcher(equation);
    if (matcher.find()) {
      throw new IllegalArgumentException("Syntax error, Consecutive operations are not allowed");
    }
    char firstChar = equation.charAt(0);
    if (firstChar != '(' && firstChar != '-' && !isDigit(firstChar) && !isLetter(firstChar)) {
      throw new IllegalArgumentException("Syntax error, can't start with \"" + firstChar + "\"");
    }
    char lastChar = equation.charAt(equation.length() - 1);
    if (lastChar != ')' && !isLetterOrDigit(lastChar)) {
      throw new IllegalArgumentException("Syntax error, can't end with \"" + lastChar + "\"");
    }
  }
}
