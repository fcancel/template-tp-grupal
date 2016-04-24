package ar.fiuba.tdd.tp.motor.game.components;

import ar.fiuba.tdd.tp.motor.game.games.zorktype.ZorkTypeGame;

public class ComponentDoor extends GameComponents {

    ComponentKey keyRequired = null;
    ComponentRoom roomItLeadsTo;
    ZorkTypeGame game;

    public ComponentDoor(ZorkTypeGame game, ComponentRoom roomItLeadsTo, ComponentKey keyRequired) {
        this.roomItLeadsTo = roomItLeadsTo;
        this.keyRequired = keyRequired;
        this.game = game;
    }

    public ComponentDoor(ZorkTypeGame game, ComponentRoom roomItLeadsTo) {
        this(game, roomItLeadsTo, null);
    }

    public Boolean matchingKey(GameComponentsSimple component) {
        return component.getDescription().equals(this.keyRequired.getDescription());
    }

    public void unlockDoor() {
        this.keyRequired = null;
    }

    public ComponentRoom getWhereItLeadsTo() {
        return this.roomItLeadsTo;
    }

    public Boolean isThisDoorUnlocked() {
        return this.keyRequired == null;
    }

    private void goToRoom(ZorkTypeGame game) {
        game.setCurrentRoom(getWhereItLeadsTo());
    }

    @Override
    public String getDescription() {
        return "Door" + String.valueOf(this.id);
    }

    @Override
    public Boolean pick() {
        return false;
    }

    @Override
    public Boolean close() {
        return true;
    }

    @Override
    public Boolean open() {
        if (isThisDoorUnlocked()) {
            goToRoom(this.game);
            return true;
        }
        for (GameComponentsSimple component: this.game.getPlayerItems()) {
            if (matchingKey(component)) {
                unlockDoor();
                goToRoom(this.game);
                return true;
            }
        }
        return false;
    }
}
