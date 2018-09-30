package endorphine.icampyou;

public class Constant {

    //NetworkTask 클래스

    //사용자 등록 경우
    public static final int USER_REGISTER = 1111;

    //사용자 로그인 경우
    public static final int USER_LOGIN = 1112;

    //사용자 로그인 경우
    public static final int USER_FIND_INFO = 1113;

    //이메일 아이디 중복되는 경우
    public static final int DUPLICATED_EMAIL = 1114;

    //닉네임 중복되는 경우
    public static final int DUPLICATED_NICKNAME = 1115;

    //핸드폰 중복되는 경우
    public static final int DUPLICATED_PHONENUMBER = 1116;

    //채팅방 개설
    public static final int MAKE_CHATTINGLIST = 1117;

    //후기 작성
    public static final int MAKE_REVIEWLIST = 1118;

    //후기 정보 가져오기
    public static final int GET_REVIEWLIST = 1119;

    //채팅방 목록 가져오기
    public static final int GET_CHATTINGLIST = 1120;

    //예약
    public static final int RESERVATION_CAMPING = 1121;

    //예약 정보 가져오기
    public static final int GET_RESERVATION_INFO = 1122;

    //사용자 정보 수정
    public static final int MODIFY_USER_INFO = 1123;

    //닉네임 중복되는 경우2
    public static final int DUPLICATED_NICKNAME2 = 1124;

    //채팅방 정보 불러오기
    public static final int GET_CHATTINGMESSAGELIST = 1125;

    //사용자 채팅방 삭제
    public static final int REMOVE_CHATTINGLIST = 1126;

    //채팅 상대방 설정
    public static final int SET_OPPONENT = 1127;

    //채팅 상대방 값 가져오기
    public static final int GET_OPPONENT = 1128;

    //사용자 플래그 설정하기
    public static final int SET_FLAG = 1129;

    //채팅방 플래그 설정
    public static  final int SET_CHATTINGFLAG = 1130;

    //카메라 접근 권한 변수
    public static int permissionCheck_Camera;
    public static int permissionCheck_Write;
    public static int permissionCheck_Read;

    //Camera 클래스
    public static final int CAMERA_CODE = 1000;
    public static final int GALLERY_CODE = 1001;
    public static final int REQUEST_PERMISSION_CODE_CAMERA = 2000;
    public static final int REQUEST_PERMISSION_CODE_GALLERY = 2001;

    //캠핑장 종류별 요청 코드
    public static final int NANJI = 1;
    public static final int SEOUL = 2;
    public static final int NOEUL = 3;
    public static final int JUNGRANG= 4;
    public static final int CHOANSAN = 5;
    public static final int GANGDONG= 6;

}
