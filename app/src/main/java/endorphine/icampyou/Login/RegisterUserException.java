package endorphine.icampyou.Login;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterUserException {

    //이메일 검사하는 함수
    public boolean EmailException(String value){
        Pattern pattern = Pattern.compile("^[a-z0-9._%+-]+@[A-Z0-9.-]+\\.[a-z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }

    // 사용자 비밀번호 예외처리하는 함수 (소문자,숫자,특수문자 입력가능)
    public boolean UserPassWordExcepiton(String value)
    {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }

    //사용자 이름 예외처리하는 함수
    public boolean UserNameException(String value){
        Pattern pattern = Pattern.compile("^[a-zA-Z가-힣0-9]{2,15}$");
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }

    //사용자 닉네임 예외처리하는 함수
    public boolean UserNickNameException(String value){
        Pattern pattern = Pattern.compile("^[a-zA-Z가-힣0-9]{2,15}$");
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }

    //사용자 핸드폰 예외처리하는 함수
    public boolean UserPhoneException(String value){
        Pattern pattern = Pattern.compile("01{1}[016789]{1}[0-9]{8}");
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }
}
