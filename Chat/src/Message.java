/**
 * Created by Роман on 04.02.2016.
 */
public class Message {
    private String id;
    private String author;
    private Long timestamp;
    private String message;
    public Message(){}
    public Message(String id,String author,Long timestamp,String message){
        this.id=id;
        this.author=author;
        this.timestamp=timestamp;
        this.message=message;
    }
    public String getMessage(){
        return message;
    }
    public Long getTimestamp(){
        return timestamp;
    }
    public String getId(){
        return id;
    }
    public String getAuthor(){
        return author;
    }
    public void setMessage(String message){
        this.message=message;
    }
    public void setTime(Long timestamp){
        this.timestamp=timestamp;
    }
    public void setId(String id){
        this.id=id;
    }
    public void setAuthor(String author){
        this.author=author;
    }
    @Override
    public String toString(){
        return getId()+" "+getAuthor()+" "+getTimestamp()+" "+getMessage();
    }

}
