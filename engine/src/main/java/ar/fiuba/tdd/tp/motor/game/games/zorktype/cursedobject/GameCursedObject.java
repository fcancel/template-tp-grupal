package ar.fiuba.tdd.tp.motor.game.games.zorktype.cursedobject;

import ar.fiuba.tdd.tp.motor.game.components.*;
import ar.fiuba.tdd.tp.motor.game.components.gamestatus.GameStatusWon;
import ar.fiuba.tdd.tp.motor.game.games.zorktype.ZorkTypeGame;


public class GameCursedObject extends ZorkTypeGame {

    private ComponentRoom winningRoom;

    public GameCursedObject() {
        BuilderZork builder = new BuilderZork();
        ComponentRoom roomOne = builder.createRoom();
        ComponentKey key = builder.createKey();
        roomOne.addComponent(key);
        ComponentRoom roomTwo = builder.createRoom();
        builder.createNormalDoor(roomOne, roomTwo, key);

        this.winningRoom = builder.createRoom();
        builder.createCursedDoor(roomTwo, this.winningRoom, key);
        ComponentThief thief = new ComponentThief();
        roomTwo.addComponent(thief);

        this.currentRoom = roomOne;
    }

    @Override
    public boolean checkIfGameIsFinished() {
        if (getCurrentRoom() == this.winningRoom) {
            this.gameStatus = new GameStatusWon();
            return true;
        }
        return false;
    }

    @Override
    public String welcomeMessage() {
        return "Welcome to the Cursed Object game, here an item is cursed and won't let you go through a door.";
    }
}