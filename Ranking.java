import java.sql.*;

public class Ranking {
    private static final String URL_TOPLIST = "ranking.db";
    private static final String SQL_TABLA_TOPLIST ="CREATE TABLE IF NOT EXISTS 	ranking \n"+
    "(id INTEGER PRIMARY KEY AUTOINCREMENT , nombre TEXT, puntaje INTEGER)";

    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private PreparedStatement pstmt= null;

    Ranking(){
        inicializar(); // Si la base de dato no existe, la crea.
    }

    public void inicializar() {
        try {
            String url = "jdbc:sqlite:"+URL_TOPLIST;
             
            conn = DriverManager.getConnection(url); // Si no existe crea el archivo de la base de datos.
     
            stmt = conn.createStatement();
            String sql = SQL_TABLA_TOPLIST;
            stmt.executeUpdate(sql);
            stmt.close();
     
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
     
    public void cerrar(){
        try{
            conn.close();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }  
     }
    
    public boolean califica(int puntos){
        int cant = 0;
        boolean califico = false;

        // Veo si el puntaje es mayor a los guardados en el top.
        try{
                String sql ="SELECT * FROM ranking";
                rs = stmt.executeQuery(sql);
        
                while(rs.next()){
                    if(rs.getInt("puntaje") < puntos)
                        califico = true;
                    cant++;
                }
        }
        catch (SQLException e) {
            System.out.println("Error READ top - FROM califica - 1"); 
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error READ top - FROM califica - 2");
            }
        }

        // Si el top tiene menos de 10 registros califica igual.
        if(cant < 10)
            califico = true;

        return califico;
    }

    public void registrar(String nombre, int puntos){
        int minimo = -1;
        int index = -1;
        int aux;
        int cant = 0;

        // Se cuentan los elementos cargados y se busca el minimo que se reemplazara.
        try{
            String sql ="SELECT * FROM ranking";
            rs = stmt.executeQuery(sql);
    
            while(rs.next()){
                aux = rs.getInt("puntaje");
                if(minimo == -1 || aux < minimo){
                    minimo = aux;
                    index = rs.getInt("id");
                }
                cant++;
            }
        }
        catch (SQLException e) {
            System.out.println("Error READ top - FROM Regristrar - 1"); 
            try {
                if (conn != null) {
                    conn.close();
                }
            }
            catch (SQLException ex) {
                System.out.println("Error READ top - FROM Regristrar - 1");
            }
        }

        // Si hay mas de 10 elementos cargados, se reemplaza el mas chico.
        if(cant >= 10 && minimo < puntos){
            try{
                String sql = "UPDATE ranking SET nombre=?, puntaje=? WHERE id=?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
    
                pstmt.setString(1, nombre);
                pstmt.setInt(2, puntos);
                pstmt.setInt(3, index);
                pstmt.executeUpdate();
            }
            catch (SQLException e) {
                System.out.println("Error UPDATE registrar - 1");
                try {
                    if (conn != null) {
                        conn.close();
                    }
                }
                catch (SQLException ex) {
                    System.out.println("Error UPDATE registrar - 2");
                }
            }
        }
        else{ // Si hay menos de 10 elementos cargados, lo ingresa.
            if(cant < 10){
                try{
                    String sql = "INSERT INTO ranking(nombre,puntaje) VALUES(?,?)";
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, nombre);
                    pstmt.setInt(2, puntos);
                    pstmt.executeUpdate();
                }
                catch (SQLException e) {
                    System.out.println("Error insertar juego - 1");
                    try {
                        if (conn != null) {
                            conn.close();
                        }
                    }
                    catch (SQLException ex) {
                        System.out.println("Error insertar juego - 2");
                    }
                }
            }
        }
    }

    public String getRegistro(int index){
        int cont = 0;
        String registro = "";
        try{
            String sql ="SELECT * FROM ranking ORDER BY puntaje DESC";
            rs = stmt.executeQuery(sql);
    
            while(rs.next()){
                if(index == cont){
                    registro = rs.getString("nombre");
                    registro += "\t";
                    registro += String.valueOf(rs.getInt("puntaje"));
                }
                cont++;
            }
        }
        catch (SQLException e) {
            System.out.println("Error READ top - FROM regristro - 1"); 
            try {
                if (conn != null) {
                    conn.close();
                }
            }
            catch (SQLException ex) {
                System.out.println("Error READ top - FROM regristro - 2");
            }
        }
        return registro;
    }

    public int size(){
        int cont = 0;
        try{
            String sql ="SELECT * FROM ranking ORDER BY puntaje DESC";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                cont++;
            }
        }
        catch (SQLException e) {
            System.out.println("Error READ top - FROM size - 1"); 
            try {
                if (conn != null) {
                    conn.close();
                }
            }
            catch (SQLException ex) {
                System.out.println("Error READ top - FROM size - 2");
            }
        }
        return cont;
    }
}