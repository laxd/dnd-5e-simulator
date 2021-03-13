package uk.laxd.dndSimulator.dice;

public class RollDecorator extends Roll {

    private Roll roll;

    public RollDecorator(Roll roll) {
        this.roll = roll;
    }

    @Override
    public RollResult roll() {
        return roll.roll();
    }

}
