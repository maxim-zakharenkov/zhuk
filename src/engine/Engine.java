package engine;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sample.SampleUnitEvaluator0;
import sample.SampleUnitEvaluator1;
import sample.SampleUnitEvaluator2;
import spi.IUnitMoveEvaluator;
import spi.Position;

/**
 * @author Max
 */
public class Engine {

    int moveNumber;
    Unit[] unitArray;
    int[][] field;
    boolean gameOver;
    GameResult gameResult;
    
    public Engine() {
        reset();
    }

    public void reset() {
        this.moveNumber = 0;
        this.unitArray = new Unit[2];
        this.unitArray[0] = new Unit("1", new Position(8, 8), Resources.greenBugImage, new SampleUnitEvaluator1());
        this.unitArray[1] = new Unit("2", new Position(0, 0), Resources.redBugImage, new SampleUnitEvaluator2());
        
        this.field = new int[9][9];
//        this.field[4][4] = IUnitMoveEvaluator.COIN;
//        this.field[3][3] = IUnitMoveEvaluator.COIN;
//        this.field[2][2] = IUnitMoveEvaluator.COIN;
//        this.field[5][5] = IUnitMoveEvaluator.COIN;
//        this.field[6][6] = IUnitMoveEvaluator.COIN;
//        this.field[5][3] = IUnitMoveEvaluator.COIN;
//        this.field[6][2] = IUnitMoveEvaluator.COIN;
//        this.field[3][5] = IUnitMoveEvaluator.COIN;
//        this.field[2][6] = IUnitMoveEvaluator.COIN;
        
        genRandomField(this.field);
        gameOver = false;
    }
    
    private void genRandomField(int[][] field) {
        for(int y = 0; y < field[0].length; y ++) {
            for(int x = 0; x < field.length - y - 1; x ++) {
            if(x == 0 && y == 0) {
                // skip initial positions
                continue;
            }
            
            if(Math.random() > 0.2) {
                continue;
            }
            
            field[x][y] = IUnitMoveEvaluator.COIN;
            field[field.length - y - 1][field[0].length - x - 1] = IUnitMoveEvaluator.COIN;
            }
        }
        
        // generate 3 random diagonal elements to make odd total count
        float n = 3.0f;
        int remaining = field.length;
        for(int x = 0; x < field.length; x ++) {
            if(Math.random() < n/remaining) {
                remaining --;
                n = n - 1;
                field[x][field[0].length - x - 1] = IUnitMoveEvaluator.COIN;
            } else {
                remaining --;
            }
        }
    }

    /**
     * returns false in case game is over
     */
    public boolean doMove() throws IllegalMoveException {
        
        if(gameOver) {
            return false;
        }
        
        moveNumber ++;
        
        Set<Unit> illegalMoveUnitArray = new HashSet<Unit>();
        
        // 1. evaluate next moves of all units
        for(int i = 0; i < unitArray.length ; i++) {
            int[][] fieldCopy = createCopy(field);
            Position[] opponentPosArray = createOpponentPosArray(i, unitArray);
            
            try {
                unitArray[i].moveCandidate = 
                        unitArray[i].moveEvaluator.evaluate(fieldCopy, fieldCopy.length, fieldCopy[0].length, unitArray[i].position, opponentPosArray);
            } catch(Throwable ex) {
                ex.printStackTrace();
                // exception inside moveEvaluator.evaluate
                illegalMoveUnitArray.add(unitArray[i]);
            }
        }

        // 2. check location correctness and collect all units performed illegal moves
        for(int i = 0; i < unitArray.length ; i++) {
            checkLocation(unitArray[i], illegalMoveUnitArray);
        }
        
        // 3. End game in case of violations
        if(!illegalMoveUnitArray.isEmpty()) {
            throw new IllegalMoveException(illegalMoveUnitArray);
        }
        
        // 4. detect collisions
        HashMap<Position, List<Unit>> collisionMap = new HashMap<Position, List<Unit>>();
        for(int i = 0; i < unitArray.length ; i++) {
            List<Unit> collidedList = collisionMap.get(unitArray[i].moveCandidate);
            if(collidedList == null) {
                collidedList = new ArrayList<Unit>();
                collisionMap.put(unitArray[i].moveCandidate, collidedList);
            } 
            collidedList.add(unitArray[i]);
        }
        
        boolean resetPositionDone = false;
        
        // 5. set initial positions to collided units
        for(List<Unit> collidedList : collisionMap.values()) {
            if(collidedList.size() > 1) {
                for(Unit curUnit : collidedList) {
                    curUnit.resetPositionToInitial();
                    resetPositionDone = true;
                }
            }
        }
        
        // 6. update positions to the rest of units and count collected coins
        for(int i = 0; i < unitArray.length ; i++) {
            if(unitArray[i].isMoveUnapplied()) {
                unitArray[i].applyMove(field);
            }
        }
        
        // 7. check for draw condition when bugs always reset each other
        // in the same way
        if(moveNumber >= field.length * field[0].length) {
            gameOver = true;
            return false;
        }
        return true;
    }

    private Position[] createOpponentPosArray(int excludePos, Unit[] unitArray) {
        Position[] resArray = new Position[unitArray.length - 1];
        int curIdx = 0;
        for(int i = 0; i < unitArray.length ; i++) {
            if(i != excludePos) {
                resArray[curIdx ++] = unitArray[i].position;
            }
        }
        return resArray;
    }

    private void checkLocation(Unit unit, Set<Unit> illegalMoveUnitArray) {
        if(unit.moveCandidate == null) {
            illegalMoveUnitArray.add(unit);
            return;
        }
        if(unit.moveCandidate.x < 0 || unit.moveCandidate.y < 0 || unit.moveCandidate.x >= field.length || unit.moveCandidate.y >= field[0].length) {
            illegalMoveUnitArray.add(unit);
            return;
        }
        if(Math.abs(unit.moveCandidate.x - unit.position.x) + Math.abs(unit.moveCandidate.y - unit.position.y) > 1) {
            illegalMoveUnitArray.add(unit);
            return;
        }
    }

    private int[][] createCopy(int[][] field) {
        int[][] copy = new int[field.length][field[0].length];
        for(int x = 0; x < field.length; x ++) {
            for(int y = 0; y < field[0].length; y++) {
                copy[x][y] = field[x][y];
            }
        }
        return copy;
    }
}
