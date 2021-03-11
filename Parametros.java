import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import javax.swing.*;
import java.io.*;


public class Parametros extends JPanel implements ActionListener{

    private JPanel vBotones;
    private JButton btn_reset, btn_Guardar;
    private JLabel Titulo, Juego_En, sonido, deflJLabel,activar_sonido,activar_musica,pausar,ayuda_juego,
    izqJLabel,derJLabel,dispararJLabel;
    private JComboBox jComboBox, jComboBox2;
    private JTextField s, m , reanudar , ayuda, izq, der, disparar;
    GridBagLayout gbl = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();
    FlowLayout flowLayout = new FlowLayout();

    RandomAccessFile archivo,archivo1;
    File a;

    String[] ops={"Ventana", "Pantalla completa"};
    String[] ops2={"Activado", "Desactivado"};

    public Parametros() {

        try {
            a = new File("parametros.dat");
            if(a.length() == 0)
                setReset();
        } catch (IOException e) {
            //TODO: handle exception
        }

        s = new JTextField("q",10);
        m = new JTextField("W",10);
        reanudar = new JTextField("p",10);
        ayuda = new JTextField("h",10);
        izq = new JTextField("<-",10);
        der = new JTextField("->",10);
        disparar = new JTextField("Barra espaciadora",10);
        setLayout(gbl);
        Titulo= new JLabel("Modificar Parametros");
        Juego_En= new JLabel("Juego en:");
        sonido= new JLabel("Sonido:");
        deflJLabel= new JLabel("Definicion de teclas por defecto.");
        activar_sonido= new JLabel("= Activar/desactivar sonido.");
        activar_musica= new JLabel("= Activar/desactivar musica de fondo.");
        pausar= new JLabel("= Pausar/reanudar el juego.");
        ayuda_juego= new JLabel("= Muestra/oculta ayuda del juego.");
        izqJLabel= new JLabel("= Izquierda");
        derJLabel= new JLabel("= Derecha");
        dispararJLabel= new JLabel("= Disparar");
        jComboBox=new JComboBox(ops);
        jComboBox2=new JComboBox(ops2);
        vBotones = new JPanel();
        btn_reset=new JButton(" Reset");
        btn_Guardar=new JButton("Guardar");
        btn_Guardar.addActionListener(this);
        btn_reset.addActionListener(this);

        try {
            setParametros();
        } catch (IOException e) {
            //TODO: handle exception
        }

        gbc.anchor=GridBagConstraints.NORTH;// anchor es el que lo ubica
        gbc.gridwidth=GridBagConstraints.REMAINDER;
        add(Titulo, gbc);

        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.anchor=GridBagConstraints.WEST;
        gbc.gridwidth=1;
        gbc.insets= new Insets(5,5,5,5);
        add(Juego_En,gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets= new Insets(5,5,5,5);
        add(jComboBox, gbc);

        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.anchor=GridBagConstraints.WEST;
        gbc.gridwidth=1;
        add(sonido,gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets= new Insets(5,5,5,5);
        add(jComboBox2, gbc);

        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.anchor=GridBagConstraints.WEST;
        gbc.gridwidth=1;
        add(deflJLabel,gbc);

        gbc.gridy=4;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.anchor=GridBagConstraints.WEST;
        gbc.gridwidth=1;
        add(s,gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets= new Insets(5,5,5,5);
        add(activar_sonido, gbc);

        gbc.gridy=5;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.anchor=GridBagConstraints.WEST;
        gbc.gridwidth=1;
        add(m,gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets= new Insets(5,5,5,5);
        add(activar_musica ,gbc);

        gbc.gridy=6;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.anchor=GridBagConstraints.WEST;
        gbc.gridwidth=1;
        add(reanudar,gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets= new Insets(5,5,5,5);
        add(pausar ,gbc);

        gbc.gridy=7;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.anchor=GridBagConstraints.WEST;
        gbc.gridwidth=1;
        add(ayuda,gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets= new Insets(5,5,5,5);
        add(ayuda_juego ,gbc);
        
        gbc.gridy=8;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.anchor=GridBagConstraints.WEST;
        gbc.gridwidth=1;
        add(izq,gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets= new Insets(5,5,5,5);
        add(izqJLabel ,gbc);

        gbc.gridy=9;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.anchor=GridBagConstraints.WEST;
        gbc.gridwidth=1;
        add(der,gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets= new Insets(5,5,5,5);
        add(derJLabel ,gbc);

        gbc.gridy=10;
        gbc.fill=GridBagConstraints.HORIZONTAL;
        gbc.anchor=GridBagConstraints.WEST;
        gbc.gridwidth=1;
        add(disparar,gbc);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets= new Insets(5,5,5,5);
        add(dispararJLabel ,gbc);

        gbc.gridy=11;
        vBotones.setLayout(flowLayout);
        vBotones.add(btn_Guardar);
        vBotones.add(btn_reset);
        gbc.anchor=GridBagConstraints.SOUTH;
        gbc.insets=new Insets(15,0,0,0);
        add(vBotones, gbc);

         //Ventana Centrada
    }

    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand() == btn_Guardar.getActionCommand()){
            try {
                setCambios();
                setParametros();
            } catch (IOException error) {}
        }

        if(e.getActionCommand() == btn_reset.getActionCommand()){
            try {
                setReset();
                setParametros();
            } catch (IOException error) {}
        }
    }
  
    public void setReset() throws IOException{

        BufferedWriter bw = new BufferedWriter(new FileWriter(a));
        bw.write("");
        bw.close();
        
        archivo = new RandomAccessFile(a, "rw");
        archivo.seek(archivo.length());
        archivo.writeUTF("Ventana");
        archivo.writeUTF("Activado");
        archivo.writeUTF("q");
        archivo.writeUTF("w");
        archivo.writeUTF("p");
        archivo.writeUTF("h");
        archivo.writeUTF("<-");
        archivo.writeUTF("->");
        archivo.writeUTF("Barra espaciadora");
        archivo.close();
    }

    public void setCambios() throws IOException, IllegalArgumentException{

        BufferedWriter bw = new BufferedWriter(new FileWriter(a));
        bw.write("");
        bw.close();
        
        archivo1 = new RandomAccessFile(a, "rw");
        
        archivo1.seek(archivo1.length());
        
        archivo1.writeUTF(jComboBox.getSelectedItem().toString());
        
        archivo1.writeUTF(jComboBox2.getSelectedItem().toString());
       
        archivo1.writeUTF(s.getText());
        archivo1.writeUTF(m.getText());
        archivo1.writeUTF(reanudar.getText());
        archivo1.writeUTF(ayuda.getText());
        archivo1.writeUTF(izq.getText());
        archivo1.writeUTF(der.getText());
        archivo1.writeUTF(disparar.getText());
        archivo1.close(); 
    }
    
    public void setParametros() throws IOException{
        archivo1 = new RandomAccessFile(a, "rw");
        archivo1.seek(0);
        jComboBox.setSelectedItem(archivo1.readUTF());
        jComboBox2.setSelectedItem(archivo1.readUTF());
        s.setText(archivo1.readUTF());
        m.setText(archivo1.readUTF());
        reanudar.setText(archivo1.readUTF());
        ayuda.setText(archivo1.readUTF());
        izq.setText(archivo1.readUTF());
        der.setText(archivo1.readUTF());
        disparar.setText(archivo1.readUTF());
        
    }
  
    
    public static void main(String[] args) {
        JFrame f = new JFrame("Parametros");
        f.add(new Parametros());
        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                            System.exit(0);
                    };
            };
        f.setVisible(true);
        f.setSize(620, 460);   
    }
    
}




