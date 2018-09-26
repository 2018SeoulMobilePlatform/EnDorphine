package endorphine.icampyou.GuideMenu.Review;

import android.graphics.Bitmap;

/**
 * 리뷰 리스트 아이템 클래스
 */
public class ReviewListItem {

    private String nickName;  // 유저 닉네임
    private float star;  // 별점
    private Bitmap reviewImage;    //리뷰 이미지
    private String reviewContent;   // 리뷰 내용
    private String campingPlace;    // 캠핑장 종류

    //리뷰 이미지뷰


    // 생성자
    public ReviewListItem(String campingPlace, String nickName, float star, Bitmap reviewImage, String reviewContent) {
        this.campingPlace = campingPlace;
        this.nickName = nickName;
        this.star = star;
        this.reviewImage = reviewImage;
        this.reviewContent = reviewContent;
    }


    public String getNickName() {
        return nickName;
    }

    public float getStar() {
        return star;
    }

    public Bitmap getReviewImage() {
        return reviewImage;
    }

    public String getReviewContent() {return reviewContent;}

    public String getCampingPlace() {return campingPlace;}
}
