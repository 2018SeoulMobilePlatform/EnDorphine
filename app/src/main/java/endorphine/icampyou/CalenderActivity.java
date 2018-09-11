package endorphine.icampyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yongbeom.aircalendar.core.DatePickerController;
import com.yongbeom.aircalendar.core.DayPickerView;
import com.yongbeom.aircalendar.core.AirMonthAdapter;
import com.yongbeom.aircalendar.core.SelectModel;
import com.yongbeom.aircalendar.core.util.AirCalendarUtils;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalenderActivity extends AppCompatActivity implements DatePickerController{

    public final static String EXTRA_FLAG = "FLAG";
    public final static String EXTRA_IS_BOOIKNG = "IS_BOOING";
    public final static String EXTRA_IS_SELECT = "IS_SELECT";
    public final static String EXTRA_BOOKING_DATES = "BOOKING_DATES";
    public final static String EXTRA_SELECT_DATE_SY = "SELECT_START_DATE_Y";
    public final static String EXTRA_SELECT_DATE_SM = "SELECT_START_DATE_M";
    public final static String EXTRA_SELECT_DATE_SD = "SELECT_START_DATE_D";
    public final static String EXTRA_SELECT_DATE_EY = "SELECT_END_DATE_Y";
    public final static String EXTRA_SELECT_DATE_EM = "SELECT_END_DATE_M";
    public final static String EXTRA_SELECT_DATE_ED = "SELECT_END_DATE_D";
    public final static String EXTRA_IS_MONTH_LABEL = "IS_MONTH_LABEL";
    public final static String EXTRA_IS_SINGLE_SELECT = "IS_SINGLE_SELECT";
    public final static String EXTRA_ACTIVE_MONTH_NUM = "ACTIVE_MONTH_NUMBER";
    public final static String EXTRA_MAX_YEAR = "MAX_YEAR";

    public final static String RESULT_SELECT_START_DATE = "start_date";
    public final static String RESULT_SELECT_END_DATE = "end_date";
    public final static String RESULT_SELECT_START_VIEW_DATE = "start_date_view";
    public final static String RESULT_SELECT_END_VIEW_DATE = "end_date_view";
    public final static String RESULT_FLAG = "flag";
    public final static String RESULT_TYPE = "result_type";
    public final static String RESULT_STATE = "result_state";

    public String CampingName;

    private DayPickerView pickerView;
    private TextView tv_start_date;
    private TextView tv_end_date;
    private TextView tv_popup_msg;
    private RelativeLayout rl_done_btn;
    private RelativeLayout rl_reset_btn;
    private RelativeLayout rl_popup_select_checkout_info_ok;
    private RelativeLayout rl_checkout_select_info_popup;
    private RelativeLayout rl_iv_back_btn_bg;

    private String SELECT_START_DATE = "";
    private String SELECT_END_DATE = "";
    private int BASE_YEAR = 2018;

    private String FLAG = "all";
    private boolean isSelect = false;
    private boolean isBooking = false;
    private boolean isMonthLabel = false;
    private boolean isSingleSelect = false;
    private ArrayList<String> dates;
    private SelectModel selectDate;

    private int sYear = 0;
    private int sMonth = 0;
    private int sDay = 0;
    private int eYear = 0;
    private int eMonth = 0;
    private int eDay = 0;

    private int maxActivieMonth = -1;
    private int maxYear = 2018;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super .onCreate(savedInstanceState);
        setContentView(R.layout.aircalendar_activity_date_picker);

        Intent getData = getIntent();

        CampingName = getData.getStringExtra("title");

        FLAG = getData.getStringExtra(EXTRA_FLAG) != null ? getData.getStringExtra(EXTRA_FLAG):"all";
        isBooking = getData.getBooleanExtra(EXTRA_IS_BOOIKNG , false);
        isSelect = getData.getBooleanExtra(EXTRA_IS_SELECT , false);
        isMonthLabel = getData.getBooleanExtra(EXTRA_IS_MONTH_LABEL , false);
        isSingleSelect = getData.getBooleanExtra(EXTRA_IS_SINGLE_SELECT , false);
        dates = getData.getStringArrayListExtra(EXTRA_BOOKING_DATES);
        maxActivieMonth = getData.getIntExtra(EXTRA_ACTIVE_MONTH_NUM , -1);
        maxYear = getData.getIntExtra(EXTRA_MAX_YEAR , 2018);

        sYear = getData.getIntExtra(EXTRA_SELECT_DATE_SY , 0);
        sMonth = getData.getIntExtra(EXTRA_SELECT_DATE_SM , 0);
        sDay = getData.getIntExtra(EXTRA_SELECT_DATE_SD , 0);

        eYear = getData.getIntExtra(EXTRA_SELECT_DATE_EY , 0);
        eMonth = getData.getIntExtra(EXTRA_SELECT_DATE_EM , 0);
        eDay = getData.getIntExtra(EXTRA_SELECT_DATE_ED , 0);

        if(sYear == 0 || sMonth == 0 || sDay == 0
                || eYear == 0 || eMonth == 0 || eDay == 0){
            selectDate = new SelectModel();
            isSelect = false;
        }

        init();

    }

    private void init(){

        rl_done_btn = findViewById(R.id.rl_done_btn);
        tv_start_date = findViewById(R.id.tv_start_date);
        tv_end_date = findViewById(R.id.tv_end_date);
        tv_popup_msg = findViewById(R.id.tv_popup_msg);
        rl_checkout_select_info_popup = findViewById(R.id.rl_checkout_select_info_popup);
        rl_reset_btn = findViewById(R.id.rl_reset_btn);
        rl_popup_select_checkout_info_ok = findViewById(R.id.rl_popup_select_checkout_info_ok);
        rl_checkout_select_info_popup = findViewById(R.id.rl_checkout_select_info_popup);
        rl_iv_back_btn_bg = findViewById(R.id.rl_iv_back_btn_bg);

        pickerView = findViewById(R.id.pickerView);
        pickerView.setIsMonthDayLabel(isMonthLabel);
        pickerView.setIsSingleSelect(isSingleSelect);
        pickerView.setMaxActiveMonth(maxActivieMonth);

        SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy", Locale.KOREA );
        Date currentTime = new Date ( );
        String dTime = formatter.format ( currentTime );

        if(maxYear != -1 && maxYear > Integer.parseInt(new DateTime().toString("yyyy"))){
            BASE_YEAR = maxYear;
        }else{
            // default : now year + 1 year
            BASE_YEAR = Integer.valueOf(dTime) + 1;
        }

        if(dates != null && dates.size() != 0 && isBooking){
            pickerView.setShowBooking(true);
            pickerView.setBookingDateArray(dates);
        }

        if(isSelect){
            selectDate = new SelectModel();
            selectDate.setSelectd(true);
            selectDate.setFristYear(sYear);
            selectDate.setFristMonth(sMonth);
            selectDate.setFristDay(sDay);
            selectDate.setLastYear(2018);
            selectDate.setLastMonth(eMonth);
            selectDate.setLastDay(eDay);
            pickerView.setSelected(selectDate);
        }

        pickerView.setController(this);


        rl_done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((SELECT_START_DATE == null || SELECT_START_DATE.equals("")) && (SELECT_END_DATE == null || SELECT_END_DATE.equals(""))){
                    SELECT_START_DATE = "";
                    SELECT_END_DATE = "";
                    tv_popup_msg.setText("이용기간을 선택해주세요");
                    rl_checkout_select_info_popup.setVisibility(View.VISIBLE);
                    return;
                }else{
                    if(SELECT_START_DATE == null || SELECT_START_DATE.equals("")){
                        tv_popup_msg.setText("이용기간을 선택해주세요");
                        rl_checkout_select_info_popup.setVisibility(View.VISIBLE);
                        return;
                    }else if(SELECT_END_DATE == null || SELECT_END_DATE.equals("")){
                        tv_popup_msg.setText("이용기간을 선택해주세요");
                        rl_checkout_select_info_popup.setVisibility(View.VISIBLE);
                        return;
                    }
                }


                Intent resultIntent = new Intent(CalenderActivity.this, DetailsActivity.class);
                resultIntent.putExtra(RESULT_SELECT_START_DATE , SELECT_START_DATE );
                resultIntent.putExtra(RESULT_SELECT_END_DATE , SELECT_END_DATE );
                resultIntent.putExtra(RESULT_SELECT_START_VIEW_DATE , tv_start_date.getText().toString() );
                resultIntent.putExtra(RESULT_SELECT_END_VIEW_DATE , tv_end_date.getText().toString() );
                resultIntent.putExtra(RESULT_FLAG , FLAG );
                resultIntent.putExtra(RESULT_TYPE , FLAG );
                resultIntent.putExtra(RESULT_STATE , "done" );
                resultIntent.putExtra("camping_name", CampingName);
                setResult(RESULT_OK , resultIntent);
                startActivity(resultIntent);
                //finish();
            }
        });

        rl_reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SELECT_START_DATE = "";
                SELECT_END_DATE = "";
                setContentView(R.layout.aicalendar_activity_date_picker);
                init();
            }
        });

        rl_popup_select_checkout_info_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_checkout_select_info_popup.setVisibility(View.GONE);
            }
        });

        rl_iv_back_btn_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public int getMaxYear() {
        return BASE_YEAR;
    }

    @Override
    public void onDayOfMonthSelected(int year, int month, int day) {
        // TODO Single Select Event
        try{
            String start_month_str =  String.format("%02d" , (month+1));
            // 일
            String start_day_str =  String.format("%02d" , day);
            String startSetDate = year+start_month_str+start_day_str;

            String startDateDay = AirCalendarUtils.getDateDay(startSetDate , "yyyyMMdd");

            tv_start_date.setText(year+"-"+start_month_str+"-"+start_day_str + " " + startDateDay);
            tv_start_date.setTextColor(0xff4a4a4a);

            tv_end_date.setText("-");
            tv_end_date.setTextColor(0xff1abc9c);
            SELECT_END_DATE = "";
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDateRangeSelected(AirMonthAdapter.SelectedDays<AirMonthAdapter.CalendarDay> selectedDays) {

        try{
            Calendar cl = Calendar.getInstance();

            cl.setTimeInMillis(selectedDays.getFirst().getDate().getTime());

            // 월
            int start_month_int = (cl.get(Calendar.MONTH)+1);
            String start_month_str =  String.format("%02d" , start_month_int);

            // 일
            int start_day_int = cl.get(Calendar.DAY_OF_MONTH);
            String start_day_str =  String.format("%02d" , start_day_int);

            String startSetDate = cl.get(Calendar.YEAR)+start_month_str+start_day_str;
            String startDateDay = AirCalendarUtils.getDateDay(startSetDate , "yyyyMMdd");
            String startDate = cl.get(Calendar.YEAR) + "-" + start_month_str + "-" + start_day_str;

            cl.setTimeInMillis(selectedDays.getLast().getDate().getTime());

            // 월
            int end_month_int = (cl.get(Calendar.MONTH)+1);
            String end_month_str = String.format("%02d" , end_month_int);

            // 일
            int end_day_int = cl.get(Calendar.DAY_OF_MONTH);
            String end_day_str = String.format("%02d" , end_day_int);

            String endSetDate = cl.get(Calendar.YEAR)+end_month_str+end_day_str;
            String endDateDay = AirCalendarUtils.getDateDay(endSetDate , "yyyyMMdd");
            String endDate = cl.get(Calendar.YEAR) + "-" + end_month_str + "-" + end_day_str;

            tv_start_date.setText(startDate + " " + startDateDay);
            tv_start_date.setTextColor(0xff4a4a4a);

            tv_end_date.setText(endDate + " " + endDateDay);
            tv_end_date.setTextColor(0xff4a4a4a);

            SELECT_START_DATE = startDate;
            SELECT_END_DATE = endDate;

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if(data != null) {
                Toast.makeText(this, "Select Data range" + data.getStringExtra(CalenderActivity.RESULT_SELECT_START_DATE) + "~" + data.getStringExtra(CalenderActivity.RESULT_SELECT_END_DATE), Toast.LENGTH_SHORT).show();
            }
        }
    }

}
