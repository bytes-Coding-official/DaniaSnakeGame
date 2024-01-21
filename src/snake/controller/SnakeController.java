package snake.controller;


import snake.model.GameModel;
import snake.model.LebensHerze;
import snake.model.Richtung;
import snake.model.SpielZustand;
import snake.view.IView;

import javax.swing.*;
import java.util.LinkedList;


public class SnakeController implements IController {


    private GameModel model;
    private IView view;


    public void setModel(GameModel model) {
        this.model = model;
    }

    public void setView(IView view) {
        this.view = view;
    }

    private static boolean displayPopUp = false;

    public void nextFrame() {
        switch (model.getZustand()) {
            case SpielZustand.START -> {
                view.drawGameStart();
                view.checkForStartButton();
            }
            case SpielZustand.PLAYING -> {
                displayPopUp = true;
                //view.drawSnake(model.getSnake().getSnakeX(), model.getSnake().getSnakeY());
                drawSnake();
                view.drawEssen(model.getEssen().getEssenPosition().getX(), model.getEssen().getEssenPosition().getY());
                drawHerzen(model.getHerzen());
                model.verwaltung();
            }
            case GAME_OVER -> {
                //OPEN A POPUP that sais the MAX_SIZE as a streak displayed using swing
                if (displayPopUp) {
                    displayPopUp = false;
                    JOptionPane.showMessageDialog(null, "Your streak was: " + GameModel.MAX_SIZE);
                }

                //MAX_SIZE = GameModel.MAX_SIZE;


                view.drawGameOver();
                view.checkForStartButton();


            }
            default -> throw new IllegalStateException("Unerwarteter Wert: " + model.getZustand());
        }
    }


    @Override
    public void GameStateStart() {
        model.setZustand(SpielZustand.PLAYING);
    }


    public void handelInput(int input) {
        if (input == 1 && model.getRichtung() != Richtung.RIGHT)
            model.setRichtung(Richtung.LEFT);
        else if (input == 2 && model.getRichtung() != Richtung.LEFT)
            model.setRichtung(Richtung.RIGHT);
        else if (input == 3 && model.getRichtung() != Richtung.DOWN)
            model.setRichtung(Richtung.UP);
        else if (input == 4 && model.getRichtung() != Richtung.UP)
            model.setRichtung(Richtung.DOWN);
    }


    private void drawHerzen(LinkedList<LebensHerze> herzen) {
        for (LebensHerze herz : herzen) {
            view.drawLeiste(model.getHerzen().size());
        }
    }

    private void drawSnake() {
        var snake = model.getSnake();
        view.drawSnakeKoerper(snake.getBodyparts().getFirst().getX(), snake.getBodyparts().getFirst().getY());
        for (int i = 1; i < snake.getBodyparts().size(); i++) {
            var bodypart = snake.getBodyparts().get(i);
            view.drawSnakeKoerper(bodypart.getX(), bodypart.getY());
        }
    }

    public void mouseClicked(int mouseX, int mouseY) {
        if (model.gameOver()) {
            model.starteNeuesSpiel();
            GameStateStart();
        }
    }

}
