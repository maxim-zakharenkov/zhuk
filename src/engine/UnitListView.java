package engine;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.font.LineMetrics;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

/**
 * @author Max
 */
@SuppressWarnings("serial")
public class UnitListView extends JComponent {

    private Engine engine;

    public UnitListView(Engine engine) {
        this.engine = engine;
    }

    @Override
    public Dimension getPreferredSize() {
        int width = 0;
        int maxHeight = 0;
        for(Unit unit : engine.unitArray) {
            width += unit.image.getWidth() + this.getGraphics().getFontMetrics().stringWidth(unit.moveEvaluator.getName()) + 5;
            maxHeight = Math.max(maxHeight, unit.image.getHeight());
        }
        return new Dimension(width, maxHeight);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        
        int x = 0;
        int y = 0; 
        for(Unit unit : engine.unitArray) {
            g.drawImage(unit.image, x, y, this);
            g.drawString(unit.moveEvaluator.getName(), x + unit.image.getWidth() + 2, unit.image.getHeight());
            x += unit.image.getWidth() + g.getFontMetrics().stringWidth(unit.moveEvaluator.getName()) + 5;
        }
    }
}
