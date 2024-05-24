package com.company;

public class EquationPart {
  public boolean isOperand() {
    return this instanceof Operand;
  }
}
