package endorphine.icampyou;

/**
 * 예약 정보 아이템 클래스
 */
public class ReservationInfoItem {

    private String reservationNo;   // 예약번호
    private String campingPlace;    // 캠핑장 종류
    private String date;    // 캠핑장 날짜

    // 생성자
    public ReservationInfoItem(String reservationNo, String campingPlace, String date) {
        this.reservationNo = reservationNo;
        this.campingPlace = campingPlace;
        this.date = date;
    }

    // 각 변수들 get 메소드
    public String getReservationNo() {return reservationNo;}

    public String getCampingPlace(){return campingPlace;}

    public String getDate(){return date;}
}
