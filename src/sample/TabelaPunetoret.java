package sample;
/**
 * Created by urankajtazaj on 11/10/16.
 */
public class TabelaPunetoret {
    private int id;
    private String emri, mbiemri, komuna, pozita, data;
    private float paga;

    public TabelaPunetoret(int id, String emri, String mbiemri, String komuna, String pozita, String data, float paga){
        this.emri = emri;
        this.mbiemri = mbiemri;
        this.id = id;
        this.komuna = komuna;
        this.pozita = pozita;
        this.data = data;
        this.paga = paga;
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

    public String getKomuna() {
        return komuna;
    }

    public void setKomuna(String komuna) {
        this.komuna = komuna;
    }

    public String getPozita() {
        return pozita;
    }

    public void setPozita(String pozita) {
        this.pozita = pozita;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public float getPaga() {
        return paga;
    }

    public void setPaga(float paga) {
        this.paga = paga;
    }
}
