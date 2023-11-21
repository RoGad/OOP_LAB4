import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Image img = new ImageIcon("1.png").getImage();
                mySimplePaint msp = new mySimplePaint();

                Toolkit kit = Toolkit.getDefaultToolkit();
                Dimension screenSize = kit.getScreenSize();
                int screenW = screenSize.width;
                int screenH = screenSize.height;

                msp.setMinimumSize(new Dimension(screenW / 2, screenH / 2));
                msp.setTitle("My Simple Paint");
                msp.setLocationByPlatform(true);
                msp.pack();
                msp.setVisible(true);
                msp.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                msp.setIconImage(img);

            }
        });
    }
    public static class mySimplePaint extends JFrame {

        private int previousX, previousY;

        private  int brushFormWidth = 1;
        private Color brushColor = Color.BLACK;
        private JPanel paintPanel = new JPanel();
        private JPanel btnPanel = new JPanel();
        private JButton selectBrushColorButton = new JButton("Select Color");
        private JButton selectBrushWidthButton = new JButton("Select Brush Width");
        private JTextField setBrushWidthValueFild = new JTextField();
        public mySimplePaint() {

            Container c = getContentPane();
            c.add(paintPanel, BorderLayout.CENTER);
            c.add(btnPanel, BorderLayout.SOUTH);

            setBrushWidthValueFild.setColumns(10);
            setBrushWidthValueFild.setHorizontalAlignment(JTextField.CENTER);

            paintPanel.setLayout(new BorderLayout());
            paintPanel.setBackground(Color.WHITE);

            btnPanel.add(selectBrushColorButton);
            btnPanel.add(selectBrushWidthButton);
            btnPanel.add(setBrushWidthValueFild);

            selectBrushColorButton.addActionListener(new colorButtonActionListener());
            selectBrushWidthButton.addActionListener(new widthButtonActionListener());

            addMouseListener(new MouseCoordinateReader());
            addMouseMotionListener(new PaintClass());

        }

        private class colorButtonActionListener implements ActionListener {

            public void actionPerformed(ActionEvent e) {

                brushColor = JColorChooser.showDialog(((Component) e.getSource()).getParent(), "Select brush color panel", brushColor);

            }

        }

        private class widthButtonActionListener implements ActionListener {

            public void actionPerformed(ActionEvent e) {

                String str =  setBrushWidthValueFild.getText();
                boolean b = str.matches("\\d+");

                if (b && str.length()<=2) {

                    brushFormWidth = Integer.parseInt(setBrushWidthValueFild.getText());

                } else JOptionPane.showMessageDialog(null, "Не увлекайтесь размером кисти и не вводите буквенные символы! Значение более 99 не рекомедуется.", "Ошибочное значение", JOptionPane.ERROR_MESSAGE);

            }

        }

        private class MouseCoordinateReader extends MouseAdapter {

            public void mousePressed(MouseEvent ev) {

                setPreviousCoordinates(ev.getX(), ev.getY());

            }

        }

        private class PaintClass extends MouseMotionAdapter {

            public void mouseDragged(MouseEvent ev) {

                Graphics2D g = (Graphics2D)getGraphics();
                BasicStroke brushForm = new BasicStroke(brushFormWidth, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL);

                g.setStroke(brushForm);
                g.setColor(brushColor);
                g.drawLine(previousX, previousY, ev.getX(), ev.getY());
                setPreviousCoordinates(ev.getX(), ev.getY());

            }

        }

        public void setPreviousCoordinates(int aPrevX, int aPrevY) {

            previousX = aPrevX;
            previousY = aPrevY;

        }
    }

}