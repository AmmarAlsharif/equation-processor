package com.company;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EquationProcessor {
  private final Stack<Operator> operationsStack = new Stack<>();
  private final Stack<Operand> operandsStack = new Stack<>();
  private final Stack<Operator> parenthesesStack = new Stack<>();

  private class PartToken {
    EquationPart part;
    int tokenEndIndex;

    PartToken(int index, EquationPart part) {
      this.part = part;
      this.tokenEndIndex = index;
    }
  }

  public double evaluate(String equation) {
    return readAndSolveEquation(equation);
  }

  private double readAndSolveEquation(String equation) {
    operationsStack.push(Operator.openParentheses);
    parenthesesStack.push(Operator.openParentheses);
    char[] equationCharacters = getEquationAsCharacters(equation);
    int index = 0;
    while (index < equationCharacters.length) {
      PartToken readToken = getEquationPart(equationCharacters, index);
      index = readToken.tokenEndIndex;
      solveEquationPart(readToken.part);
    }
    return operandsStack.pop().getValue();
  }

  private char[] getEquationAsCharacters(String equation) {
    checkInput(equation);
    equation += ')';
    return equation
        .replace(" ", "")
        .replaceAll("[+][-]|[-][+]", "-")
        .replaceAll("[+][+]|[-][-]", "+")
        .toCharArray();
  }

  private void solveEquationPart(EquationPart part) {
    if (part.isOperand()) {
      operandsStack.push((Operand) part);
      return;
    }
    pushOperator((Operator) part);
  }

  private void pushOperator(Operator operator) {
    calculateAndPopLessPriority(operator);
    if (operator.isClosedParentheses()) {
      operationsStack.pop();
      return;
    }
    operationsStack.push(operator);
  }

  private PartToken getEquationPart(char[] equationParts, int index) {
    index = processForUnaryMinus(equationParts, index);
    char current = equationParts[index];
    if (isDigit(current)) {
      PartToken readToken = readNumber(equationParts, index);
      index = readToken.tokenEndIndex;
      return new PartToken(index, readToken.part);
    }
    if (isLetter(current)) {
      VariableProvider variableProvider = VariableProvider.getInstance();
      double variableValue = variableProvider.supplyVariableValue(current);
      return new PartToken(++index, new Operand(variableValue));
    }
    checkForParentheses(current);
    return new PartToken(++index, new Operator(current));
  }

  private int processForUnaryMinus(char[] equationParts, int index) {
    if (equationParts[index] == '-') {
      boolean ifExistUnaryMinus =
          (index >= 1 && (isOperator(equationParts[index - 1]) || equationParts[index - 1] == '('))
              || index == 0;
      if (ifExistUnaryMinus) {
        operationsStack.push(Operator.multiplication);
        operandsStack.push(new Operand(-1));
        return ++index;
      }
    }
    return index;
  }

  private void checkForParentheses(char current) {
    switch (current) {
      case '(':
        parenthesesStack.push(Operator.openParentheses);
        break;
      case ')':
        if (parenthesesStack.isEmpty()) {
          throw new IllegalArgumentException("Parentheses not matched");
        }
        parenthesesStack.pop();
        break;
    }
  }

  private PartToken readNumber(char[] equationParts, int index) {
    double num = 0;
    while (index < equationParts.length && isDigit(equationParts[index])) {
      num = (num * 10) + (equationParts[index++] - '0');
    }
    if (index < equationParts.length && equationParts[index] == '.') {
      double divisor = 10;
      index++;
      while (index < equationParts.length && isDigit(equationParts[index])) {
        num = num + (equationParts[index++] - '0') / divisor;
        divisor *= 10;
      }
    }
    return new PartToken(index, new Operand(num));
  }

  private void calculateAndPopLessPriority(Operator operator) {
    while (!operationsStack.isEmpty()
        && !operationsStack.peek().isOpenParentheses()
        && operationsStack.peek().getPrecedence() >= operator.getPrecedence()) {
      doCalculations();
    }
  }

  private void doCalculations() {
    Operand operand1 = operandsStack.pop();
    Operand operand2 = operandsStack.pop();
    operandsStack.push(operand2.doOperation(operand1, operationsStack.pop()));
  }

  private boolean isOperator(char ch) {
    return String.valueOf(ch).matches("[-,*,/,+,^]");
  }

  public void checkInput(String equation) {
    Pattern pattern = Pattern.compile("\\d[ ]+\\d");
    Matcher matcher = pattern.matcher(equation);
    if (matcher.find()) {
      throw new IllegalArgumentException("Syntax error, space separated numbers are not allowed");
    }
    String operations = "[*/^]";
    String plusMinus = "[-+]";
    pattern =
        Pattern.compile(
            operations
                + "[ ]*"
                + operations
                + "|"
                + plusMinus
                + "[ ]*"
                + plusMinus
                + "[ ]*"
                + plusMinus
                + "|"
                + plusMinus
                + "[ ]*"
                + operations);
    matcher = pattern.matcher(equation);
    if (matcher.find()) {
      throw new IllegalArgumentException("Syntax error, Consecutive operations are not allowed");
    }
    char firstChar = equation.charAt(0);
    if (firstChar != '(' && firstChar != '-' && !isDigit(firstChar) && !isLetter(firstChar)) {
      throw new IllegalArgumentException("Syntax error, can't start with \"" + firstChar + "\"");
    }
    char lastChar = equation.charAt(equation.length() - 1);
    if (lastChar != ')' && (!isDigit(lastChar) || isOperator(lastChar))) {
      throw new IllegalArgumentException("Syntax error, can't end with \"" + lastChar + "\"");
    }
  }
}
