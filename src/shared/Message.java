package shared;


public class Message {
    private User sender;
    private String text;
    public Message(User sender,String text){
        this.sender = sender;
        this.text = text;
    }
    public User getSender() {
        return this.sender;
    }

    public String getText() {
        return text;
    }
}
