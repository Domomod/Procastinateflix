package Aktorzy;

public class KontoBankowe {
    public Integer numerKonta;
    public Integer stanKonta;

    public KontoBankowe(Integer numerKonta, Integer stanKonta) {
        this.numerKonta = numerKonta;
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
}
