public interface Action {

    /**
     * for each action, this method will include the basic conditions that must be met
     * in order to execute the action on the given robot
     * @param pRobot robot, on which the actions will be executed on
     * @return whether the robot satisfies the base conditions for the action
     */
    public boolean conditionCheck(Robot pRobot);

    /**
     * executes the given action in the specified robot
     * in implementaion, it should check for conditions,
     * and in the event they are not met, fix them if possible
     * @param pRobot the robot to execute the action on
     * @return whether the action was executed successfully
     */
    public boolean execute(Robot pRobot);

    /**
     * method to receive and accept visitor
     * @param pVisitor
     */
    public void accept(ActionVisitor pVisitor);

    @Override
    public String toString();
}
