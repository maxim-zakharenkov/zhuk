package engine;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

import spi.IUnitMoveEvaluator;

/**
 * @author Max
 */
@SuppressWarnings("serial")
public class View extends JComponent {
    
    private Color lineColor;
    private Color coinColor;
    private Engine engine;


    public View(Engine engine) {
        this.engine = engine;
        setBackground(Color.LIGHT_GRAY);
        this.lineColor = Color.BLACK;
        this.coinColor = Color.RED;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        final int cellWidth  = getWidth()/engine.field.length;
        final int cellHeight  = getHeight()/engine.field[0].length;
        
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        
        g.setColor(lineColor);
        g.drawRect(0, 0, getWidth() -1, getHeight() -1);
        
        // vertical lines
        for(int x = 0; x < engine.field.length; x ++) {
            int lineX = cellWidth * x;
            g.drawLine(lineX, 0, lineX, getHeight());
        }
        
        // horizontal lines
        for(int y = 0; y < engine.field[0].length; y ++) {
            int lineY = cellHeight * y;
            g.drawLine(0, lineY, getWidth(), lineY);
        }
        
        // draw coins
        g.setColor(coinColor);
        for(int x = 0; x < engine.field.length; x++) {
            for(int y = 0; y < engine.field[0].length; y ++) {
                if(engine.field[x][y] == IUnitMoveEvaluator.COIN) {
                    g.drawImage(Resources.cherryImage, x*cellWidth + (cellWidth - Resources.cherryImage.getWidth())/2, y*cellHeight + (cellWidth - Resources.cherryImage.getHeight())/2, this);
//                    g.fillOval(x*cellWidth + cellWidth/4, y*cellHeight + cellHeight/4, cellWidth/2, cellHeight/2);
                }
            }
        }
        
        // draw units
        for(Unit unit : engine.unitArray) {
//            g.setColor(unit.color);
//            g.fillRect(unit.position.x*cellWidth + cellWidth/4, unit.position.y*cellHeight + cellHeight/4, cellWidth/2, cellHeight/2);
            g.drawImage(unit.image, unit.position.x*cellWidth + (cellWidth - unit.image.getWidth())/2, unit.position.y*cellHeight + (cellWidth - unit.image.getHeight())/2, this);
            g.setColor(lineColor);
            g.drawString(String.valueOf(unit.coinsCollected), unit.position.x*cellWidth + 5, unit.position.y*cellHeight + 15);
        }
    }
}

