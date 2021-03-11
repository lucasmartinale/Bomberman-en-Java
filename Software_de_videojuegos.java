import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class Software_de_videojuegos extends JPanel implements ActionListener, ItemListener {

    private JPanel cont1,cont2,pnl_imagenes;
    private JFrame vista;
    private JButton btn_ant,btn_sig,btn_jugar,btn_config;
    private CardLayout cardLayout;
    private JComboBox opciones;

    private String[] contenedores = {"Street Fighter","Pacman","Bomberman"};

    public Software_de_videojuegos(){
        vista = new JFrame("Interfaz"); // Defino el frame
        vista.setLayout(new GridLayout(0,1));

        cont1 = new JPanel(); // Segundo contenedor
        cont1.setLayout(new BorderLayout(5,5));

        opciones = new JComboBox(contenedores);

        btn_ant = new JButton("<< Anterior");
        btn_ant.addActionListener(this);
        btn_sig = new JButton("Siguiente >>");
        btn_sig.addActionListener(this);
        btn_jugar = new JButton("Jugar");
        btn_jugar.addActionListener(this);
        btn_config = new JButton("Configurar parametros");
        btn_config.addActionListener(this);

        cont2 = new JPanel();
        cont2.add(opciones);
        opciones.addItemListener(this);
        cont2.add(btn_ant);
        cont2.add(btn_jugar);
        cont2.add(btn_config);
        cont2.add(btn_sig);

        cont1.add(cont2,BorderLayout.SOUTH);

        pnl_imagenes= new JPanel();
        cardLayout = new  CardLayout();
        pnl_imagenes.setLayout(cardLayout);

        pnl_imagenes.add(contenedores[0],new JLabel(new ImageIcon("imagenes/streetfighter.png")));
        pnl_imagenes.add(contenedores[1],new JLabel(new ImageIcon("imagenes/pacman.png")));
        pnl_imagenes.add(contenedores[2],new JLabel(new ImageIcon("imagenes/bomberman.png")));

        cont1.add(pnl_imagenes,BorderLayout.CENTER);
        
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
    }

    public void itemStateChanged(ItemEvent e){
        cardLayout.show(pnl_imagenes,(String)e.getItem());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ( (e.getSource()).equals(btn_jugar)){
            Component var1 = pnl_imagenes.getComponent(2);
            Component var2 = pnl_imagenes.getComponent(1);
            Component var3 = pnl_imagenes.getComponent(0);
            if (var1.isVisible()){ 
                Interfaz i = new Interfaz();
                opciones.setSelectedIndex(2);
                vista.setVisible(true);
                vista.dispose();
            }
    
            if (var2.isVisible()){ 
                cartel();
                opciones.setSelectedIndex(1);
            }

            if (var3.isVisible()){ 
                cartel();
                opciones.setSelectedIndex(0);
            }
    
        }

        if ( (e.getSource()).equals(btn_ant)){
            cardLayout.previous(pnl_imagenes);
            setCombobox();
        }

        if ( (e.getSource()).equals(btn_sig)){
            cardLayout.next(pnl_imagenes);
            setCombobox();
        }  
        if ( (e.getSource()).equals(btn_config)){
            abrirConfig();
        }
    }

    public void cartel(){
        JTextArea textArea = new JTextArea("El juego seleccionado no se encuentra instalado");
                textArea.setFont(new Font("arial", 5, 16));
                textArea.setEditable(false);
                JFrame frame = new JFrame("Error");
                frame.add(textArea);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
                frame.setVisible(true);
    }

    public void setCombobox(){
        Component var2 = pnl_imagenes.getComponent(2);
        Component var1 = pnl_imagenes.getComponent(1);
        Component var0 = pnl_imagenes.getComponent(0);
        if(var0.isVisible())
            opciones.setSelectedIndex(0);
        if(var1.isVisible())
            opciones.setSelectedIndex(1);
        if(var2.isVisible())
            opciones.setSelectedIndex(2);
    }

    public void abrirConfig(){
        Parametros i = new Parametros();
        JFrame mFrame= new JFrame();
        mFrame.add(i);
        mFrame.setSize(620,460);
        mFrame.setVisible(true);
        mFrame.setLocationRelativeTo(null);
        JDialog d;
        d = new JDialog(mFrame,"Configurar parametros");   
    }
    
    public static void main(String[] args) throws IOException {
        Software_de_videojuegos sdv = new Software_de_videojuegos();
    }
}