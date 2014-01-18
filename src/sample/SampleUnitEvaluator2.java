package sample;

import java.util.ArrayList;
import java.util.List;

import spi.IUnitMoveEvaluator;
import spi.Position;

/**
 * @author Max
 */
public class SampleUnitEvaluator2 implements IUnitMoveEvaluator {

    @Override
    public Position evaluate(int[][] field, int width, int height, final Position yourPos, Position[] opponentPositionArray) {
        
        Position opponentPos = opponentPositionArray[0];
        
        
        List<Position> allCoins = allCoinList(field, width, height);
        if(allCoins.isEmpty()) {
            // there are no coins
            return yourPos;
        }
        
        List<Position> minDistanceList = yourPos.minDistanceList(allCoins);
        List<Position> maxDistToOpponent = opponentPos.maxDistanceList(minDistanceList);
        
        Position destinationCandidate = maxDistToOpponent.get(0);
        
        final int aDx = destinationCandidate.distanceX(yourPos.x);
        final int aDy = destinationCandidate.distanceY(yourPos.y);
        if(aDx > aDy) {
            int dx = new Integer(destinationCandidate.x).compareTo(yourPos.x);
            return new Position(yourPos.x + dx, yourPos.y);
        } else {
            int dy = new Integer(destinationCandidate.y).compareTo(yourPos.y);
            return new Position(yourPos.x, yourPos.y + dy);
        }
    }

    public List<Position> allCoinList(int[][] field, int width, int height) {
        List<Position> allCoins = new ArrayList<Position>();
        for(int x = 0; x < width; x ++) {
            for(int y = 0; y < height; y ++) {
                if(field[x][y] == COIN) {
                    allCoins.add(new Position(x, y));
                }
            }
        }
        return allCoins;
    }

    @Override
    public String getName() {
        return "Intel 1";
    }
}
