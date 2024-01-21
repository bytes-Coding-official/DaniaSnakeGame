package snake.model;


public class Essen {
    private Position essenPosition;

    public Essen(Position position) {
        this.essenPosition = position;
    }

    public Position getEssenPosition() {
        return essenPosition;
    }
    public void setEssenPosition(Position position){
        this.essenPosition= position;
    }

    @Override
    public String toString() {
        return "Essen{" +
                "essenPosition=" + essenPosition +
                '}';
    }
}
