
public class Release implements Action {


    /**
     * gripper holding an object is not enforced
     * since the releasing action can happen regardles of that
     * there are hence no hard conditions
     * @param pRobot robot, on which the actions will be executed on
     * @return true
     */
    @Override
    public boolean conditionCheck(Robot pRobot) {
        return true;
//        return aRobot.getGripperState().equals(Robot.GripperState.HOLDING_OBJECT);
    }

    @Override
    public boolean execute(Robot pRobot) {
        if(!conditionCheck(pRobot)) return false;
        if(pRobot.getArmState().equals(Robot.ArmState.EXTENDED))
            pRobot.retractArm();
        if(!pRobot.getGripperState().equals(Robot.GripperState.OPEN))
            pRobot.openGripper();
        return true;
    }

    @Override
    public String toString() {
        return "Release";
    }

    @Override
    public void accept(ActionVisitor pVisitor) {
        pVisitor.visit(this);
    }

}
