package endorphine.icampyou.GuideMenu;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * 리뷰 리스트 아이템 클래스
 */
public class ReviewListItem {

    private int profileImage; // 프로필 이미지
    private String nickName;  // 유저 닉네임
    private float star;  // 별점
    private int reviewImage;    //리뷰 이미지
    private String reviewContent;   // 리뷰 내용
    private String campingPlace;    // 캠핑장 종류

    // 생성자
    public ReviewListItem(String campingPlace, int profileImage, String nickName, float star, int reviewImage, String reviewContent) {
        this.campingPlace = campingPlace;
        this.profileImage = profileImage;
        this.nickName = nickName;
        this.star = star;
        this.reviewImage = reviewImage;
        this.reviewContent = reviewContent;
    }

    // 각 변수들 get 메소드
    public int getProfileImage() {
        return profileImage;
    }

    public String getNickName() {
        return nickName;
    }

    public float getStar() {
        return star;
    }

    public int getReviewImage() {
        return reviewImage;
    }

    public String getReviewContent() {return reviewContent;}

    public String getCampingPlace() {return campingPlace;}
}
