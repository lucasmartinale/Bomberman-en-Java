import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Jugador extends JPanel implements ActionListener{

    private JFrame jframe = new JFrame("Jugador");
    private JPanel JPanelBotones = new JPanel();
    private JLabel titulo = new JLabel();
    private JLabel nombre = new JLabel();
    private JTextField textNombre = new JTextField();
    private JButton btnAceptar = new JButton();
    private GridBagLayout gbl = new GridBagLayout();
    private GridBagConstraints gbc = new GridBagConstraints();
    private FlowLayout flowLayout = new FlowLayout();
    static int puntaje = 0;
    Ranking ranking = new Ranking();

    public Jugador() {
        
        titulo.setText("Puntaje   "+puntaje);
        titulo.setFont(new java.awt.Font("Retro Gaming", 1, 20));
        titulo.setBackground(new Color(0,0,0));
        titulo.setForeground(new Color(255,255,255));
        titulo.setBorder(null);
        titulo.setFocusable(false);

        //nombre.setText("Nickname ");
        nombre.setFont(new java.awt.Font("Retro Gaming", 1, 16));
        nombre.setBackground(new Color(0,0,0));
        nombre.setForeground(new Color(255,255,255));
        nombre.setBorder(null);
        nombre.setFocusable(false);
        
        textNombre.setColumns(20);
        textNombre.setHorizontalAlignment(JTextField.CENTER);
        textNombre.setForeground(new Color(0,0,0));
        textNombre.setFont(new java.awt.Font("Retro Gaming", 1, 16));
        textNombre.setText("Ingrese su nickname");
        textNombre.setBorder(null);

        btnAceptar.setText(" Aceptar ");
        btnAceptar.setFont(new java.awt.Font("Retro Gaming", 1, 16));
        btnAceptar.setBackground(new Color(0,0,0));
        btnAceptar.setForeground(new Color(255,255,255));
        //btnAceptar.setBorder(null);
        btnAceptar.setFocusable(false);
// gridBaglayout
        jframe.setLayout(gbl);
        btnAceptar.addActionListener(this);

        JPanelBotones.setBackground(new Color(0,0,0));

 // Primera fila - titulo
        gbc.anchor=GridBagConstraints.NORTH;
        gbc.insets=new Insets(0,0,10,0);
        gbc.gridwidth=GridBagConstraints.REMAINDER;
        jframe.add(titulo, gbc);

// Segunda fila - nombre
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.anchor=GridBagConstraints.WEST;
        gbc.gridwidth=1;
        gbc.insets=new Insets(0,0,0,0);
        jframe.add(nombre, gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        jframe.add(textNombre, gbc);

        gbc.gridwidth = 1;
        gbc.insets=new Insets(5,0,5,0);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill=GridBagConstraints.NONE;
     
	    JPanelBotones.setLayout(flowLayout);
        JPanelBotones.add(btnAceptar);
        
        gbc.anchor=GridBagConstraints.SOUTH;
        gbc.insets=new Insets(15,0,0,0);
        jframe.add(JPanelBotones, gbc);

        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                            System.exit(0);
                    };
            };
    
        jframe.addWindowListener(l);
        //jframe.setSize(300, 180);
        jframe.setSize(640,480);
        jframe.setResizable(false);
        jframe.getContentPane().setBackground(new Color(0,0,0));
        jframe.setLocationRelativeTo(null);
        //jframe.setUndecorated(true);
        jframe.setVisible(true);
    }

    public static void cargarPuntaje(int n){
        puntaje = n;
    }

    public void actionPerformed(ActionEvent e){

        if(e.getActionCommand() == btnAceptar.getActionCommand()){
            String nombre = textNombre.getText();

            // Ve si califica para pedir el nombre.
            if(ranking.califica(puntaje))
                ranking.registrar(nombre, puntaje);

            jframe.dispose();
            Interfaz i = new Interfaz();
        }
    }

    public static void main(String[] args){
        Jugador i = new Jugador();
	}
}
