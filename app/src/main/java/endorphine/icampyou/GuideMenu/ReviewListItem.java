package endorphine.icampyou.GuideMenu;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * 리뷰 리스트 아이템 클래스
 */
public class ReviewListItem {

    private int profileImage; // 프로필 이미지
    private String nickName;  // 유저 닉네임
    private String star;  // 별점
    private int reviewImage;    //리뷰 이미지

    // 생성자
    public ReviewListItem(int profileImage, String nickName, String star, int reviewImage) {
        this.profileImage = profileImage;
        this.nickName = nickName;
        this.star = star;
        this.reviewImage = reviewImage;
    }

    // 각 변수들 get 메소드
    public int getProfileImage() {
        return profileImage;
    }

    public String getNickName() {
        return nickName;
    }

    public String getStar() {
        return star;
    }

    public int getReviewImage() {
        return reviewImage;
    }
}
