package endorphine.icampyou.NavigationDrawerMenu;

/**
 * 예약 정보 아이템 클래스
 */
public class ReservationInfoItem {

    private String reservationNo;   // 예약번호
    private String campingPlace;    // 캠핑장 종류
    private String date;    // 캠핑장 날짜

    private String tent_type;
    private String tent_number;
    private String total_price;


    // 생성자
    public ReservationInfoItem(String reservationNo, String campingPlace, String date,String tent_type,String tent_number,String total_price) {
        this.reservationNo = reservationNo;
        this.campingPlace = campingPlace;
        this.date = date;
        this.tent_type = tent_type;
        this.tent_number = tent_number;
        this.total_price = total_price;
    }

    // 각 변수들 get 메소드
    public String getReservationNo() {return reservationNo;}

    public String getCampingPlace(){return campingPlace;}

    public String getDate(){return date;}

    public String getTent_type(){
        return tent_type;
    }

    public String getTent_number(){
        return tent_number;
    }

    public String getTotal_price(){
        return total_price;
    }
}
