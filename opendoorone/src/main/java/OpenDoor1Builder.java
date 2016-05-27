import ar.fiuba.tdd.tp.engine.Game;
import ar.fiuba.tdd.tp.engine.GameBuilder;
import ar.fiuba.tdd.tp.engine.elements.Content;
import ar.fiuba.tdd.tp.engine.utils.CommandsUtils;

@SuppressWarnings("CPD-START")
public class OpenDoor1Builder implements GameBuilder {

    @Override
    public Game build() {
        Game game = new Game();
        Content room1 = new Content("room1");
        Content room2 = new Content("room2");
        Content player = new Content("player");
        Content key = new Content("key");
        Content door = new Content("door");
        room1.put(player);
        room1.put(key);
        room1.put(door);
        addContentCommands(player, key, door, room1, room2);
        game.setWinCondition(() -> room2.has("player"));
        game.setLoseCondition(() -> false);
        game.setCommand(CommandsUtils.getSameRoomCommand("pick .*", "pick", player, 1));
        game.setCommand(CommandsUtils.getLookAroundCommand("look around", player));
        game.setCommand(CommandsUtils.getSameRoomCommand("open .*", "open", player, 1));
        return game;
    }


    private void addContentCommands(Content player, Content key, Content door, Content room1, Content room2) {
        CommandsUtils.addPickCommand(player, key, "key", "pick");
        door.addCommand("open", (params) -> player.has("key"), (params) -> {
            room2.put(room1.take("player"));
            return "You opened a door and walked to room2";
        });
    }
}