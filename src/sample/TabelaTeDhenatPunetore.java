package sample;
/**
 * Created by urankajtazaj on 13/10/16.
 */
public class TabelaTeDhenatPunetore {
    private int id;
    private String puna, data, konsumatori, pershkrimi, kryer, makina;
    float qmimi;

    public TabelaTeDhenatPunetore(int id, String puna, String data, float qmimi, String konsumatori, String pershkrimi, String kryer, String makina){
        this.id = id;
        this.data = data;
        this.konsumatori = konsumatori;
        this.pershkrimi = pershkrimi;
        this.qmimi = qmimi;
        this.puna = puna;
        this.kryer = kryer;
        this.makina = makina;
    }

    public String getMakina() {
        return makina;
    }

    public void setMakina(String makina) {
        this.makina = makina;
    }

    public String getKryer() {
        return kryer;
    }

    public void setKryer(String kryer) {
        this.kryer = kryer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPuna() {
        return puna;
    }

    public void setPuna(String puna) {
        this.puna = puna;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getKonsumatori() {
        return konsumatori;
    }

    public void setKonsumatori(String konsumatori) {
        this.konsumatori = konsumatori;
    }

    public String getPershkrimi() {
        return pershkrimi;
    }

    public void setPershkrimi(String pershkrimi) {
        this.pershkrimi = pershkrimi;
    }

    public float getQmimi() {
        return qmimi;
    }

    public void setQmimi(float qmimi) {
        this.qmimi = qmimi;
    }
}
