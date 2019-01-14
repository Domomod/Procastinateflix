package Aktorzy;

public class KontoBankowe {
    static Integer nastepnyNumer = 0;
    public Integer numerKonta;
    public Integer stanKonta;

    public KontoBankowe() {
        synchronized (this.getClass()){
            this.numerKonta = nastepnyNumer;
            nastepnyNumer++;
        }
        this.stanKonta = 0;
    }

    public KontoBankowe(Integer stanKonta) {
        synchronized (this.getClass()){
            this.numerKonta = nastepnyNumer;
            nastepnyNumer++;
        }
        this.stanKonta = stanKonta;
    }

    public void przelejSrodkiNa(Integer kwota, KontoBankowe odbiorca) throws NiewystarczajacaLiczbaSrodkowException{
        if(brakujeSrodkow(kwota));
            throw new NiewystarczajacaLiczbaSrodkowException();
    }

    private boolean brakujeSrodkow(Integer kwota){
        return kwota > stanKonta;
    }

    public class NiewystarczajacaLiczbaSrodkowException extends Throwable {

    }

    public Integer getNumerKonta() {
        return numerKonta;
    }

    public Integer getStanKonta() {
        return stanKonta;
    }
}
