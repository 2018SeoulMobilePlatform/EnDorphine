package endorphine.icampyou.EventMenu;


/**
 * 이벤트 리스트 아이템 데이터 처리하는 클래스
 */
public class EventListViewItem {

    private int eventImage; // 이벤트 이미지
    private String eventPlace;  // 이벤트 개최 캠핑장
    private String eventTitle;  // 이벤트 제목
    private String eventDate;   // 이벤트 기간
    private Boolean isClosed;    // 이벤트 종료 여부

    // 생성자
    public EventListViewItem(int eventImage, String eventPlace, String eventTitle, String eventDate, Boolean isClosed){
        this.eventImage = eventImage;
        this.eventPlace = eventPlace;
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
        this.isClosed = isClosed;
    }

    // 각 변수들 get 메소드
    public int getEventImage(){return eventImage;}

    public String getEventPlace(){return eventPlace;}

    public String getEventTitle(){return eventTitle;}

    public String getEventDate(){return eventDate;}

    public Boolean getEventIsClosed(){return isClosed;}
}
