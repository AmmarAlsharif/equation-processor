package com.company;

public class Operand extends EquationPart {
    private final double value;

    public Operand(double value) {
        this.value = value;
    }

    public double getValue() {
        return this.value;
    }

    public Operand doOperation(Operand operand, Operator operator) {
        if (operator.isPlus())
            return new Operand(this.getValue() + operand.getValue());
        if (operator.isMinus())
            return new Operand(this.getValue() - operand.getValue());
        if (operator.isMultiplication())
            return new Operand(this.getValue() * operand.getValue());
        if (operator.isDivision())
            return new Operand(this.getValue() / operand.getValue());
        if (operator.isExponent())
            return new Operand(Math.pow(this.getValue(), operand.getValue()));
        return null;
    }
}
