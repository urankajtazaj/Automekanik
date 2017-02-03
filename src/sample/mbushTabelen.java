package sample;

/**
 * Created by urankajtazaj on 06/10/16.
 */
public class mbushTabelen {
    private int id, sasia;
    private String pjesa, tipi, lloji, paisja, info;
    private float qmimi;

    public mbushTabelen(int id, String tipi, String lloji, String paisja, String pjesa, float qmimi, int sasia, String info){
        this.id = id;
        this.tipi = tipi;
        this.lloji = lloji;
        this.paisja = paisja;
        this.pjesa = pjesa;
        this.qmimi = qmimi;
        this.sasia = sasia;
        this.info = info;
    }


    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSasia() {
        return sasia;
    }

    public void setSasia(int sasia) {
        this.sasia = sasia;
    }

    public String getPjesa() {
        return pjesa;
    }

    public void setPjesa(String pjesa) {
        this.pjesa = pjesa;
    }

    public String getTipi() {
        return tipi;
    }

    public void setTipi(String tipi) {
        this.tipi = tipi;
    }

    public String getLloji() {
        return lloji;
    }

    public void setLloji(String lloji) {
        this.lloji = lloji;
    }

    public String getPaisja() {
        return paisja;
    }

    public void setPaisja(String paisja) {
        this.paisja = paisja;
    }

    public float getQmimi() {
        return qmimi;
    }

    public void setQmimi(float qmimi) {
        this.qmimi = qmimi;
    }


}
