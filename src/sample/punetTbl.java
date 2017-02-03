package sample;
/**
 * Created by urankajtazaj on 07/10/16.
 */
public class punetTbl {
    private int id;
    private String lloji, data, pershkrimi, makina;
    private float qmimi;
    private String ngjyra;

    public punetTbl(int id, String lloji, String data, float qmimi, String pershkrimi, String ngjyra, String makina){
        this.id = id;
        this.lloji = lloji;
        this.data = data;
        this.qmimi = qmimi;
        this.pershkrimi = pershkrimi;
        this.ngjyra = ngjyra;
        this.makina = makina;
    }

    public String getMakina(){return makina;}

    public void setMakina(String makina){this.makina = makina;}

    public String getNgjyra() {
        return ngjyra;
    }

    public void setNgjyra(String ngjyra) {
        this.ngjyra = ngjyra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLloji() {
        return lloji;
    }

    public void setLloji(String lloji) {
        this.lloji = lloji;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public float getQmimi() {
        return qmimi;
    }

    public void setQmimi(float qmimi) {
        this.qmimi = qmimi;
    }

    public String getPershkrimi() {
        return pershkrimi;
    }

    public void setPershkrimi(String pershkrimi) {
        this.pershkrimi = pershkrimi;
    }

}
