package endorphine.icampyou.GuideMenu.Reservation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.yongbeom.aircalendar.core.AirMonthAdapter;
import com.yongbeom.aircalendar.core.DatePickerController;
import com.yongbeom.aircalendar.core.DayPickerView;
import com.yongbeom.aircalendar.core.SelectModel;
import com.yongbeom.aircalendar.core.util.AirCalendarUtils;
import org.joda.time.DateTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import endorphine.icampyou.R;

public class CalenderActivity extends AppCompatActivity implements DatePickerController{

    public String EXTRA_ACTIVE_MONTH_NUM = "ACTIVE_MONTH_NUMBER";
    public String EXTRA_MAX_YEAR = "MAX_YEAR";

    public String RESULT_SELECT_START_DATE = "start_date";
    public String RESULT_SELECT_END_DATE = "end_date";
    public String RESULT_SELECT_START_VIEW_DATE = "start_date_view";
    public String RESULT_SELECT_END_VIEW_DATE = "end_date_view";
    public String RESULT_STATE = "result_state";

    public String RESULT_STAY_LENGTH = "stay_length";

    public String CampingName;

    private DayPickerView pickerView;
    private TextView tv_start_date;
    private TextView tv_end_date;
    private TextView tv_popup_msg;
    private RelativeLayout rl_done_btn;
    private RelativeLayout rl_popup_select_checkout_info_ok;
    private RelativeLayout rl_checkout_select_info_popup;
    private RelativeLayout rl_iv_back_btn_bg;

    private String SELECT_START_DATE = "";
    private String SELECT_END_DATE = "";
    private int BASE_YEAR = 2018;

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

        maxActivieMonth = getData.getIntExtra(EXTRA_ACTIVE_MONTH_NUM , 2);
        maxYear = getData.getIntExtra(EXTRA_MAX_YEAR , 2018);

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

                Intent resultIntent = new Intent(CalenderActivity.this, TentSelectActivity.class);
                resultIntent.putExtra(RESULT_SELECT_START_DATE , SELECT_START_DATE );
                resultIntent.putExtra(RESULT_SELECT_END_DATE , SELECT_END_DATE );
                resultIntent.putExtra(RESULT_SELECT_START_VIEW_DATE , tv_start_date.getText().toString() );
                resultIntent.putExtra(RESULT_SELECT_END_VIEW_DATE , tv_end_date.getText().toString() );
                resultIntent.putExtra(RESULT_STATE , "done" );
                resultIntent.putExtra("camping_name", CampingName);
                resultIntent.putExtra("stay_length", RESULT_STAY_LENGTH);
                Log.e("CalendarActivity", CampingName);
                Log.e("CalendarActivity", "1");
                setResult(RESULT_OK , resultIntent);
                startActivity(resultIntent);
                //finish();
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

            RESULT_STAY_LENGTH = Integer.toString(calculateStayLength(start_month_int, start_day_int, end_month_int, end_day_int));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int calculateStayLength(int startMonth, int startDay, int endMonth, int endDay) {
        if(startMonth == endMonth)
            return endDay - startDay;
        else
            if(startMonth == 1 || startMonth == 3 || startMonth == 5 || startMonth == 7 || startMonth == 8 || startMonth == 10 || startMonth == 12)
                return endDay + (31 - startDay);
            else if(startDay == 2)
                return endDay + (28 - startDay);
            else
                return endDay + (30 - startDay);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            if(data != null) {
                Toast.makeText(this, "Select Data range" + data.getStringExtra(RESULT_SELECT_START_DATE) + "~" + data.getStringExtra(RESULT_SELECT_END_DATE), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onPause() {
        super.onPause();
    }
}
