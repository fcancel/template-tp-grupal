package ar.fiuba.tdd.tp.game.player.action;

import ar.fiuba.tdd.tp.game.commons.constraint.Constraint;
import ar.fiuba.tdd.tp.game.component.Component;
import ar.fiuba.tdd.tp.game.player.Player;
import ar.fiuba.tdd.tp.game.player.action.impl.Action;
import ar.fiuba.tdd.tp.game.player.action.resolver.ActionDeciderAbstract;

import java.util.List;

/**
 * Some actions take not one but two objects: PUT BOOK ON SHELF, UNLOCK DOOR WITH KEY.
 * That is, and action with two objects in different roles.
 * For example: UNLOCK DOOR WITH KEY, the key is the "instrument" or "agent" of the action, but the door
 * is what's being unlocked.
 * The door is called the "direct object," because it's the object that's being directly affected by the action.
 * The key is called the "indirect object," because it's being used in the course of performing the action but
 * isn't the main focus of the action.
 */
public abstract class TwoObjectActionDecider extends ActionDeciderAbstract {

    public TwoObjectActionDecider(String commandName, Constraint constraint) {
        super(commandName, constraint);
    }


    @Override
    public String execute(String action) {
        //TODO Parse action and populate arguments
        return this.execute(null, null);
    }

    protected abstract String execute(Component directComponent, Component indirectComponent);

}