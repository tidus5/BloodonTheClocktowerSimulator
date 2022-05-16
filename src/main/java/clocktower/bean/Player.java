package clocktower.bean;

import com.google.common.collect.Sets;

import java.util.Set;

public class Player {
    private int id;
    private Character character;
    private Character drunkedMistaken;

    private boolean dead;
    private boolean isEnemyOfFortuenTeller;

    private boolean drunked;
    private int drunkEndTime;

    /** 玩家信息真实的可能性， 可能是恶魔伪装， 可能是好人伪装 */
    private int truthRate;
    private Set<Character> possibleCharacter = Sets.newHashSet();

    public Player(int id, Character character) {
        this.id = id;
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }

    public int getId() {
        return id;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Character getDrunkedMistaken() {
        return drunkedMistaken;
    }

    public void setDrunkedMistaken(Character drunkedMistaken) {
        this.drunkedMistaken = drunkedMistaken;
    }

    public boolean isEnemyOfFortuenTeller() {
        return isEnemyOfFortuenTeller;
    }

    public void setEnemyOfFortuenTeller(boolean enemyOfFortuenTeller) {
        isEnemyOfFortuenTeller = enemyOfFortuenTeller;
    }

    public boolean isDrunked() {
        return drunked;
    }

    public void setDrunked(boolean drunked) {
        this.drunked = drunked;
    }

    public int getDrunkEndTime() {
        return drunkEndTime;
    }

    public void setDrunkEndTime(int drunkEndTime) {
        this.drunkEndTime = drunkEndTime;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTruthRate() {
        return truthRate;
    }

    public void setTruthRate(int truthRate) {
        this.truthRate = truthRate;
    }

    public Set<Character> getPossibleCharacter() {
        return possibleCharacter;
    }

    public void setPossibleCharacter(Set<Character> possibleCharacter) {
        this.possibleCharacter = possibleCharacter;
    }


}
