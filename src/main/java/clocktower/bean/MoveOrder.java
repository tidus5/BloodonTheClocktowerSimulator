package clocktower.bean;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

import static clocktower.bean.Character.*;

public class MoveOrder {

    public static Map<Integer, Map<Integer, List<Character>>> order = Maps.newHashMap();

    public static List<Character> troubleBrewingOrder1;
    public static List<Character> troubleBrewingOrder2;
    public static Map<Integer, List<Character>> troubleBrewingOrderMap = Maps.newHashMap();

    static {
        troubleBrewingOrder1 = Lists.newArrayList(
                Poisoner,
                WasherWoman,
                Librarian,
                Investigator,
                Chef,
                Empath,
                FortuenTeller,
                Butler,
                Spy
                );
        troubleBrewingOrder2 = Lists.newArrayList(
                Poisoner,
                Monk,
                ScarletWoman,
                Imp,
                Ravenkeeper,
                Empath,
                FortuenTeller,
                Butler,
                Undertaker,
                Spy);

        troubleBrewingOrderMap.put(0, troubleBrewingOrder1);
        troubleBrewingOrderMap.put(1, troubleBrewingOrder2);
        order.put(Constants.TROUBLE_BREWING, troubleBrewingOrderMap);

    }

    public static List<Character> getOrder(int edition, int dayPeriod){
        if (dayPeriod == 0) {
            return order.get(edition).get(0);
        } else {
            return order.get(edition).get(1);
        }
    }


    public static Map<Integer, Map<Integer, List<Character>>> getOrder() {
        return order;
    }

    public static void setOrder(Map<Integer, Map<Integer, List<Character>>> order) {
        MoveOrder.order = order;
    }

    public static List<Character> getTroubleBrewingOrder1() {
        return troubleBrewingOrder1;
    }

    public static void setTroubleBrewingOrder1(List<Character> troubleBrewingOrder1) {
        MoveOrder.troubleBrewingOrder1 = troubleBrewingOrder1;
    }

    public static List<Character> getTroubleBrewingOrder2() {
        return troubleBrewingOrder2;
    }

    public static void setTroubleBrewingOrder2(List<Character> troubleBrewingOrder2) {
        MoveOrder.troubleBrewingOrder2 = troubleBrewingOrder2;
    }

    public static Map<Integer, List<Character>> getTroubleBrewingOrderMap() {
        return troubleBrewingOrderMap;
    }

    public static void setTroubleBrewingOrderMap(Map<Integer, List<Character>> troubleBrewingOrderMap) {
        MoveOrder.troubleBrewingOrderMap = troubleBrewingOrderMap;
    }
}
