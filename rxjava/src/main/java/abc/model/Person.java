package abc.model;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Person {

    private final StringProperty nick;
    private final StringProperty amount;

    public Person() {
        this(null, null);
    }

    public Person(String nick, String amount) {
        this.nick = new SimpleStringProperty(nick);
        this.amount = new SimpleStringProperty(amount);
    }

    public String getNick() {
        return nick.get();
    }

    public StringProperty nickProperty() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick.set(nick);
    }

    public String getAmount() {
        return amount.get();
    }

    public StringProperty amountProperty() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount.set(amount);
    }
}
