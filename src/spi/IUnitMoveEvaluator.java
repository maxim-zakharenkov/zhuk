package spi;



public interface IUnitMoveEvaluator {
    
    public static final int EMPTY = 0;
    public static final int COIN = 1;
    
    public Position evaluate(int[][] field, int width, int height, Position yourPos, Position[] opponentPositionArray);
    
    public String getName();
}
