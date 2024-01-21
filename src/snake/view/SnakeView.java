package snake.view;

import processing.core.PApplet;
import processing.core.PImage;
import snake.controller.IController;


public class SnakeView extends PApplet implements IView {


    private IController controller;

    private PImage startScreen;
    private PImage gameOverScreen;
    private PImage snakeKopf;
    private PImage herz;
    private PImage cookies;
    private float titleAnimation;

    public SnakeView() {
    }

    public void setController(IController controller) {
        this.controller = controller;
    }

    @Override
    public void settings() {
        size(900, 600);
    }

    @Override
    public void setup() {
        // controller.GameStateStart();
        startScreen = loadImage("images/GameStart.jpeg");
        gameOverScreen = loadImage("images/GameOver.jpeg");
        snakeKopf = loadImage("images/snakeHead.png");
        cookies = loadImage("images/cookies.png");
        herz = loadImage("images/gradient_heart.jpg");
        if (herz == null) {
            println("Fehler beim Laden des Herz-Bildes!");
        }
    }

    @Override
    public void draw() {
        frameRate(5);
        background(255);
        controller.nextFrame();
    }

    @Override
    public void drawGameStart() {
        imageMode(CORNER);
        image(startScreen, 0, 0, width, height);
        // Animation für den Titel
        float titleX = (float) width / 2;
        float titleY = (float) height / 8;

        // Farbverlauf für den Text
        int color1 = color(148, 0, 211); // Lila

        int color3 = color(255, 0, 255); // Rosa
        int color4 = color(255, 255, 0); // Gelb
        int color2 = color(0, 0, 255);   // Blau

        // Farbmischung basierend auf der Sinus-Welle
        int mixedColor = lerpColor(lerpColor(color1, color2, titleAnimation),
                lerpColor(color3, color4, titleAnimation),
                abs(sin(titleAnimation)));

        fill(mixedColor);
        textSize(100);
        textAlign(CENTER, CENTER);
        text("Snake Game", titleX, titleY);
        fill(255);
        textSize(40);
        textAlign(CENTER, CENTER);
        text("Press the space key to start the game", (float) width / 2, height - 50);

        titleAnimation += 0.02F;

    }

    public void drawEssen(int x, int y) {
        image(cookies, x, y, 40, 40);
    }

    public void drawSnakeKopf(int x, int y) {
        image(snakeKopf, x, y, 40, 40);
    }


    public void drawSnakeKoerper(int x, int y) {
        fill(0, 255, 0);
        stroke(0, 128, 0);
        int durchmesser = 20;
        circle(x, y, durchmesser);
    }


    @Override
    public void drawGameOver() {
        imageMode(CORNER);
        image(gameOverScreen, 0, 0, width, height);
        float titleX = (float) width / 2;
        float titleY = (float) height / 8;


        fill(0);
        textSize(100);
        textAlign(CENTER, CENTER);
        text("Game Over", titleX, titleY);
        fill(255);
        textSize(20);
        textAlign(CENTER, CENTER);
        text("Click anywhere to start a new game.", (float) width / 2, height - 20);

    }

    public void mouseClicked() {
        controller.mouseClicked(mouseX, mouseY);
    }


    @Override
    public void checkForStartButton() {
        if (keyPressed && key == ' ') {
            controller.GameStateStart();
        }

    }


    public void drawLeiste(int leben) {
        int herztBreite = 30;
        int abstand = 10;
        for (int i = 0; i < leben; i++) {
            float heartX = 10 + i * (herztBreite + abstand);  // Anpassen der X-Position jedes Herzens
            float heartY = 10;  // Anpassen der Y-Position jedes Herzens auf die obere Leiste

            image(herz, heartX, heartY, herztBreite, herztBreite);
        }
    }


    public void keyPressed() {
        if (keyCode == ENTER) controller.GameStateStart();
        if (keyCode == RIGHT)
            controller.handelInput(2);
        if (keyCode == LEFT)
            controller.handelInput(1);
        if (keyCode == UP)
            controller.handelInput(3);
        if (keyCode == DOWN)
            controller.handelInput(4);

    }
}
