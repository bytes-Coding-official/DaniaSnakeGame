package snake;


import snake.controller.SnakeController;
import snake.model.GameModel;
import snake.view.SnakeView;

import processing.core.PApplet;

public final class Main {

    public static void main(String[] args) {


        var model = new GameModel();
        var controller = new SnakeController();
        var view = new SnakeView();
        controller.setModel(model);
        controller.setView(view);
        view.setController(controller);
        PApplet.runSketch(new String[]{"snake.view.SnakeView"}, view);
    }
}
