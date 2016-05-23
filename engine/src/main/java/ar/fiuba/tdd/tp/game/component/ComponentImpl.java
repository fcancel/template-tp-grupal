package ar.fiuba.tdd.tp.game.component;

import ar.fiuba.tdd.tp.game.commons.constraint.Constraint;
import ar.fiuba.tdd.tp.game.component.state.ComponentState;
import ar.fiuba.tdd.tp.game.player.action.impl.Action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ComponentImpl implements Component {

    private final String name;
    private final List<ComponentState> states;
    private Map<String,List<Action>> commandActions = new HashMap<>();
    private Map<String,Constraint> commandConstraint = new HashMap<>();

    public ComponentImpl(String name, List<ComponentState> states) {
        this.name = name;
        this.states = states;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Set<String> getSupportedActions() {
        return commandActions.keySet();
    }

    @Override
    public void addAction(String actionName, List<Action> actionsList, Constraint constraint) {
        commandActions.put(actionName, actionsList);
        commandConstraint.put(actionName, constraint);
    }

    @Override
    public Boolean satisfiesConstraints(String action) {
        return commandConstraint.get(action).isSatisfied();
    }

    @Override
    public String doAction(String commandName) {
        StringBuffer message = new StringBuffer();
        for (Action action : commandActions.get(commandName)) {
            message.append(action.doAction()).append("\n");
        }
        return message.toString();
    }

}
