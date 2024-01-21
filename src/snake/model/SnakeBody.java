package snake.model;

import snake.model.Position;

public class SnakeBody  extends Position{


    private Richtung richtung;

    public SnakeBody(int x, int y, Richtung richtung) {
        super(x, y);
        this.richtung = richtung;
    }

    public Richtung getRichtung() {
        return richtung;
    }

    public void setRichtung(Richtung richtung) {
        this.richtung = richtung;
    }

}
