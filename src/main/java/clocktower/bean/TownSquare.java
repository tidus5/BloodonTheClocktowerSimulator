package clocktower.bean;

import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static clocktower.bean.CharacterType.*;

public class TownSquare implements Runnable {
    private int edition;
    private int playerNum;
    private Staff staff;
    private Map<Integer, Player> playerMap = Maps.newHashMap();

    /**  00, 01 分别表示 第一夜，第一天。  10, 11 表示第二夜， 第二天*/
    private int dayPeriod = 0;

    public static Scanner sc = new Scanner(System.in);

    public TownSquare() {
    }

    public TownSquare(int edition, int players) {
        this.edition = edition;
        this.playerNum = players;
        this.staff = Staff.getStaff(players);
        initStaff();
    }

    private void initStaff(){
        List<Integer> idList = Lists.newArrayList();
        for (int i = 1; i <= playerNum; i++) {
            idList.add(i);
        }

        List<Player> townFoldList = addCharacter(TOWNS_FOLD.getId(), idList);
        List<Player> outsiderList = addCharacter(OUTSIDERS.getId(), idList);
        addCharacter(MINIONS.getId(), idList);
        addCharacter(DEMONS.getId(), idList);

        //占卜师，设置宿敌
        if(haveLivingCharacter(Character.FortuenTeller)){
            markEnemyOfFortuenTeller(townFoldList, outsiderList);
        }
        if(haveLivingCharacter(Character.Drunk)){
           markDrunkMisTaken(townFoldList, outsiderList);
        }
    }

    private void markDrunkMisTaken(List<Player> townFoldList, List<Player> outsiderList) {
        int id = getCharacterId(Character.Drunk);
        Player player = getPlayer(id);
        player.setDrunked(true);
        player.setDrunkEndTime(999);

        List<Character> characterList = Lists.newArrayList();
        characterList.addAll(Character.getCharacters(edition, TOWNS_FOLD.getId()));
        for(Player p : townFoldList){
            characterList.remove(p.getCharacter());
        }
        int randomIndex = RandomUtil.randomInt(characterList.size());
        characterList.get(randomIndex);
        player.setDrunkedMistaken(characterList.get(randomIndex));
    }

    private void markEnemyOfFortuenTeller( List<Player> townFoldList, List<Player> outsiderList){
        int id = getCharacterId(Character.FortuenTeller);
        List<Player> goodPlayer = Lists.newArrayList();
        goodPlayer.addAll(townFoldList);
        goodPlayer.addAll(outsiderList);

        for (Player player : goodPlayer){
            if(player.getCharacter() == Character.FortuenTeller){
                goodPlayer.remove(player);
                break;
            }
        }
        int randomIndex = RandomUtil.randomInt(goodPlayer.size());
        Player player = goodPlayer.get(randomIndex);
        player.setEnemyOfFortuenTeller(true);
    }

    private List<Player> addCharacter(int type, List<Integer> idList){
        List<Player> list = Lists.newArrayList();
        List<Character> characterList = Lists.newArrayList();
        characterList.addAll(Character.getCharacters(edition, type));
        for (int i = 0; i < staff.getNum(type); i++) {
            int randomIndex = RandomUtil.randomInt(characterList.size());

            Character character = characterList.remove(randomIndex);

            randomIndex = RandomUtil.randomInt(idList.size());
            Integer id = idList.remove(randomIndex);
            Player player = new Player(id, character);
            this.playerMap.put(player.getId(), player);
            list.add(player);
        }
        return list;
    }



