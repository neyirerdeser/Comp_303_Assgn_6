import java.util.List;

public class ComplexAction extends CompositeAction implements Action {

    /**
     * adds zero to multiple actions to the program
     * @param pActions actions to be added
     */
    @Override
    public void addActions(Action... pActions) {
        aActions.addAll(List.of(pActions));
    }

    /**
     * removes zero to multiple actions from the program
     * @param pActions actions to be removed
     * @return true if all actions given were found and removed
     *          false if at least one action given was not in the program
     */
    @Override
    public boolean removeActions(Action... pActions) {
        boolean allFound = true;
        for(Action action : pActions)
            allFound &= aActions.remove(action);
        return allFound;
    }

    /**
     * robot needs enough charge to ensure it can execute an action
     * @param pRobot robot, on which the actions will be executed on
     * @return
     */
    @Override
    public boolean conditionCheck(Robot pRobot) {
        return pRobot.getBatteryCharge() > 5;
    }

    /**
     * execute all basic/complex actions in order
     * and reduces battery if an  action was executed successfully
     * @param pRobot the robot to execute the action on
     * @return false, to avoid draining battery an extra time for the complex actions
     */
    @Override
    public boolean execute(Robot pRobot) {
        for(Action action : aActions) {
            if(!conditionCheck(pRobot)) pRobot.rechargeBattery();
            if(action.execute(pRobot)) {
                pRobot.updateBatteryLevel();
                Logger.logToFile(action, pRobot);
            } else {
                System.out.println(action.toString());
                return false;
            }
        }
        return true;
    }

    @Override
    public void accept(ActionVisitor pVisitor) {
        for(Action action : aActions)
            action.accept(pVisitor);
    }

}
