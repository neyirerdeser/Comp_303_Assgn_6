public class RechargeDecorator extends ActionDecorator {

    public RechargeDecorator(Action pDecoratedAction) {
        super(pDecoratedAction);
    }

    @Override
    public boolean conditionCheck(Robot pRobot) {
        return aDecoratedAction.conditionCheck(pRobot);
    }

    /**
     * recharge the robot before even attempting to execute the action
     * @param pRobot
     * @return
     */
    @Override
    public boolean execute(Robot pRobot) {
        pRobot.rechargeBattery();
        Logger.logToFile(this, pRobot);
        return aDecoratedAction.execute(pRobot);
    }

    @Override
    public String toString() {
        return "Recharge";
    }

    @Override
    public void accept(ActionVisitor pVisitor) {
        aDecoratedAction.accept(pVisitor);
    }
}
