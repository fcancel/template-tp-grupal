package ar.fiuba.tdd.tp.engine;

import ar.fiuba.tdd.tp.engine.player.Player;

public class GameImpl implements Game {

    protected String gameName;
    protected String help;
    protected Player player;
    protected String gameWelcomeMessage;

    @Override
    public String getName() {
        return gameName;
    }

    @Override
    public Boolean isFinished() {
        //TODO ver como hacer esto
        return false;
    }

    @Override
    public String help() {
        return help;
    }

    @Override
    public String doCommand(String clientMessage) {
        String message = player.doCommand(clientMessage);
        if (isFinished()) {
            return "Game over.";
        }
        return message;
    }

    @Override
    public String getWelcomeMessage() {
        return gameWelcomeMessage;
    }


    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setGameWelcomeMessage(String gameWelcomeMessage) {
        this.gameWelcomeMessage = gameWelcomeMessage;
    }
}
