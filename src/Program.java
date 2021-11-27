import java.util.ArrayList;
import java.util.List;

public class Program extends CompositeAction {

    List<Action> aActions = new ArrayList<>();

    public Program(Action... pActions) {
        aActions.addAll(List.of(pActions));
    }

    /**
     * adds zero to multiple actions to the program
     * @param pActions actions to be added
     */
    public void addActions(Action... pActions) {
        aActions.addAll(List.of(pActions));
    }

    /**
     * removes zero to multiple actions from the program
     * @param pActions actions to be removed
     * @return true if all actions given were found and removed
     *          false if at least one action given was not in the program
     */
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
    public boolean checkCharge(Robot pRobot) {
        return pRobot.getBatteryCharge() > 5;
    }


    /**
     * execute all basic/complex actions in order
     * and reduces battery if an  action was executed successfully
     * @param pRobot the robot to execute the action on
     * @return false, to avoid draining battery an extra time for the complex actions
     */
    public void execute(Robot pRobot) {
        for(Action action : aActions) {
            if(!checkCharge(pRobot)) pRobot.rechargeBattery();
            if(action.execute(pRobot)) {
                if(!(action instanceof ComplexAction)) {
                    if(action instanceof ActionDecorator) {
                        if(!(((ActionDecorator) action).aDecoratedAction instanceof ComplexAction)){
                            pRobot.updateBatteryLevel();
                            Logger.logToFile(((ActionDecorator) action).aDecoratedAction, pRobot);
                        }
                    } else {
                        pRobot.updateBatteryLevel();
                        Logger.logToFile(action, pRobot);
                    }
                }
            } else break;
        }
    }

    public void accept(ActionVisitor pVisitor) {
        for(Action action : aActions)
            action.accept(pVisitor);
    }

    public double calculateDistance() {
        DistanceVisitor visitor = new DistanceVisitor();
        this.accept(visitor);
        return visitor.aDistance;
    }

    public int countCompacting() {
        CompactorVisitor visitor = new CompactorVisitor();
        this.accept(visitor);
        return visitor.aCompacted;
    }

}
