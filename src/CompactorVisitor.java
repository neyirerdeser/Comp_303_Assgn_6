public class CompactorVisitor implements ActionVisitor {

    int aCompacted = 0;

    @Override
    public void visit(Move pAction) {}

    @Override
    public void visit(Turn pAction) {}

    @Override
    public void visit(Grab pAction) {}

    @Override
    public void visit(Release pAction) {}

    @Override
    public void visit(Compact pAction) {
        aCompacted++;
    }

    @Override
    public void visit(Empty pAction) {}
}
