package com.example.a12902.myapplication.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a12902.myapplication.R;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;

import java.util.ArrayList;
import java.util.List;

public class RiLiActivity extends AppCompatActivity implements CalendarView.OnDateSelectedListener, CalendarView.OnYearChangeListener {
    TextView mTextMonthDay;//几月几号

    TextView mTextYear;//哪一年

    TextView mTextLunar;//农历多少号

    TextView mTextCurrentDay;//几号

    CalendarView mCalendarView;//日历

    RelativeLayout mRelativeTool;
    private int mYear;//今年

    CalendarLayout mCalendarLayout;
    private int curMonth;//今月
    private int curDay;//今天
    private String curLunar;//农历今天


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ri_li);
        initView();
        initData();
    }

    private void initView() {
        mTextMonthDay = (TextView) findViewById(R.id.tv_month_day);
        mTextYear = (TextView) findViewById(R.id.tv_year);
        mTextLunar = (TextView) findViewById(R.id.tv_lunar);
        mRelativeTool = (RelativeLayout) findViewById(R.id.rl_tool);
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        mTextCurrentDay = (TextView) findViewById(R.id.tv_current_day);
        mTextMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCalendarLayout.isExpand()) {
                    mCalendarView.showYearSelectLayout(mYear);
                    return;
                }
                mCalendarView.showYearSelectLayout(mYear);
                mTextLunar.setVisibility(View.GONE);
                mTextYear.setVisibility(View.GONE);
                mTextMonthDay.setText(String.valueOf(mYear));
            }
        });
        findViewById(R.id.fl_current).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarView.scrollToCurrent();
            }
        });

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Calendar> schemes = new ArrayList<>();
                int year = mCalendarView.getCurYear();
                int month = mCalendarView.getCurMonth();

                schemes.add(getSchemeCalendar(year, month, 1, 0xFF40db25, "假",0));
                schemes.add(getSchemeCalendar(year, month, 6, 0xFFe69138, "草",1));
                schemes.add(getSchemeCalendar(year, month, 19, 0xFFedc56d, "记",0));
                schemes.add(getSchemeCalendar(year, month, 17, 0xFFaacc44, "假",0));
                schemes.add(getSchemeCalendar(year, month, 22, 0xFF13acf0, "假",1));
                schemes.add(getSchemeCalendar(year, month, 30, 0xFF13acf0, "多",0));
                mCalendarView.setSchemeDate(schemes);
            }
        });
        mCalendarLayout = (CalendarLayout) findViewById(R.id.calendarLayout);
        mCalendarView.setOnDateSelectedListener(this);
        mCalendarView.setOnYearChangeListener(this);
        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        curMonth = mCalendarView.getCurMonth();
        curDay = mCalendarView.getCurDay();
        curLunar = mCalendarView.getCurLunar();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(String.valueOf(mCalendarView.getCurDay()));
    }


    private void initData() {
        List<Calendar> schemes = new ArrayList<>();
        int year = mCalendarView.getCurYear();
        int month = mCalendarView.getCurMonth();

        schemes.add(getSchemeCalendar(year, month, 3, 0xFF40db25, "假",0));
        schemes.add(getSchemeCalendar(year, month, 6, 0xFFe69138, "事",0));
        schemes.add(getSchemeCalendar(year, month, 10, 0xFFdf1356, "议",1));
        schemes.add(getSchemeCalendar(year, month, 11, 0xFFedc56d, "记",1));
        schemes.add(getSchemeCalendar(year, month, 14, 0xFFedc56d, "记",0));
        schemes.add(getSchemeCalendar(year, month, 15, 0xFFaacc44, "假",0));
        schemes.add(getSchemeCalendar(year, month, 18, 0xFFbc13f0, "记",0));
        schemes.add(getSchemeCalendar(year, month, 25, 0xFF13acf0, "假",1));
        schemes.add(getSchemeCalendar(year, month, 27, 0xFF13acf0, "多",0));
        mCalendarView.setSchemeDate(schemes);
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text,int type) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setType(type);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        calendar.addScheme(new Calendar.Scheme());
        calendar.addScheme(0xFF008800, "假");
        calendar.addScheme(0xFF008800, "节");
        return calendar;
    }

    @Override
    public void onDateSelected(Calendar calendar, boolean isClick) {
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        if (isClick) {
            Toast.makeText(this, "点击改变", Toast.LENGTH_SHORT).show();
            //点击改变日历数据
            mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
            mTextLunar.setText(calendar.getLunar());
            mTextCurrentDay.setText(calendar.getDay() + "");
            Toast.makeText(this, calendar.getYear()+"年"+calendar.getMonth() + "月" + calendar.getDay() + "日", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "滑动改变", Toast.LENGTH_SHORT).show();
            //滑动改变日历数据
            if (curMonth == calendar.getMonth()) {
                //如果滑动到当月
                mTextMonthDay.setText(curMonth + "月" + curDay + "日");//设置阳历今天
                mTextLunar.setText(curLunar);//设置农历今天
                mTextCurrentDay.setText(curDay + "");
            } else {
                //如果滑动到其他月 基本设置为其他月的1号
                mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
                mTextLunar.setText(calendar.getLunar());
                mTextCurrentDay.setText(calendar.getDay() + "");
            }
        }

        mTextYear.setText(String.valueOf(calendar.getYear()));
        mYear = calendar.getYear();
    }

    @Override
    public void onYearChange(int year) {
        Toast.makeText(this, "onYearChange", Toast.LENGTH_SHORT).show();
        mTextMonthDay.setText(String.valueOf(year));
    }
}
