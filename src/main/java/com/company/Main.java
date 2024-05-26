package com.company;

import java.io.IOException;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) throws IOException {
    Scanner scanner = new Scanner(System.in);
    EquationProcessor exp = new EquationProcessor();
    String expression = scanner.nextLine().trim();
    System.out.println(exp.evaluate(expression));
  }
}

