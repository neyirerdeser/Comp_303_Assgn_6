public abstract class ActionDecorator implements Action {

    Action aDecoratedAction;

    public ActionDecorator(Action pDecoratedAction) {
        aDecoratedAction = pDecoratedAction;
    }

    @Override
    abstract public boolean conditionCheck(Robot pRobot);

    @Override
    abstract public boolean execute(Robot pRobot);
}