    public int getEdition() {
        return edition;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public Staff getStaff() {
        return staff;
    }

    public Map<Integer, Player> getPlayerMap() {
        return playerMap;
    }

    public Player getPlayer(int id){
        return playerMap.get(id);
    }

    public Player getPlayerByCharacter(Character ch){
        return getPlayer(getCharacterId(ch));
    }

    public int getCharacterId(Character ch) {
        for (Player player : playerMap.values()) {
            if (player.getCharacter() == ch) {
                return player.getId();
            }
        }
        return 0;
    }

    public boolean haveLivingCharacter(Character ch){
        int id = getCharacterId(ch);

        return id> 0 && !getPlayer(id).isDead();
    }

    public void squareRun(){
        List<Character> moveOrder = MoveOrder.getOrder(edition, dayPeriod);

        for(Character ch: moveOrder){
            if(haveLivingCharacter(ch)){
                String methodName = "do" + ch.name();
                try {
                    Method method = TownSquare.class.getDeclaredMethod(methodName);
                    method.invoke(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void doSpy() {
        System.out.println("间谍看魔典");
    }

    private void doButler() {
        int id = 0;
        while (true) {
            System.out.println("输入主人的id：");
            id = sc.nextInt();

            if(id >0 && id <= playerNum){
                break;
            }
        }
        Player player = getPlayer(id);
        if(player != null){
            player.setDrunked(true);
            player.setDrunkEndTime(dayPeriod + 2);
        }
        System.out.println("管家:选择了XX作为主人");
    }

    private void doFortuenTeller() {
        String text;
        int n1, n2;

        while (true) {
            System.out.println("输入要查验的两个不同id， 以逗号分隔：");
            text = sc.next();
            if (text.contains(",")|| text.contains("，")) {
                String[] strArray = text.split("[,，]");
                if (strArray.length >= 2) {
                    n1 = Integer.parseInt(strArray[0]);
                    n2 = Integer.parseInt(strArray[1]);
                    if (n1 > 0 && n1 <= playerNum &&
                            n2 > 0 && n2 <= playerNum) {
                        break;
                    }
                }
            }
        }

        boolean haveDemon = false;
        if (isDemonOrEnemy(n1) || isDemonOrEnemy(n2)) {
            haveDemon = true;
        }

        Player player = getPlayerByCharacter(Character.FortuenTeller);
        if(player.isDrunked()){
            if(RandomUtil.randomDouble() <= 0.9){
                haveDemon = !haveDemon;
            }
        }
        if(haveDemon){
            System.out.println("占卜师："+n1+"和"+n2+"中 有恶魔");
        } else {
            System.out.println("占卜师："+n1+"和"+n2+"中没有恶魔");
        }
    }

    private void doEmpath() {
        int id = getCharacterId(Character.Empath);
        int temp = id;
        Player p1, p2;
        while(true){
            temp++;
            if(temp >playerNum){temp = 1;}
            p1 = getPlayer(temp);
            if(!p1.isDead()){
                break;
            }
        }
        temp = id;
        while(true){
            temp--;
            if(temp <1){temp = playerNum;}
            p2 = getPlayer(temp);
            if(!p2.isDead()){
                break;
            }
        }

        Player player = getPlayer(id);
        int badGuyNum = 0;
        int bad = 0;
        if (p1 == p2) {
            if (isBad(p1.getCharacter())) {
                bad = 1;
            }
        } else {
            if (isBad(p1.getCharacter())) {
                bad++;
            }
            if (isBad(p2.getCharacter())) {
                bad++;
            }
        }

        if(player.isDrunked() && RandomUtil.randomDouble() <= 0.9){
            bad = getRandomIntExcludeSpe(0,2, bad);
        }
        System.out.println("神使领座有" + bad + "个坏人");

    }

    private int getRandomIntExcludeSpe(int start, int end, int exclude){
        List list = Lists.newArrayList();
        for (int i = start; i <= end; i++) {
            if (i != exclude) {
                list.add(i);
            }
        }
        return (int) list.get(RandomUtil.randomInt(list.size()));
    }

    private boolean isBad(Character character){
        if(character == Character.Spy){
            return false;
        }
        if(character.getType() == MINIONS.getId() || character.getType() == DEMONS.getId()){
            return true;
        }
        if(character == Character.Recluse){
            return RandomUtil.randomInt(2) > 0;
        }
        return false;
    }

    /**
     * 占卜师，占卜是否有恶魔或者宿敌
     */
    private boolean isDemonOrEnemy(int id){
        Player player = getPlayer(id);
        if(player.getCharacter().getType() == DEMONS.getId() || player.isEnemyOfFortuenTeller()){
            return true;
        }
        return false;
    }

    private void doChef() {
        int between = 0;
        for(int i=1;i<playerNum;i++){
           if(isBad(getPlayer(i).getCharacter()) && isBad(getPlayer(i+1).getCharacter())){
               between ++;
           }
        }
        if(isBad(getPlayer(playerNum).getCharacter()) && isBad(getPlayer(1).getCharacter())){
            between ++;
        }

        int maxBad = 0;
        for(Player p : playerMap.values()){
            if(isBad(p.getCharacter()) || p.getCharacter() == Character.Recluse){
                maxBad++;
            }
        }

        if (getPlayerByCharacter(Character.Chef).isDrunked() && RandomUtil.randomDouble() <= 0.9) {
            int maxBetween = maxBad - 1;
            between = getRandomIntExcludeSpe(0, maxBetween, between);
        }
        System.out.println("厨师：本局有"+between+"个坏人为相邻");
    }

    private void doInvestigator() {
        System.out.print("调查员：");
        Player player = getPlayerByCharacter(Character.Investigator);
        doCommonFirstNightCheck(player, MINIONS, false);
    }

    private void doLibrarian() {
        System.out.print("图书管理员：");
        Player player = getPlayerByCharacter(Character.Librarian);
        doCommonFirstNightCheck(player, OUTSIDERS, true);
    }


    private void doWasherWoman() {
        System.out.print("洗衣妇：");
        Player player = getPlayerByCharacter(Character.WasherWoman);
        doCommonFirstNightCheck(player, TOWNS_FOLD, false);
    }
    private void doCommonFirstNightCheck(Player p, CharacterType type, boolean checkZero){
        ArrayList<Player> list = Lists.newArrayList();
        for(Player player : playerMap.values()){
            if(player.getCharacter().getType() == type.getId()){
                if(type==TOWNS_FOLD && player.getCharacter()==Character.WasherWoman){
                    continue;
                }
                list.add(player);
            }
        }
        if (p.isDrunked() && RandomUtil.randomDouble() <= 0.9) {
            if (checkZero && list.size() > 0 && RandomUtil.randomDouble()< 0.5) {
                System.out.println("本局没有" + type.getName());
                return;
            }else{
                List<Character> chList = Character.getCharacters(edition, type.getId());
                Character ch = chList.get(RandomUtil.randomInt(chList.size()));
                List<Player> playerList = Lists.newArrayList(playerMap.values());
                playerList.remove(p);
                Player r1 = playerList.remove(RandomUtil.randomInt(playerList.size()));
                Player r2 = playerList.remove(RandomUtil.randomInt(playerList.size()));
                System.out.println(r1.getId() + "," + r2.getId() + " 中有一位是" + ch.getName());
            }

        }else{
            if (checkZero) {
                if (list.size() == 0) {
                    System.out.println("本局没有" + type.getName());
                    return;
                }
            }

            Player r1 = list.remove(RandomUtil.randomInt(list.size()));
            list.clear();

            Map map = Maps.newHashMap(playerMap);
            map.remove(r1.getId());
            list.addAll(map.values());
            Player r2 = list.remove(RandomUtil.randomInt(list.size()));
            System.out.println(r1.getId() + "," + r2.getId() + " 中有一位是" + r1.getCharacter().getName());
        }
    }



    public void doPoisoner(){
        int poisonId = 0;
        while (true) {
            System.out.println("投毒者：输入要毒的id：");
            poisonId = sc.nextInt();
            if(poisonId >0 && poisonId <= playerNum){break;}
        }
        Player player = getPlayer(poisonId);
        if(player != null){
            player.setDrunked(true);
            player.setDrunkEndTime(dayPeriod + 2);
        }
    }

    @Override
    public void run() {

    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public void setPlayerNum(int playerNum) {
        this.playerNum = playerNum;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public void setPlayerMap(Map<Integer, Player> playerMap) {
        this.playerMap = playerMap;
    }

    public int getDayPeriod() {
        return dayPeriod;
    }

    public void setDayPeriod(int dayPeriod) {
        this.dayPeriod = dayPeriod;
    }

    public static Scanner getSc() {
        return sc;
    }

    public static void setSc(Scanner sc) {
        TownSquare.sc = sc;
    }
}
