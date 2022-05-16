package clocktower.bean;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static clocktower.bean.CharacterType.*;

public enum Character {

    /** 洗衣妇*/
    WasherWoman(1101, "洗衣妇"),
    /** 图书管理员
     * 1. 可能会 得知两个玩家中有隐士
     * 2. 可能会 得知两个玩家中有酒鬼
     * 3. 隐士被视为恶魔时， 可以给出 “没有外来者”， 或者“A,B里有隐士”
     * */
    Librarian(1102, "图书管理员"),
    /** 调查员
     * 1. 隐士会先 被说书人决定当成什么角色，然后再给调查员。 （所以可能隐士会视为爪牙）
     * 2. 可以得知，A，B中有间谍
     * 3. 爪牙是间谍，且视为村民时，  不建议和调查员并存。（调查员查不出来，废掉了）
     * */
    Investigator(1103, "调查员"),
    /** 厨师
     *
     * 隐士在每次判定，可以单独重新认定是否为善良
     * */
    Chef(1104, "厨师"),
    /** 共情者、神使*/
    Empath(1105, "神使"),
    /** 占卜师*/
    FortuenTeller(1106, "占卜师"),
    /** 送葬者 */
    Undertaker(1107, "送葬者"),
    /** 僧侣*/
    Monk(1108, "僧侣"),
    /** 守鸦人*/
    Ravenkeeper(1109, "守鸦人"),
    /** 圣女*/
    Virgin(1110, "圣女"),
    /** 杀手*/
    Slayer(1111, "杀手"),
    /** 士兵*/
    Soldier(1112, "士兵"),
    /** 镇长*/
    Mayor(1113, "镇长"),

    /** 管家*/
    Butler(1201, "管家"),
    /** 酒鬼*/
    Drunk(1202, "酒鬼"),
    /** 隐士*/
    Recluse(1203, "隐士"),
    /** 圣徒*/
    Saint(1204, "圣徒"),

    /** 投毒者*/
    Poisoner(1301, "投毒者(红)"),
    /** 间谍*/
    Spy(1302, "间谍(红)"),
    /** 红唇女郎*/
    ScarletWoman(1303, "红唇女郎(红)"),
    /** 男爵*/
    Baron(1304, "男爵(红)"),

    /** 小恶魔*/
    Imp(1401, "小恶魔(红)"),
    ;

    private int edition;
    private int type;
    private int index;
    private String name;

    private static Map<Integer, List<Character>> troubleBrewing = Maps.newHashMap();


    private static Map<Integer, Map<Integer, List<Character>>>  map = Maps.newHashMap();


    static {
        Map<Integer, List<Character>> troubleBrewingMap = Maps.newHashMap();
        for (Character character : values()) {
            if (character.edition == 1) {
                List<Character> list = troubleBrewingMap.get(character.type);
                if (list == null) {
                    troubleBrewingMap.put(character.type,list =  Lists.<Character>newArrayList());
                }
                list.add(character);
            }
        }

        for (Map.Entry<Integer, List<Character>> e : troubleBrewingMap.entrySet()) {
            List<Character> list = e.getValue();
            troubleBrewing.put(e.getKey(), Collections.unmodifiableList(list));
        }
        map.put(1, troubleBrewing);
    }

    Character(int i, String name) {
        edition = i / 1000;
        type = i / 100 % 10;
        index = i % 100;
        this.name = name;
    }

    public static List<Character> getCharacters(int edition, int type) {
        return map.get(edition).get(type);
    }

    public static List<Character> getTownFolks(int edition) {
        return map.get(edition).get(TOWNS_FOLD.getId());
    }

    public static List<Character> getOutsiders(int edition) {
        return map.get(edition).get(OUTSIDERS.getId());
    }

    public static List<Character> getMinions(int edition) {
        return map.get(edition).get(MINIONS.getId());
    }

    public static List<Character> getDemons(int edition) {
        return map.get(edition).get(DEMONS.getId());
    }

    public int getEdition() {
        return edition;
    }

    public int getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Map<Integer, List<Character>> getTroubleBrewing() {
        return troubleBrewing;
    }

    public static void setTroubleBrewing(Map<Integer, List<Character>> troubleBrewing) {
        Character.troubleBrewing = troubleBrewing;
    }

    public static Map<Integer, Map<Integer, List<Character>>> getMap() {
        return map;
    }

    public static void setMap(Map<Integer, Map<Integer, List<Character>>> map) {
        Character.map = map;
    }
}
