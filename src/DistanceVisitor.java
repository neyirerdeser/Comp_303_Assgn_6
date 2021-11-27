public class DistanceVisitor implements ActionVisitor {
    
    double aDistance = 0;
    @Override
    public void visit(Move pAction) {
        aDistance += pAction.aDistance;
    }

    @Override
    public void visit(Turn pAction) {}

    @Override
    public void visit(Grab pAction) {}

    @Override
    public void visit(Release pAction) {}

    @Override
    public void visit(Compact pAction) {}

    @Override
    public void visit(Empty pAction) {}

}
