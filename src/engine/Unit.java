package engine;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import spi.IUnitMoveEvaluator;
import spi.Position;

/**
 * @author Max
 */
public class Unit {
    
    final String name;
    final Position initialPosition;
    final IUnitMoveEvaluator moveEvaluator;
    
    int coinsCollected;
    Position position;
    Position moveCandidate;
    public BufferedImage image;

    public Unit(String name, Position initialPosition, BufferedImage image, IUnitMoveEvaluator moveEvaluator) {
        super();
        this.name = name;
        this.initialPosition = initialPosition;
        this.position = initialPosition;
        this.image = image;
        this.moveEvaluator = moveEvaluator;
        this.moveCandidate = null;
        this.coinsCollected = 0;
    }

    public void resetPositionToInitial() {
        position = initialPosition;
        moveCandidate = null;
    }
    
    public void applyMove(int[][] field) {
        position = moveCandidate;
        moveCandidate = null;
        
        if(field[position.x][position.y] == IUnitMoveEvaluator.COIN) {
            field[position.x][position.y] = IUnitMoveEvaluator.EMPTY;
            coinsCollected ++;
            
            playMunch();
        }
    }

    public void playMunch()  {
//        try {
//            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new ByteArrayInputStream(Resources.munch));
//            Clip clip = AudioSystem.getClip();
//            clip.open(audioIn);
//            clip.start();
//        } catch(Exception ex) {
//            ex.printStackTrace();
//        }
    }
    
    public boolean isMoveUnapplied() {
        return moveCandidate != null;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
