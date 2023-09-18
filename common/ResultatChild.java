package common;

public class ResultatChild extends Resultat{
    int pin;

    public ResultatChild(int score, String infoCB,int pin) {
        super(score, infoCB);
        this.pin=pin;
    }
    @Override
    public void setInfoCB(String infoCB){
        String encryptedInfoCB = infoCB.toUpperCase();
        super.setInfoCB(encryptedInfoCB);
    }
    @Override
    public String getInfoCB(){
        String decryptedInfoCB = super.getInfoCB().toLowerCase()+pin;
        return decryptedInfoCB;
    }
    @Override
    public String toString(){
        String parentString=super.toString();
        return parentString+"\nbrute value of infoCB is : "+super.getInfoCB()+"\ncodePIN : "+pin;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }
}
