package snake.controller;

    public interface IController {
        void nextFrame();


        //void GameStatePlaying();

        void GameStateStart();

        void handelInput(int a);

        void mouseClicked(int mouseX, int mouseY);


//        void handelInput(int kCode);
    }