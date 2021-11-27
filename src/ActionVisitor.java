public interface ActionVisitor {
    void visit(Move pAction);
    void visit(Turn pAction);
    void visit(Grab pAction);
    void visit(Release pAction);
    void visit(Compact pAction);
    void visit(Empty pAction);
}
