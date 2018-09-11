package endorphine.icampyou.EventMenu;

import android.widget.ListView;

/**
 * 이벤트 리스트 아이템 데이터 처리하는 클래스
 */
public class EventListViewItem {

    private int eventImage; // 이벤트 이미지
    private String eventTitle;  // 이벤트 제목
    private String eventDate;   // 이벤트 기간

    // 생성자
    public EventListViewItem(int eventImage, String eventTitle, String eventDate){
        this.eventImage = eventImage;
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
    }

    // 각 변수들 get 메소드
    public int getEventImage(){return eventImage;}

    public String getEventTitle(){return eventTitle;}

    public String getEventDate(){return eventDate;}
}
