package clocktower;

import clocktower.bean.Player;
import clocktower.bean.TownSquare;
import cn.hutool.json.JSONUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game {

    public static ExecutorService service = Executors.newScheduledThreadPool(5);



    public static void main(String[] args) {

        TownSquare square = new TownSquare(1,5);

        String data = "{\"edition\":1,\"staff\":\"STAFF_5\",\"playerNum\":5,\"playerMap\":{\"3\":{\"drunkEndTime\":0,\"id\":3,\"isEnemyOfFortuenTeller\":false,\"dead\":false,\"drunked\":false,\"character\":\"Undertaker\"},\"2\":{\"drunkEndTime\":0,\"id\":2,\"isEnemyOfFortuenTeller\":false,\"dead\":false,\"drunked\":false,\"character\":\"Imp\"},\"1\":{\"drunkEndTime\":0,\"id\":1,\"isEnemyOfFortuenTeller\":false,\"dead\":false,\"drunked\":false,\"character\":\"Poisoner\"},\"5\":{\"drunkEndTime\":0,\"id\":5,\"isEnemyOfFortuenTeller\":false,\"dead\":false,\"drunked\":false,\"character\":\"Librarian\"},\"4\":{\"drunkEndTime\":0,\"id\":4,\"isEnemyOfFortuenTeller\":false,\"dead\":false,\"drunked\":false,\"character\":\"WasherWoman\"}}}";


        square = JSONUtil.toBean(data, TownSquare.class);
        System.out.println(JSONUtil.toJsonStr(square));

        for(Player player:square.getPlayerMap().values()){
            System.out.println(player.getId()+" "+player.getCharacter().getName());
        }

        square.squareRun();
        System.out.println("OK");
    }

}
