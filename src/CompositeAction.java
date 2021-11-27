import java.util.ArrayList;
import java.util.List;

public abstract class CompositeAction {
    List<Action> aActions = new ArrayList<>();
    abstract void addActions(Action... pActions);
    abstract boolean removeActions(Action... pActions);

}
