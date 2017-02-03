package sample;
/**
 * Created by urankajtazaj on 07/10/16.
 */
public class tabelaShitjet {
    private int id;
    private float qmimi;
    private String emri, paisja, tipi_makines, lloji_makines, data_shitjes;

    public tabelaShitjet(int id, String emri, String paisja, String tipi_makines, String lloji_makines, float qmimi, String data_shitjes){
        this.id = id;
        this.emri = emri;
        this.paisja = paisja;
        this.tipi_makines = tipi_makines;
        this.lloji_makines = lloji_makines;
        this.qmimi = qmimi;
        this.data_shitjes = data_shitjes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getQmimi() {
        return qmimi;
    }

    public void setQmimi(float qmimi) {
        this.qmimi = qmimi;
    }

    public String getEmri() {
        return emri;
    }

    public void setEmri(String emri) {
        this.emri = emri;
    }

    public String getPaisja() {
        return paisja;
    }

    public void setPaisja(String paisja) {
        this.paisja = paisja;
    }

    public String getTipi_makines() {
        return tipi_makines;
    }

    public void setTipi_makines(String tipi_makines) {
        this.tipi_makines = tipi_makines;
    }

    public String getLloji_makines() {
        return lloji_makines;
    }

    public void setLloji_makines(String lloji_makines) {
        this.lloji_makines = lloji_makines;
    }

    public String getData_shitjes() {
        return data_shitjes;
    }

    public void setData_shitjes(String data_shitjes) {
        this.data_shitjes = data_shitjes;
    }
}
