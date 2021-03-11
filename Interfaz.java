import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.entropyinteractive.*;

public class Interfaz extends JPanel implements ActionListener, ItemListener{

    private JFrame vista;
    private JPanel pnl_imagenes, cont1, cont2;
    private JButton jugar, ranking, help;
    CardLayout cardLayout;
    String fondo, fhelp;
    JGame juego;
    Thread t;

    public Interfaz(){       
        vista = new JFrame("Interfaz"); 
        vista.setLayout(new GridLayout(0,1));

        cont1 = new JPanel();
        cont1.setLayout(new BorderLayout(5,5));

        jugar = new JButton("Jugar");
        jugar.addActionListener(this);
        ranking = new JButton("Ranking");
        ranking.addActionListener(this);
        help = new JButton("Help");
        help.addActionListener(this);

        cont2 = new JPanel();
        cont2.add(help);
        cont2.add(Box.createRigidArea(new Dimension(50,0)));
        cont2.add(jugar);
        cont2.add(Box.createRigidArea(new Dimension(50,0)));
        cont2.add(ranking);
        
        cont1.add(cont2,BorderLayout.SOUTH);       

        pnl_imagenes = new JPanel();
        cardLayout = new  CardLayout();
        pnl_imagenes.setLayout(cardLayout);

        pnl_imagenes.add(fondo, new JLabel(new ImageIcon("imagenes/fondo interfaz.png")));

        cont1.add(pnl_imagenes, BorderLayout.CENTER);
        
        vista.add(cont1);

        vista.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent windowEvent){
                        System.exit(0);
            }        
        }); 

        vista.setSize(640,480); 
        vista.setVisible(true);
        vista.setLocationRelativeTo(null);
        vista.setResizable(false);

        jugar.setBackground(new Color(0,0,0));
        ranking.setBackground(new Color(0,0,0));
        help.setBackground(new Color(0,0,0));
        vista.setBackground(new Color(0,0,0));
        cont1.setBackground(new Color(0,0,0));
        cont2.setBackground(new Color(0,0,0));
        pnl_imagenes.setBackground(new Color(0,0,0));
        jugar.setForeground(new Color(255,255,255));
        ranking.setForeground(new Color(255,255,255));
        help.setForeground(new Color(255,255,255));
        jugar.setFont(new Font("Retro Gaming", 3, 30));
        ranking.setFont(new Font("Retro Gaming", 3, 30));
        help.setFont(new Font("Retro Gaming", 3, 30));
        jugar.setBorder(null);
        ranking.setBorder(null);
        help.setBorder(null);
        cont1.setBorder(null);
        cont2.setBorder(null);
        pnl_imagenes.setBorder(null);

        //Sonido.INTERFAZ.play();
    }

    public void itemStateChanged(ItemEvent e){
        cardLayout.show(pnl_imagenes,(String)e.getItem());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ( (e.getSource()).equals(jugar)){
            vista.dispose();
            //Sonido.INTERFAZ.stop();
            juego = new Juego_Bomberman();
            t = new Thread() {
                public void run() {
                    juego.run(1.0 / 30.0);
                    System.exit(0);
                }
            };
            t.start();
        }

        if ( (e.getSource()).equals(ranking)){
            cartelRanking();
        }

        if ( (e.getSource()).equals(help)){
            cartelHelp();
        }
        
    }

    public void cartelRanking(){
        Ranking ranking = new Ranking();
        String texto = new String();

        for(int i=0; i<ranking.size(); i++){
            texto += "\n                "+ranking.getRegistro(i);
        }

        JTextArea textArea = new JTextArea();
        textArea.setText(texto);
        textArea.setFont(new Font("Retro Gaming", 1, 26));
        textArea.setEditable(false);
        textArea.setBackground(new Color(0,0,0));
        textArea.setForeground(new Color(255,255,255));

        JFrame frame = new JFrame("Ranking");
        frame.add(textArea);
        frame.pack();
        frame.setSize(620,440);
        frame.getContentPane().setBackground(new Color(0,0,0));
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void cartelHelp(){
        JPanel jpanel = new JPanel();
        cardLayout = new  CardLayout();
        jpanel.setLayout(cardLayout);
        jpanel.add(fhelp, new JLabel(new ImageIcon("imagenes/interfaz help.png")));
        JFrame frame = new JFrame("Help");
        frame.setSize(620,440);
        frame.add(jpanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

    }
    public static void main(String[] args) {
        Interfaz i = new Interfaz();
    }  
}