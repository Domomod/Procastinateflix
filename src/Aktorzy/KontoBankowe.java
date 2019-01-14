package Aktorzy;

import com.sun.javaws.exceptions.InvalidArgumentException;

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
        try {
            odbiorca.zwiekszStanKonta(kwota);
            stanKonta -= kwota;
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }

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

    public void zwiekszStanKonta(Integer kwota) throws InvalidArgumentException {
        if(kwota < 0)
            throw new InvalidArgumentException(new String[]{"Proba pobrania z konta odbiorcy"});
        this.stanKonta += kwota;
    }
}
