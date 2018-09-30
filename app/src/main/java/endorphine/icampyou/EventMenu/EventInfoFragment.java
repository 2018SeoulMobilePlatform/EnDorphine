package endorphine.icampyou.EventMenu;


import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import endorphine.icampyou.BaseFragment;
import endorphine.icampyou.GlideApp;
import endorphine.icampyou.R;

/**
 * 이벤트 상세정보 페이지 프래그먼트
 */
public class EventInfoFragment extends BaseFragment {

    View view;
    TextView eventTitle;
    TextView eventContent;
    ImageView eventImage;
    int mPosition;

    public EventInfoFragment() {
    }

    public static EventInfoFragment newInstance(int position) {
        EventInfoFragment fragment = new EventInfoFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);

        return fragment;
    }

    // 프래그먼트 xml 설정하는 메소드
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 먼저 view에 xml 설정
        view = inflater.inflate(R.layout.fragment_event_info, container, false);

        eventTitle = view.findViewById(R.id.event_info_title);
        eventContent = view.findViewById(R.id.event_info_content);
        eventImage = view.findViewById(R.id.event_info_image);

        if (getArguments() != null) {
            mPosition = getArguments().getInt("position");
            Log.e("position", mPosition + "");
        }

        // 포지션에 따라 이벤트 상세 내용 다르게 셋팅
        switch (mPosition) {
            case 0:
                eventTitle.setText("2018년 서울동물원 대탐험(1박 2일 가족캠프)에 참여하세요!");
                eventContent.setText("안녕하세요? 서울대공원입니다.\n" + "선선한 가을바람 부는 계절! 캠핑하기 좋은 계절!\n" +
                        "서울동물원에서 가족과 함께 특별한 캠핑에 참여하세요!\n" + "선착순접수이오니 서둘러 신청바랍니다.\n" + "\n" + "\n" + "\n" +
                        "○ 1차 9.22(토)~23(일), 2차 9.29(토)~30(일)\n" + "○ 3차 10.6(토)~7(일), 4차 10.8(월)~9(화)");
                GlideApp.with(this).load(R.drawable.event_info1).into(eventImage);
                break;
            case 1:
                eventTitle.setText("즐거운 추석행사 '동물원 한가위 한마당' 개최");
                eventContent.setText("안녕하세요? 서울대공원입니다.\n" +
                        "서울대공원에서는 추석명절을 맞아,\n" +
                        "'18.9. 24.(월) ~ 9.25(화)' \"동물원 한가위 한마당\"을 개최합니다.\n" +
                        "온가족이 함께 즐길 수 있는 즐거운 프로그램들을 준비하였으니\n" +
                        "서울대공원에서 동물과 자연과 함께~ 즐거운 추억을 만드시기 바랍니다.\n" +
                        " \n" +
                        "\n" +
                        "보름달처럼 풍성한 동물원 큰 잔치!\n" +
                        "추석명절엔 서울대공원에서 함께하세요~!\n" +
                        " \n" +
                        "\n" +
                        "○ 일시 : 9.24(월) ~ 9.25(일) / 12시~18시\n" +
                        "○ 장소 : 동물원 정문광장\n" +
                        "○ 내용 : 한복입기, 전통놀이 등 다양한 명절 체험프로그램");
                GlideApp.with(this).load(R.drawable.event_info2).into(eventImage);
                break;
            case 2:
                eventTitle.setText("중랑캠핑숲! 2019년도 상반기 『시민의숲 꽃길 결혼식』 참여자를 모집합니다.");
                eventContent.setText("아름다운 봄날, 꽃들이 만발한 공원에서 만들어가는 소박하지만 특별한 결혼식.\n" +
                        "동부공원녹지사업소는 2019년 상반기(4월~6월)에 『시민의숲 꽃길 결혼식』에 참여할 예비부부 25쌍을 오는 10월 1일부터 15일까지 보름간 모집합니다.\n" +
                        "\n" +
                        " ※『시민의숲 꽃길 결혼식』 : 양재시민의숲 공원에서 진행하는 친환경 작은 결혼식의 브랜드 이름입니다.\n" +
                        "\n" +
                        "\n" +
                        "양재시민의숲 야외 예식장은 울창한 숲으로 둘러싸인 자연환경, 1일 1식으로 진행되는 예식 등 일반 예식장에서 누릴 수 없는 특별함이 있어 결혼을 앞둔 예비 부부들에게 인기 있는 곳이지요. 대관료가 무료인데다 하객용 파라솔과 테이블 등이 비치되어 있어 특별하면서도 알뜰한 결혼식을 준비하고자 하는 이들에게 더 없이 안성맞춤한 공간입니다.\n" +
                        "\n"+"기존에는 신청만 하면 누구나 자유롭게 이용할 수 있었는데요.\n" +
                        "올해부터는 정말로, 소규모로 공원에 어울리는 친환경 작은 결혼식을 진행하실 분들께만 개방합니다.\n" +"\n"+
                        "하객규모는 120명 내외여야 하고, 예식 진행은 서울시가 지정한 4개의 작은 결혼식 협력업체와 해야 합니다.\n"+
                        "이 밖에도 ▲피로연 음식 간소화 및 화기 사용 금지, ▲축하화환 반입 금지, ▲일회용품 사용 최소화 등의 운영 원칙이 있어요.\n" +
                        "\n"+"자세한 내용은 붙임 공고문 참고하시고, 저희 시민의숲 꽃길 결혼식에 관심있는 예비 부부들의 많은 참여 바랍니다.\n");
                GlideApp.with(this).load(R.drawable.event_info3).into(eventImage);
                break;
            default:
                eventTitle.setText("★난지캠핑장 여름맞이 COOL한 8월 할인 프로모션! ★");
                eventContent.setText(" 6월 27일 오전 11시 ~ 7월 2일 오전 10시 해당 기간에 한하여 (8월 예매)를 최대"+
                        "30% 할인을 적용 받을 수 있는 절호의 기회! (토요일, 광복절 전날 제외)\n" +
                        "다시 없을 기회를 놓치지 마세요!!\n" + "\n" +
                        "(특정 기간 외에는 정가가 판매되오니, 예매에 참고 부탁드립니다.)");
                GlideApp.with(this).load(R.drawable.event_info4).into(eventImage);
                break;
        }

        return view;
    }

}
