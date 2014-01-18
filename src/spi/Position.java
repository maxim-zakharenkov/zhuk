package spi;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author Max
 */
public class Position {
    
    public final int x;
    public final int y;
    
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int distance(Position position) {
        return Math.abs(x - position.x) + Math.abs(y - position.y);
    }
    
    @Override
    public boolean equals(Object obj) {
        Position locObj = (Position)obj;
        return locObj != null && locObj.x == x && locObj.y == y;
    }

    @Override
    public int hashCode() {
        return x<<16 + y; 
    }
    
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public int distance(int x2, int y2) {
        return Math.abs(x - x2) + Math.abs(y - y2);
    }

    public int distanceX(int x2) {
        return  Math.abs(x - x2);
    }
    
    public int distanceY(int y2) {
        return  Math.abs(y - y2);
    }
    
    public List<Position> minDistanceList(List<Position> list) {
        int minDistance = Integer.MAX_VALUE;
        List<Position> minDistanceList = new ArrayList<Position>();
        for(Position curPos : list) {
            int curDistance = this.distance(curPos);
            if(minDistance > curDistance) {
                minDistance = curDistance;
                minDistanceList.clear();
                minDistanceList.add(curPos);
            } else if(minDistance == curDistance) {
                minDistanceList.add(curPos);
            }
        }
        return minDistanceList;
    }
    
    public List<Position> maxDistanceList(List<Position> list) {
        int maxDistance = 0;
        List<Position> maxDistanceList = new ArrayList<Position>();
        for(Position curPos : list) {
            int curDistance = this.distance(curPos);
            if(maxDistance < curDistance) {
                maxDistance = curDistance;
                maxDistanceList.clear();
                maxDistanceList.add(curPos);
            } else if(maxDistance == curDistance) {
                maxDistanceList.add(curPos);
            }
        }
        return maxDistanceList;
    }
}
