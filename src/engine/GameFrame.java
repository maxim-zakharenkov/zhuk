package engine;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * @author Max
 */
@SuppressWarnings("serial")
public class GameFrame extends JFrame implements Runnable {

    private JButton btnStep;
    private JButton btnReset;
    private JButton btnRun;
    private Timer runTimer;

    public GameFrame() {
        
    }
    
    @Override
    public void run() {
        
        final Engine engine = new Engine();
        final View view = new View(engine);
        final UnitListView unitListView = new UnitListView(engine);
        unitListView.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        btnStep = new JButton();
        btnStep.setText("Step");
        
        btnRun = new JButton();
        btnRun.setText("Run");
        
        btnReset = new JButton();
        btnReset.setText("Reset");
        
        JPanel pnlControls = new JPanel();
        pnlControls.setLayout(new FlowLayout());
        pnlControls.add(unitListView);
        pnlControls.add(btnStep);
        pnlControls.add(btnRun);
        pnlControls.add(btnReset);
        
        JPanel pnlNorth = new JPanel(new BorderLayout());
        pnlNorth.add(unitListView, BorderLayout.WEST);
        pnlNorth.add(pnlControls, BorderLayout.EAST);
        
        JPanel pnlContentPane = new JPanel(new BorderLayout());
        pnlContentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(pnlContentPane);
        pnlContentPane.add(view, BorderLayout.CENTER);
        pnlContentPane.add(pnlNorth, BorderLayout.NORTH);
        
        setBounds(100, 100, 600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        btnRun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runTimer.start();
            }
        });
        
        runTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(!engine.doMove()) {
                        runTimer.stop();
                    }
                    unitListView.repaint();
                    view.repaint();
                } catch (IllegalMoveException ex) {
                    System.out.printf("%s\n", ex.illegalMoveUnitSet); 
                }
                
            }
        });
        
        btnStep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    runTimer.stop();
                    engine.doMove();
                    unitListView.repaint();
                    view.repaint();
                } catch (IllegalMoveException ex) {
                    System.out.printf("%s\n", ex.illegalMoveUnitSet); 
                }
                
            }
        });
        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runTimer.stop();
                engine.reset();
                unitListView.repaint();
                view.repaint();
            }
        });
        
        setVisible(true);
    }
    
    public static void main(String[] args) {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        SwingUtilities.invokeLater(new GameFrame());
    }
}
