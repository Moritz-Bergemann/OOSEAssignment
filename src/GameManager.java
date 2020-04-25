import java.util.HashMap;

public class GameManager {
    private Player player;

    public GameManager() {
        player = new Player();

    }

    public static void main(String[] args) {
        //TODO Perform setup (read file maybe?)

        runGame();
    }

    public static void runGame() {
        EnemyChances chances = new EnemyChances();

        while (!player.getIsDead() && !player.getSlainDragon()) {
            //Running intermediate stage between battles
            runIntermediate();

            Enemy enemy = chances.getRandomEnemy();

            BattleManager battle = new BattleManager(player, enemy);

            battle.run();
        }

        MainMenu.showStats(player);
    }

    public static void runIntermediate() {

    }
}
