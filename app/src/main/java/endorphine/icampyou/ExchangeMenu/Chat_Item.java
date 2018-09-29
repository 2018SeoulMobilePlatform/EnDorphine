package endorphine.icampyou.ExchangeMenu;

import android.graphics.Bitmap;

//채팅 목록 한 개를 차지할 클래스
public class Chat_Item {

    private Bitmap need_pic;
    private String user_id;
    private String need_thing;
    private String lettable_thing;
    private String camping_name;
    private String number;
    private String flag;
    private String nickname;

    public Chat_Item(String number,Bitmap image,String user,String nickname,String need, String lettable, String camp,String flag){
        this.number =number;
        this.need_pic = image;
        this.user_id = user;
        this.need_thing = need;
        this.lettable_thing = lettable;
        this.camping_name = camp;
        this.nickname = nickname;
        this.flag = flag;
    }

    public void setNumber(String number){ this.number = number;}
    public void setImage(Bitmap _need_pic) { this.need_pic = _need_pic; }
    public void setUser_id(String _user_id) { this.user_id = _user_id;}
    public void setNeed_thing(String _need_thing) { this.need_thing = _need_thing ; }
    public void setLettable_thing(String _lettable_thing){this.lettable_thing = _lettable_thing;}
    public void setCamping_name(String _camping_name){ this.camping_name = _camping_name;}
    public void setFlag(String _flag){ this.flag = flag;}
    public void setNickname(String _nickname){this.nickname = _nickname;}

    public String getNumber(){return number;}
    public Bitmap getImage(){ return need_pic; }
    public String getUser_id(){
        return user_id;
    }
    public String getNeed_thing(){
        return need_thing;
    }
    public String getLettable_thing(){
        return lettable_thing;
    }
    public String getCamping_name(){ return camping_name; }
    public String getFlag(){ return flag;}
    public String getNickname(){return nickname;}
 }
