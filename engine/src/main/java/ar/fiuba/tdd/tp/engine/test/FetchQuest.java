package ar.fiuba.tdd.tp.engine.test;

import ar.fiuba.tdd.tp.engine.GameImpl;
import ar.fiuba.tdd.tp.engine.commons.condition.Condition;
import ar.fiuba.tdd.tp.engine.commons.condition.have.NoConditionRequired;
import ar.fiuba.tdd.tp.engine.commons.condition.have.PlayerInventoryNotFull;
import ar.fiuba.tdd.tp.engine.commons.constraint.Constraint;
import ar.fiuba.tdd.tp.engine.commons.constraint.ConstraintImpl;
import ar.fiuba.tdd.tp.engine.component.Component;
import ar.fiuba.tdd.tp.engine.component.ComponentImpl;
import ar.fiuba.tdd.tp.engine.component.state.ComponentState;
import ar.fiuba.tdd.tp.engine.player.Player;
import ar.fiuba.tdd.tp.engine.player.PlayerImpl;
import ar.fiuba.tdd.tp.engine.player.action.ActionDecider;
import ar.fiuba.tdd.tp.engine.player.action.NoObjectActionDecider;
import ar.fiuba.tdd.tp.engine.player.action.OneObjectActionDecider;
import ar.fiuba.tdd.tp.engine.player.action.impl.Action;
import ar.fiuba.tdd.tp.engine.player.action.impl.LookAround;
import ar.fiuba.tdd.tp.engine.player.action.resolver.ActionResolver;
import ar.fiuba.tdd.tp.engine.scenario.context.Context;
import ar.fiuba.tdd.tp.engine.scenario.context.ContextImpl;

import java.util.ArrayList;
import java.util.List;

public class FetchQuest extends GameImpl {
    private static final String GAME_NAME = "Fetch Quest";
    private static final String ROOM_NAME = "Room";
    private static final String STICK_NAME = "stick";
    private static final String GAME_HELP = "Fetch Quest Help commands";
    private static final int INVENTORY_LIMIT = 10;
    private static final String LOOK_AROUND = "look around";
    private static final String PICK = "pick";

    public FetchQuest() {
        this.setGameName(GAME_NAME);
        this.setHelp(GAME_HELP);
        this.setPlayer(createPlayer());
        makeFirstRoomAndPutPlayerInIt(player);
    }

    private void makeFirstRoomAndPutPlayerInIt(Player player) {
        List<Component> components = new ArrayList<>();
        List<ComponentState> states = new ArrayList<>();

        components.add(new ComponentImpl(STICK_NAME,states));

        Context context = new ContextImpl(ROOM_NAME, components);

        player.putInRoom(context);
    }

    private Player createPlayer() {
        Player player = new PlayerImpl(INVENTORY_LIMIT);
        List<Action> actions = new ArrayList<>();
        Constraint constraints;
        List<Condition> conditions = new ArrayList<>();

        conditions.add(new NoConditionRequired());
        constraints = new ConstraintImpl(conditions);
        actions.add(new LookAround(player));
        ActionDecider actionDecider = new NoObjectActionDecider(LOOK_AROUND,actions, constraints);
        actions.clear();
        ActionResolver actionResolver = new ActionResolver();
        actionResolver.addActionDecider(actionDecider);


        conditions.add(new PlayerInventoryNotFull(player));
        constraints = new ConstraintImpl(conditions);
        actionDecider = new OneObjectActionDecider(PICK, constraints, player);
        conditions.clear();
        actionResolver.addActionDecider(actionDecider);

        player.setActionResolver(actionResolver);

        return player;
    }

}
