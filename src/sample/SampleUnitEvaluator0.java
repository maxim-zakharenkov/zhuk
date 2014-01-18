package sample;

import spi.IUnitMoveEvaluator;
import spi.Position;

/**
 * @author Max
 */
public class SampleUnitEvaluator0 implements IUnitMoveEvaluator {

    @Override
    public Position evaluate(int[][] field, int width, int height, Position yourPos, Position[] opponentPositionArray) {
        
        Position destinationCandidate = null;
        for(int x = 0; x < width && destinationCandidate == null; x ++) {
            for(int y = 0; y < height && destinationCandidate == null; y ++) {
                if(field[x][y] == COIN) {
                    destinationCandidate = new Position(x, y);
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

    @Override
    public String getName() {
        return "Primit";
    }
}
