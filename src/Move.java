
public class Move implements Action {
    double aDistance;

    public Move(double pDistance) {
        aDistance = pDistance;
    }

    @Override
    public boolean conditionCheck(Robot pRobot) {
        return pRobot.getArmState().equals(Robot.ArmState.RETRACTED);
    }

    /**
     * condition can be fixed, if it is not satisfied
     * @param pRobot the robot to execute the action on
     * @return true
     */
    @Override
    public boolean execute(Robot pRobot) {
        if(!conditionCheck(pRobot)) pRobot.retractArm();
        pRobot.moveRobot(aDistance);
        return true;
    }

    @Override
    public String toString() {
        return "Move";
    }

    @Override
    public void accept(ActionVisitor pVisitor) {
        pVisitor.visit(this);
    }
}
