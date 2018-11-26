package id.ac.ukdw.woney;

public class Transaction {
    public String from, date, status, value;

    public Transaction(String from, String date, String status , String value){
        this.from = from;
        this.date = date;
        this.status = status;
        this.value = value;
    }
}
