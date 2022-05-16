package clocktower.bean;

import com.google.common.collect.Maps;

import java.util.Map;

import static clocktower.bean.CharacterType.*;

public enum Staff {

STAFF_5(5,3,0,1,1),
    STAFF_6(6,3,1,1,1),
    STAFF_7(7,5,0,1,1),
    STAFF_8(8,5,1,1,1),
    STAFF_9(9,5,2,1,1),
    STAFF_10(10,7,0,2,1),
    STAFF_11(11,7,1,2,1),
    STAFF_12(12,7,2,2,1),
    STAFF_13(13,9,0,3,1),
    STAFF_14(14,9,1,3,1),
    STAFF_15(15,9,2,3,1),
        ;

    static Map<Integer, Staff> map = Maps.newHashMap();
    static {
        for(Staff staff : values()){
            map.put(staff.players, staff);
        }
    }

    public static Staff getStaff(int player) {
        return map.get(player);
    }

    private int players;
    private int townsfolk;
    private int outersiders;
    private int minions;
    private int demons;
    Staff(int players, int townsfolk, int outersiders, int minions, int demons) {
        this.players = players;
        this.townsfolk = townsfolk;
        this.outersiders = outersiders;
        this.minions = minions;
        this.demons = demons;
    }

    public int getPlayers() {
        return players;
    }

    public int getTownsfolk() {
        return townsfolk;
    }

    public int getOutersiders() {
        return outersiders;
    }

    public int getMinions() {
        return minions;
    }

    public int getDemons() {
        return demons;
    }

    public int getNum(int type){
        switch (CharacterType.of(type)){
            case TOWNS_FOLD: return townsfolk;
            case OUTSIDERS: return outersiders;
            case MINIONS: return minions;
            case DEMONS: return demons;
        }
        return 0;
    }

    public static Map<Integer, Staff> getMap() {
        return map;
    }

    public static void setMap(Map<Integer, Staff> map) {
        Staff.map = map;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public void setTownsfolk(int townsfolk) {
        this.townsfolk = townsfolk;
    }

    public void setOutersiders(int outersiders) {
        this.outersiders = outersiders;
    }

    public void setMinions(int minions) {
        this.minions = minions;
    }

    public void setDemons(int demons) {
        this.demons = demons;
    }
}
