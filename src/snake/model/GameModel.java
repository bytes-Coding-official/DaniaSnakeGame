package snake.model;


import java.util.LinkedList;
import java.util.Random;

public class GameModel {

    private SpielZustand zustand;

    public static int MAX_SIZE = 0;
    private static final boolean DEBUG = false;
    private final int widthSpielfeld = 900;
    private final int heightSpielfeld = 600;
    private Snake snake;
    private static final int KOLLISIONS_BEREICH = 20;
    private final LinkedList<LebensHerze> herzen;
    private Essen essen;

    private int result = 0;
    private int spielZeit = 60;
    private int leben = 3;
    //private int counter = leben;

    public GameModel() {
        this.snake = new Snake(100, 100, Richtung.RIGHT);
        this.essen = new Essen(null);
        generiereEssen();
        this.zustand = SpielZustand.START;
        this.herzen = new LinkedList<>();
        this.herzen.add(new LebensHerze(new Position(0, 0)));
        this.herzen.add(new LebensHerze(new Position(30, 0)));
        this.herzen.add(new LebensHerze(new Position(60, 0)));
    }

    public Snake getSnake() {
        return snake;
    }


    public void generiereEssen() {
        Random neuesEssen = new Random();
        int abstand = 40;
        int zeileGrenze = widthSpielfeld - abstand;
        int spalteGrenze = heightSpielfeld - abstand;

        int randomZeile = neuesEssen.nextInt(0, zeileGrenze);
        int randomSpalte = neuesEssen.nextInt(0, spalteGrenze);
        //Die Position das Essen zu zentrieren
        Position randomPosition = new Position(randomZeile, randomSpalte);
        essen.setEssenPosition(randomPosition);
    }


    //whenever the snake moves get that direction of that snake and move all bodyparts in that direction
    private void move() {
        var direction = snake.getBodyparts().getFirst().getRichtung();
        //snake bodyparts contains all bodyparts of the snake as well as the head


        //get last element
        //remove last element
        var last = snake.getBodyparts().removeLast();
        last.setRichtung(direction);
        var first = snake.getBodyparts().isEmpty() ? last : snake.getBodyparts().getFirst();
        //now add it to the front depending on the direction of the snake
        switch (direction) {
            case UP:
                last.setY(first.getY() - 20);
                last.setX(first.getX());
                break;
            case DOWN:
                last.setY(first.getY() + 20);
                last.setX(first.getX());
                break;
            case LEFT:
                last.setX(first.getX() - 20);
                last.setY(first.getY());
                break;
            case RIGHT:
                last.setX(first.getX() + 20);
                last.setY(first.getY());
                break;
        }
        //add the last element to the front of the list
        snake.getBodyparts().addFirst(last);


        //move the head of the snake


        if (DEBUG) {
            var stringbuilder = snakePrinter();
            System.out.println(stringbuilder);


        }
    }

    private StringBuilder snakePrinter() {
        var stringbuilder = new StringBuilder();
        var head = snake.getBodyparts().getFirst();
        stringbuilder.append("head: ");
        stringbuilder.append(head.getX());
        stringbuilder.append(" ");
        stringbuilder.append(head.getY());
        stringbuilder.append(" ");
        stringbuilder.append("bodyparts: ");
        for (var bodypart : snake.getBodyparts()) {
            stringbuilder.append(bodypart.getX());
            stringbuilder.append(" ");
            stringbuilder.append(bodypart.getY());
            stringbuilder.append(" ");
        }
        return stringbuilder;
    }


    public void uberprufeKollision() {
        if (wandCollision() || playerCollision()) {
            System.out.println("Kollision entdeckt");
            if (leben > 0) {
                snake = new Snake(100, 100, Richtung.RIGHT);
                reduziereCounter();
                aktuallisiereHerzen();
            }


        }


    }

    private void reduziereCounter() {
        //Das hat das Problem mit reduziereCounter(); und schnell das Spiel zu Ende gelöst , ABER DAMIT BIN ICH NICHT ZUFRIEDEN

        leben--;
        System.out.println("leben reduzieren um 1 also leben ist : " + " " + leben);


    }

    private void aktuallisiereHerzen() {
        if (!herzen.isEmpty()) {
            herzen.removeLast();
        }
    }

    public boolean wandCollision() {
        var snake = this.snake.getBodyparts().getFirst();
        return snake.getX() < 0 ||
                snake.getX() >= widthSpielfeld ||
                snake.getY() < 0 ||
                snake.getY() >= heightSpielfeld;

    }


