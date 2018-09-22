package endorphine.icampyou.GuideMenu;

import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import endorphine.icampyou.ExchangeMenu.Chat_Item;
import endorphine.icampyou.GuideMenu.ReviewListItem;
import endorphine.icampyou.R;

/**
 * 리뷰 리스트뷰 어댑터 클래스
 */
public class ReviewListViewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<ReviewListItem> reviewData;
    private int layout;

    private ImageView profileImage;
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

    public void addItem(ReviewListItem mitem){
        reviewData.add(mitem);
    }

    public void removeAllitem(){
        reviewData.clear();
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

        nickName=(TextView)convertView.findViewById(R.id.review_nickname);
        nickName.setText(reviewListItem.getNickName());

        star = (RatingBar)convertView.findViewById(R.id.review_star);
        star.setRating(reviewListItem.getStar());

        reviewImage = (ImageView)convertView.findViewById(R.id.review_image);
        reviewImage.setImageBitmap(reviewListItem.getReviewImage());

        reviewContent = convertView.findViewById(R.id.review_content);
        reviewContent.setText(reviewListItem.getReviewContent());

        return convertView;
    }
}
