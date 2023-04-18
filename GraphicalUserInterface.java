import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class GraphicalUserInterface extends JFrame {
    private java.util.List<JButton> bottomButtons;
    private java.util.List<JButton> topButtons;
    private java.util.List<JTextField> topText;
    private java.util.List<JTextField> bottomText;
    private java.util.List<JLabel> topLabels;
    private java.util.List<JLabel> bottomLabel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel map;

    public GraphicalUserInterface(){this(500, 500, "NARPG");}

    public GraphicalUserInterface(int width, int height, String title) {
        super(title);
        bottomButtons = new ArrayList<>();
        topButtons = new ArrayList<>();
        topText = new ArrayList<>();
        bottomText = new ArrayList<>();
        topLabels = new ArrayList<>();
        bottomLabel = new ArrayList<>();
        setIconImage(new ImageIcon("na.png").getImage());
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //setLayout(new GridLayout(2,1));
        setLayout(new BorderLayout());

        topPanel = new JPanel();
        bottomPanel = new JPanel();
        //topPanel.setMinimumSize(new Dimension(width, topHeight));
        bottomPanel.setMinimumSize(new Dimension(width, 100));

        topPanel.setLayout(new GridLayout(0, 1));
        bottomPanel.setLayout(new FlowLayout());
        topPanel.setBackground(Color.gray);
        bottomPanel.setBackground(Color.lightGray);
        topPanel.setBorder(BorderFactory.createEtchedBorder());
        bottomPanel.setBorder(BorderFactory.createEtchedBorder());

        add(topPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.PAGE_END);
        setVisible(true);
    }

    public void showMap(Map m){
        topPanel.removeAll();
        map = new JPanel();
        map.setLayout(new GridLayout(m.rows(), m.cols()));
        for (int i = 0; i < m.rows(); i++){
            for (int j = 0; j < m.cols(); j++){
                JLabel label = new JLabel(m.getLocation(j, i).toString());
                label.setBackground(m.getLocation(j, i).getFaction().getColor());
                label.setFont(new Font("Serif", Font.PLAIN, 20));
                label.setOpaque(true);
                label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
                label.setAlignmentY(JLabel.CENTER_ALIGNMENT);
                label.setVisible(true);
                map.add(label);
            }
        }
        topPanel.add(map);
        topPanel.revalidate();
        topPanel.repaint();
    }

    public void addTopText(String text){
        JLabel label = new JLabel(text);
        //label.setPreferredSize(new Dimension(100, 30));
        label.setSize(500, 30);                     //TODO try to make them print tighter
        label.setFont(new Font("Serif", Font.PLAIN, 20));
        label.setVisible(true);
        topLabels.add(label);
        topPanel.add(label);
    }

    public void addToTop(JButton button){
        button.setVisible(true);
        button.setFocusable(false);
        button.setBackground(Color.gray);
        button.setBorder(BorderFactory.createEtchedBorder());
        topButtons.add(button);
        topPanel.add(button, BorderLayout.CENTER);
    }

    public void addToBottom(JButton button){
        button.setVisible(true);
        button.setFocusable(false);
        button.setBackground(Color.lightGray);
        button.setBorder(BorderFactory.createEtchedBorder());
        bottomButtons.add(button);
        bottomPanel.add(button);
    }

    public void addToTop(JLabel label){
        label.setVisible(true);
        topLabels.add(label);
        topPanel.add(label);
    }

    public void addToBottom(JLabel label){
        label.setVisible(true);
        bottomLabel.add(label);
        bottomPanel.add(label);
    }

    public void addToTop(JTextField textField){
        textField.setVisible(true);
        topText.add(textField);
        topPanel.add(textField);
    }

    public void addToBottom(JTextField textField){
        textField.setVisible(true);
        bottomText.add(textField);
        bottomPanel.add(textField);
    }

    public void clear(){
        if(topButtons != null) topButtons.forEach((e)->topPanel.remove(e));
        if(bottomButtons != null) bottomButtons.forEach((e)->bottomPanel.remove(e));
        if(topLabels != null) topLabels.forEach((e)->topPanel.remove(e));
        if(bottomLabel != null) bottomLabel.forEach((e)->bottomPanel.remove(e));
        if(topText != null) topText.forEach((e)->topPanel.remove(e));
        if(bottomText != null) bottomText.forEach((e)->bottomPanel.remove(e));
        if(map != null) topPanel.remove(map);
        this.repaint();
    }
    public void clearTop(){
        if(topButtons != null) topButtons.forEach((e)->topPanel.remove(e));
        if(topLabels != null) topLabels.forEach((e)->topPanel.remove(e));
        if(topText != null) topText.forEach((e)->topPanel.remove(e));
        if(map != null) topPanel.remove(map);
        this.repaint();
    }
    public void clearBottom(){
        if(bottomButtons != null) bottomButtons.forEach((e)->bottomPanel.remove(e));
        if(bottomLabel != null) bottomLabel.forEach((e)->bottomPanel.remove(e));
        if(bottomText != null) bottomText.forEach((e)->bottomPanel.remove(e));
        this.repaint();
    }

    public void redraw(){
        this.setVisible(true);
        this.repaint();
    }
}
