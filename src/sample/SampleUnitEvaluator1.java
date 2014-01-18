package sample;

import spi.IUnitMoveEvaluator;
import spi.Position;

/**
 * @author Max
 */
public class SampleUnitEvaluator1 implements IUnitMoveEvaluator {
    
    public String getName() {
        return "Mini";
    }

    @Override
    public Position evaluate(int[][] field, int width, int height, Position yourPos, Position[] opponentPositionArray) {
        
        int minDistance = width + height;
        Position destinationCandidate = null;
        for(int x = 0; x < width; x ++) {
            for(int y = 0; y < height; y ++) {
                if(field[x][y] == COIN) {
                    int distance = Math.abs(x - yourPos.x) + Math.abs(y - yourPos.y);
                    if(distance < minDistance) {
                        minDistance = distance;
                        destinationCandidate = new Position(x, y);
                    }
                }
            }
        }
        
        if(destinationCandidate == null) {
            return yourPos;
        }
        
        final int aDx = Math.abs(destinationCandidate.x - yourPos.x);
        final int aDy = Math.abs(destinationCandidate.y - yourPos.y);
        if(aDx > aDy) {
            int dx = new Integer(destinationCandidate.x).compareTo(yourPos.x);
            return new Position(yourPos.x + dx, yourPos.y);
        } else {
            int dy = new Integer(destinationCandidate.y).compareTo(yourPos.y);
            return new Position(yourPos.x, yourPos.y + dy);
        }
    }
}
