package snake.model;

public class LebensHerze {


    private final Position herzPosition;



    public LebensHerze(Position herzPosition){
        this.herzPosition = herzPosition;

    }


    public Position getHerz(){
        return herzPosition;
    }

    @Override
    public String toString() {
        return "LebensHerze{" +
                "herzPosition=" + herzPosition +
                '}';
    }


}


