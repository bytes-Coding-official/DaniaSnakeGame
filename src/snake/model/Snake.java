package snake.model;

import java.util.ArrayList;

public class Snake {


    private final ArrayList<SnakeBody> bodyparts;

    public Snake(int x, int y, Richtung richtung) {
        var body = new SnakeBody(x, y, richtung);
        bodyparts = new ArrayList<>();
        bodyparts.add(body);
    }



    public ArrayList<SnakeBody> getBodyparts() {
        return bodyparts;
    }

    @Override
    public String toString() {
        var stringbuilder = new StringBuilder();
        stringbuilder.append("Snake position:").append(getBodyparts().getFirst().getX()).append(" : ").append(getBodyparts().getFirst().getY()).append("\n");
        stringbuilder.append("Richtung: ").append(getBodyparts().getFirst().getRichtung());

        stringbuilder.append("\nParts:");
        for (var part : getBodyparts()) {
            stringbuilder.append(part.toString()).append(", ");
        }
        return stringbuilder.toString();
    }
}
