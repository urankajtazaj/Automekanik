package sample;
/**
 * Created by urankajtazaj on 08/10/16.
 */

public class TabelaKonsumatori {
    private int id;
    private String emri, mbiemri, makina, komuna, pershkrimi;

    public  TabelaKonsumatori(int id, String emri, String mbiemri, String makina, String komuna, String pershkrimi){
        this.emri = emri;
        this.mbiemri = mbiemri;
        this.id = id;
        this.komuna = komuna;
        this.makina = makina;
        this.pershkrimi = pershkrimi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmri() {
        return emri;
    }

    public void setEmri(String emri) {
        this.emri = emri;
    }

    public String getMbiemri() {
        return mbiemri;
    }

    public void setMbiemri(String mbiemri) {
        this.mbiemri = mbiemri;
    }

    public String getMakina() {
        return makina;
    }

    public void setMakina(String makina) {
        this.makina = makina;
    }

    public String getKomuna() {
        return komuna;
    }

    public void setKomuna(String komuna) {
        this.komuna = komuna;
    }

    public String getPershkrimi() {
        return pershkrimi;
    }

    public void setPershkrimi(String pershkrimi) {
        this.pershkrimi = pershkrimi;
    }
}
