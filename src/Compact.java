
public class Compact implements Action {

    /**
     * compactor cannot be full
     * gripper must be holding an object
     * @param pRobot robot, on which the actions will be executed on
     * @return
     */
    @Override
    public boolean conditionCheck(Robot pRobot) {
        return pRobot.getCompactorLevel() < 10 && pRobot.getGripperState() == Robot.GripperState.HOLDING_OBJECT;
    }

    @Override
    public boolean execute(Robot pRobot) {
        if(!conditionCheck(pRobot)) return false;
        pRobot.compact();
        return true;
    }

    @Override
    public String toString() {
        return "Compact";
    }

    @Override
    public void accept(ActionVisitor pVisitor) {
        pVisitor.visit(this);
    }
}
