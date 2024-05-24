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

// Test Cases:
//  5  * 5+   ( ( 2 .   2  + 0 . 8 )   *   2 ) ^ 2 ^ 2
//  5*5+(2.2+1+0.8)^2
//  x  * X + ( ( 2 . 2 +   0 . 8 )   *   2 ) ^ y ^ z
//  (z^(x+1-1)^((1+(y-1))/x))
//  (-2^-(12.3+1-1)^((1+(2.2-1))/12.3)) ans = -0.3376163771132725
