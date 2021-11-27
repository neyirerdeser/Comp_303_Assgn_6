
public class Turn implements Action {
    double aDegrees;

    /**
     * turning right: 90 degrees clock-wise: 90
     * turning left: 90 degrees counter-clock-wise: -90
     * @param pDirection
     */
    public Turn(Direction pDirection) {
        aDegrees = 90.0;
        if(pDirection.equals(Direction.LEFT)) aDegrees *= -1;
    }

    @Override
    public boolean conditionCheck(Robot pRobot) {
        return pRobot.getArmState().equals(Robot.ArmState.RETRACTED);
    }

    @Override
    public boolean execute(Robot pRobot) {
        if(!conditionCheck(pRobot))
            pRobot.retractArm();
        pRobot.turnRobot(aDegrees);
        return true;
    }

    @Override
    public String toString() {
        return "Turn";
    }

    @Override
    public void accept(ActionVisitor pVisitor) {
        pVisitor.visit(this);
    }
}
