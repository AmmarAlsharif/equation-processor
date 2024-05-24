package com.company;

import static java.lang.Character.toLowerCase;

import java.util.Scanner;

public class VariableProvider {

  private final boolean[] usedVariables = new boolean[26];
  private final double[] variableValue = new double[26];
  private final Scanner scanner;
  private static VariableProvider instance;

  private VariableProvider() {
    this.scanner = new Scanner(System.in);
  }

  public static VariableProvider getInstance() {
    if (instance == null) {
      instance = new VariableProvider();
    }
    return instance;
  }

  public double supplyVariableValue(char name) {
    int variableIndex = toLowerCase(name) - 'a';
    if (!usedVariables[variableIndex]) {
      System.out.println("Enter the value of \"" + name + "\"");
      variableValue[variableIndex] = scanner.nextDouble();
      usedVariables[variableIndex] = true;
    }
    return variableValue[variableIndex];
  }
}
