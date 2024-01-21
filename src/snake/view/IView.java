package snake.view;


public interface IView {
    void drawGameStart();



    void drawGameOver();



    void checkForStartButton();


    void drawLeiste(int leben);



    void drawEssen(int x, int y);

    void drawSnakeKopf(int x , int y);

    void drawSnakeKoerper(int x , int y);

}
