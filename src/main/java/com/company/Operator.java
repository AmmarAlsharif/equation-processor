package com.company;


public class Operator extends EquationPart {

    public final static Operator plus = new Operator('+');
    public final static Operator minus = new Operator('-');
    public final static Operator division = new Operator('/');
    public final static Operator multiplication = new Operator('*');
    public final static Operator exponent = new Operator('^');
    public final static Operator openParentheses = new Operator('(');
    public final static Operator closedParentheses = new Operator(')');
    private final char operator;

    public Operator(char operator) {
        this.operator = operator;
    }

    public boolean isPlus() {
        return this.equals(Operator.plus);
    }

    public boolean isMinus() {
        return this.equals(Operator.minus);
    }

    public boolean isDivision() {
        return this.equals(Operator.division);
    }

    public boolean isMultiplication() {
        return this.equals(Operator.multiplication);
    }

    public boolean isExponent() {
        return this.equals(Operator.exponent);
    }

    public boolean isOpenParentheses() {
        return this.equals(Operator.openParentheses);
    }

    public boolean isClosedParentheses() {
        return this.equals(Operator.closedParentheses);
    }

    public int getPrecedence() {
        switch (this.operator) {
            case '+':
            case '-':
                return 0;
            case '*':
            case '/':
                return 1;
            case '^':
                return 2;
            case '(':
                return 3;
            default:
                return -1;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return this.operator == ((Operator) obj).operator;
    }
}