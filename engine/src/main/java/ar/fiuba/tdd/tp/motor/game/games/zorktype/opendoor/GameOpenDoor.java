package ar.fiuba.tdd.tp.motor.game.games.zorktype.opendoor;

import ar.fiuba.tdd.tp.motor.game.components.ComponentKey;
import ar.fiuba.tdd.tp.motor.game.components.ComponentNormalDoor;
import ar.fiuba.tdd.tp.motor.game.components.ComponentRoom;
import ar.fiuba.tdd.tp.motor.game.games.zorktype.ZorkTypeGame;
import ar.fiuba.tdd.tp.motor.game.games.zorktype.gamestatus.GameStatusWon;

public class GameOpenDoor extends ZorkTypeGame {

    private ComponentRoom winningRoom;

    public GameOpenDoor() {
        ComponentRoom roomOne = new ComponentRoom();
        this.winningRoom = new ComponentRoom();
        ComponentKey key = new ComponentKey();
        roomOne.addComponent(key);
        ComponentNormalDoor door = new ComponentNormalDoor(roomOne, this.winningRoom, key);
        roomOne.addComponent(door);
        this.currentRoom = roomOne;
    }

    public boolean checkIfGameIsFinished() {
        if (this.getCurrentRoom() == this.winningRoom) {
            this.gameStatus = new GameStatusWon();
            return true;
        }
        return false;
    }

    @Override
    public String welcomeMessage() {
        return "Welcome to Open Door Game, to win you have to open the door!";
    }
}
