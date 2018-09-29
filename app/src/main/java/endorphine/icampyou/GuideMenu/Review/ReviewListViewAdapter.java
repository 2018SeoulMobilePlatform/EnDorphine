package endorphine.icampyou.GuideMenu.Review;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import endorphine.icampyou.GlideApp;
import endorphine.icampyou.GuideMenu.Review.ReviewListItem;
import endorphine.icampyou.R;

import static android.view.View.GONE;

/**
 * 리뷰 리스트뷰 어댑터 클래스
 */
public class ReviewListViewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<ReviewListItem> reviewData;
    private int layout;

    private CircleImageView profileImage;
    private TextView nickName;
    private RatingBar star;
    private ImageView reviewImage;
    private TextView reviewContent;

    // 생성자
    public ReviewListViewAdapter(LayoutInflater inflater, int layout, ArrayList<ReviewListItem> reviewData){
        //this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.inflater = inflater;
        this.reviewData = reviewData;
        this.layout=layout;
    }

    public ArrayList<ReviewListItem> getReviewList(){
        return reviewData;
    }

    public void addItem(ReviewListItem mitem){
        reviewData.add(mitem);
    }

    @Override
    public int getCount(){return reviewData.size();}
    @Override
    public String getItem(int position){return reviewData.get(position).getNickName();}
    @Override
    public long getItemId(int position){return position;}
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            convertView=inflater.inflate(layout,parent,false);
        }

        ReviewListItem reviewListItem= reviewData.get(position);

        profileImage= (CircleImageView)convertView.findViewById(R.id.review_profile_image);
        GlideApp.with(convertView).load(reviewListItem.getProfile_image()).into(profileImage);

        nickName=(TextView)convertView.findViewById(R.id.review_nickname);
        nickName.setText(reviewListItem.getNickName());

        star = (RatingBar)convertView.findViewById(R.id.review_star);
        star.setRating(reviewListItem.getStar());

        reviewImage = (ImageView)convertView.findViewById(R.id.review_image);
        if(reviewData.get(position).getReviewImage() == null){
            reviewImage.setVisibility(GONE);
        } else{
            GlideApp.with(convertView).load(reviewListItem.getReviewImage()).into(reviewImage);
        }

        reviewContent = convertView.findViewById(R.id.review_content);
        reviewContent.setText(reviewListItem.getReviewContent());

        return convertView;
    }
}
