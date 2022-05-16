package clocktower.bean;

public enum CharacterType {
    TOWNS_FOLD(1,"村民"),
    OUTSIDERS(2,"外来者"),
    MINIONS(3,"爪牙"),
    DEMONS(4,"恶魔");

   private int id;
   private String name;

   public static  CharacterType of(int id){
      switch (id){
          case 1:return TOWNS_FOLD;
          case 2:return OUTSIDERS;
          case 3:return MINIONS;
          case 4: return DEMONS;
      }
      return null;
   }

    CharacterType(int i, String s) {
        id = i;
        name = s;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
