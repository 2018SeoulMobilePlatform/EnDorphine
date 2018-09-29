package endorphine.icampyou;

import android.support.annotation.NonNull;

public class ChattingMessageVO{
    private String from_id;
    private String to_id;
    private String message;
    private String datetime;

    public ChattingMessageVO(String from_id,String to_id,String message,String datetime){
        this.from_id = from_id;
        this.to_id = to_id;
        this.message = message;
        this. datetime = datetime;
    }

    public void setFrom_id(String from_id){this.from_id = from_id;}
    public void setTo_id(String to_id){this.to_id = to_id;}
    public void setMessage(String message){this.message = message;}
    public void setDatetime(String datetime){this.datetime = datetime;}

    public String getFrom_id(){return from_id;}
    public String getTo_id(){return to_id;}
    public String getMessage(){return message;}
    public String getDatetime(){return datetime;}

}
