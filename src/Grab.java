
public class Grab implements Action {

    /**
     * gripper cannot be already holding an object
     * @param pRobot robot, on which the actions will be executed on
     * @return
     */
    @Override
    public boolean conditionCheck(Robot pRobot) {
        return !pRobot.getGripperState().equals(Robot.GripperState.HOLDING_OBJECT);
    }

    /**
     * small unsatisfied conditions are fixed before execution
     * @param pRobot the robot to execute the action on
     * @return false if the gripper was already holding an object
     */
    @Override
    public boolean execute(Robot pRobot) {
        if(!conditionCheck(pRobot)) return false;
        if(pRobot.getArmState().equals(Robot.ArmState.EXTENDED))
            pRobot.retractArm();
        if(pRobot.getGripperState().equals(Robot.GripperState.EMPTY))
            pRobot.openGripper();
        if(pRobot.getArmState().equals(Robot.ArmState.RETRACTED))
            pRobot.extendArm();

        pRobot.closeGripper();
        pRobot.retractArm();
        return true;
    }

    @Override
    public String toString() {
        return "Grab";
    }

    @Override
    public void accept(ActionVisitor pVisitor) {
        pVisitor.visit(this);
    }
}