    public boolean playerCollision() {
        var bodyparts = snake.getBodyparts();
        var head = bodyparts.getFirst();
        for (int i = 1; i < bodyparts.size(); i++) {
            var current = bodyparts.get(i);
            if (head.getX() == current.getX() && head.getY() == current.getY()) {
                System.out.println("Kollision mit dem Schwanz");
                return true;
            }
        }
        return false;
    }

    public void checkEssen() {
        if (kollidiertMitEssen(essen)) {
            System.out.println("Kollision mit Essen erkannt!");
            extend();
            System.out.println("Nach dem Essen Schlange verlängern");
            // essen = null;

            // Erzeuge ein neues Essen
            essen = new Essen(new Position(0, 0));
            generiereEssen();

            result++;


        }


    }


    public void extend() {
        System.out.println("Schlange verlängern");
        // Berechne die Position für das neue Segment der Schlange
        var direction = snake.getBodyparts().getFirst().getRichtung();
        var bodyparts = snake.getBodyparts();
        var last = bodyparts.getLast();
        var x = last.getX();
        var y = last.getY();


        //add based on prev. direction and position of last element the element in bodyparts
        switch (direction) {
            case UP:
                bodyparts.add(new SnakeBody(x, y + 20, last.getRichtung()));
                break;
            case DOWN:
                bodyparts.add(new SnakeBody(x, y - 20, last.getRichtung()));
                break;
            case LEFT:
                bodyparts.add(new SnakeBody(x + 20, y, last.getRichtung()));
                break;
            case RIGHT:
                bodyparts.add(new SnakeBody(x - 20, y, last.getRichtung()));
                break;
        }
        System.out.println("Bodyparts: " + bodyparts.size());

        if (bodyparts.size() - 1 > MAX_SIZE) {
            MAX_SIZE = bodyparts.size() - 1;
        }
    }

    private boolean kollidiertMitEssen(Essen essen) {
        return Math.abs(snake.getBodyparts().getFirst().getX() - essen.getEssenPosition().getX()) <= KOLLISIONS_BEREICH
                && Math.abs(snake.getBodyparts().getFirst().getY() - essen.getEssenPosition().getY()) <= KOLLISIONS_BEREICH;
    }


    public SpielZustand getZustand() {
        return zustand;
    }


    public void setZustand(SpielZustand zustand) {
        this.zustand = zustand;
    }

    public boolean gameOver() {
        if (leben == 0) {
            System.out.println("Spieler hat verloren :(");

            return true;

        } else if (gewonnen()) {
            System.out.println("Du hast gewonnen (;");
        }

        return false;
    }

    public boolean gewonnen() {
        //private int essenCounter = 0;
        int gewinnEssen = 5;
        if (result == gewinnEssen && spielZeit > 0) {
            zustand = SpielZustand.GAME_OVER;
            
            return true;
        }
        return false;
    }


    public void verwaltung() {
        spielZeit--;
        if (gameOver()) {
            zustand = SpielZustand.GAME_OVER;
            // starteNeuesSpiel();
        }
        switch (zustand) {
            case PLAYING:
                move();
                checkEssen();
                uberprufeKollision();
                break;
            case GAME_OVER:
                System.out.println("Du hast verloren");
                break;
            case MENU:
                System.out.println("Du kannst jetzt spielen");
        }


    }

    public void starteNeuesSpiel() {
        this.snake = new Snake(100, 100, Richtung.RIGHT);
        Position essenPosition = new Position(widthSpielfeld / 2, heightSpielfeld / 2);
        this.essen = new Essen(essenPosition);
        this.zustand = SpielZustand.START;
        this.herzen.clear();
        this.herzen.add(new LebensHerze(new Position(0, 0)));
        this.herzen.add(new LebensHerze(new Position(30, 0)));
        this.herzen.add(new LebensHerze(new Position(60, 0)));
        this.leben = 3;
        this.result = 0;
        this.spielZeit = 1000;
    }


    public Essen getEssen() {
        return essen;
    }

    public LinkedList<LebensHerze> getHerzen() {
        return herzen;
    }

    public Richtung getRichtung() {
        return snake.getBodyparts().getFirst().getRichtung();
    }

    public void setRichtung(Richtung r) {
        snake.getBodyparts().getFirst().setRichtung(r);
    }

}
