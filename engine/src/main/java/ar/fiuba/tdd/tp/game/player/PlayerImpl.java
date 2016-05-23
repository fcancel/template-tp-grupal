package ar.fiuba.tdd.tp.game.player;

import ar.fiuba.tdd.tp.game.player.action.resolver.ActionResolver;
import ar.fiuba.tdd.tp.game.scenario.context.Context;

public class PlayerImpl implements Player {

    private ActionResolver actionResolver;
    private final Inventory inventory;
    private Context currentContext;

    public PlayerImpl(int inventoryLimit) {
        this.inventory = new Inventory(inventoryLimit);
    }

    @Override
    public String doCommand(String command) {
        return this.actionResolver.execute(command);
    }

    @Override
    public Boolean has(String componentName) {
        return this.inventory.getComponents().stream()
                .anyMatch(component -> component.getName().equals(componentName));
    }

    @Override
    public Context getCurrentContext() {
        return currentContext;
    }

    public void setCurrentContext(Context currentContext) {
        this.currentContext = currentContext;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void setActionResolver(ActionResolver actionResolver) {
        this.actionResolver = actionResolver;
    }


}