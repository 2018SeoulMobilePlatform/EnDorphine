package endorphine.icampyou.GuideMenu.Reservation;

public class CardItem {
    private int mTextResource;
    private int mTitleResource;
    private int mPicResource;

    public CardItem(int title, int pic, int text) {
        mTitleResource = title;
        mPicResource = pic;
        mTextResource = text;
    }

    public int getText() {
        return mTextResource;
    }

    public int getPic() { return mPicResource; }

    public int getTitle() {
        return mTitleResource;
    }
}