package sample;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by urankajtazaj on 19/10/16.
 */
public class Query {
//String tableAdmin = "create table admin(id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
//        "emri varchar(30) not null, fjalekalimi varchar(30) not null, " +
//        "lloji smallint not null," +
//        " unique (id))";
//String tabelaPunetoret = "create table Punetoret(id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), " +
//        "emri varchar(25) not null, mbiemri varchar(30) not null, komuna varchar(30) not null, " +
//        "pozita varchar(30) not null, paga decimal(10,2) not null, regjistrimi date not null)";
//String tabelaPaisjet = "create table Paisjet(id integer not null generated always as identity (start with 1, increment by 1), " +
//        "prodhimi varchar(25) not null, tipi varchar(25) not null, lloji_pjeses varchar(30) not null, emri_pjeses varchar(30) not null, " +
//        "qmimi decimal(10,2) not null, q_puna decimal(10,2) not null, sasia integer not null, info varchar(100))";
//String tabelaKons = "create table Konsumatori(id integer not null generated always as identity (start with 1, increment by 1), "+
//        "emri varchar(30) not null, mbiemri varchar(30) not null, komuna varchar(30) not null, makina varchar(30) not null, " +
//        "pershkrimi varchar(100))";
//String tabelaPunet = "create table Punet(id integer not null generated always as identity (start with 1, increment by 1), " +
//        "lloji varchar(30) not null, data date not null, qmimi decimal(10,2) not null, konsumatori varchar(30) not null, " +
//        "pershkrimi varchar(100), punetori varchar(80) not null)";

    public static void main(String[] args){
        try {
            String tabelaPaisjet = "create table Pjeset(id integer not null generated always as identity (start with 1, increment by 1), " +
                    "prodhimi varchar(25) not null, tipi varchar(25), lloji_pjeses varchar(30) not null, emri_pjeses varchar(30) not null, " +
                    "qmimi decimal(10,2) not null, sasia integer not null, info varchar(100))";
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection("jdbc:h2:file:~/db/auto","test","test");
//            insert(conn);
            Statement stmt = conn.createStatement();
//            stmt.execute(tabelaPaisjet);
//            System.out.println("DONE");
            ResultSet rs = stmt.executeQuery("select count(id) from punet where kryer = 'po' and year(data) = '2016'");
            System.out.println("------------------------------------");
            while (rs.next()){
                System.out.println(rs.getInt(1));
            }
            System.out.println("------------------------------------");
        }catch (Exception ex){
            System.err.println(ex.getMessage());
        }
    }

    private static void insert(Connection con){
        try {

//            String sqlAdmin = "insert into admin(emri, fjalekalimi, lloji) values (" +
//                    "'urankajtazaj', 'uran123', 1)";
//            String sqlAdminDelete = "delete from admin where id = 2";
//            String sqlPunetoret = "insert into Punetoret(emri, mbiemri, komuna, pozita, paga, regjistrimi) values " +
//                    "('Uran', 'Kajtazaj', 'Istog', 'Menaxher', 500.0, DATE '2016-10-21')";
//            String delPunetoret = "delete from Punetoret where id = 103";
//            String sqlPjeset = "insert into Pjeset(prodhimi, tipi, lloji_pjeses, emri_pjeses, qmimi, q_puna, sasia, info) values " +
//                    "('bmw', 'q7', 'Nxemje & ngrohje', 'Termostat', 19.0, 5.0, 3, '')";
//            String sqlKons = "insert into Konsumatori(emri, mbiemri, komuna, makina, pershkrimi) values " +
//                    "('Uran', 'Kajtazaj', 'Istog', 'Audi 80 A4', '')";
//            String sqlPunet = "insert into Punet(lloji, data, qmimi, konsumatori, pershkrimi, punetori, kryer) values " +
//                    "('Montim i dyerve', DATE '2016-11-2', 22.0, 'Fabian Mirdita', '', 'urankajtazaj', 'jo')";

//            con.createStatement().execute();

        }catch (Exception ex){ex.printStackTrace();}
    }

}
