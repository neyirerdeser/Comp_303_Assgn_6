
public class Empty implements Action {

    /**
     * compactor must have at least one item in it
     * @param pRobot robot, on which the actions will be executed on
     * @return
     */
    @Override
    public boolean conditionCheck(Robot pRobot) {
        return pRobot.getCompactorLevel() > 0;
    }

    @Override
    public boolean execute(Robot pRobot) {
        if(!conditionCheck(pRobot)) return false;
        pRobot.emptyCompactor();
        return true;
    }

    @Override
    public String toString() {
        return "Empty compactor";
    }

    @Override
    public void accept(ActionVisitor pVisitor) {
        pVisitor.visit(this);
    }
}
