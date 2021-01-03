public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public String toString(){
        switch(this){
            case UP :
                return "UP";
            case DOWN :
                return "DOWN";
            case LEFT :
                return "LEFT";
            case RIGHT:
                return "RIGHT";
        }
        return null;
    }
}
