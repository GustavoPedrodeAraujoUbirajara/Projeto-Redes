package Game;

public class Player {
    private String name;
    private int life;
    private boolean alive;

    public Player(String name) {
        this.name = name;
        this.life = 3;
        this.alive = true;
    }

    public String getName() {
        return name;
    }

    public int getLife() {
        return life;
    }

    public boolean isAlive() {
        return alive;
    }

    public void loseLife() {
        life--;
        if (life <= 0) {
            alive = false;
        }
    }
    

    public void kill() {
    	this.alive = false;
    }
}