package com.armyof2.poll4bunk;


import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.armyof2.poll4bunk.MainActivity.addDataSet;
import static com.armyof2.poll4bunk.MainActivity.i;
import static com.armyof2.poll4bunk.MainActivity.j;
import static com.armyof2.poll4bunk.MainActivity.k;
import static com.armyof2.poll4bunk.MainActivity.l;
import static com.armyof2.poll4bunk.MainActivity.m;
import static com.armyof2.poll4bunk.MainActivity.posp;
import static com.armyof2.poll4bunk.MainActivity.servTitle;
import static com.armyof2.poll4bunk.MainActivity.totalpeeps;


public class PollingOverActivity extends AppCompatActivity{

    public static int[] yData = {Integer.parseInt(i), Integer.parseInt(j), Integer.parseInt(k), Integer.parseInt(l)};
    public static PieChart pieChart;
    Description description;
    int timeInterval = 200;
    private String[] xData = {"Yes", "No", "Yes if 80%", "Not decided"};
    private TextView bunkRes;
    private boolean stop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adjustFontScale( getResources().getConfiguration());
        adjustDisplayScale( getResources().getConfiguration());
        setContentView(R.layout.activity_pollingover);

        pieChart = (PieChart) findViewById(R.id.piechart);
        bunkRes = (TextView) findViewById(R.id.bunkres);
        //RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) pieChart.getLayoutParams();
        //params.setMargins(getResources().getDimensionPixelSize(R.dimen.pie_left_margin), getResources().getDimensionPixelSize(R.dimen.pie_top_margin), getResources().getDimensionPixelSize(R.dimen.pie_right_margin), getResources().getDimensionPixelSize(R.dimen.pie_bot_margin));
        //pieChart.setLayoutParams(params);

        description = new Description();
        description.setText("Final Statistics\n\n\n");
        description.setTextSize(14);
        description.setTextColor(Color.WHITE);
        description.setPosition(1010, 1010);

        //set props
        pieChart.setHoleRadius(45f);
        pieChart.setHoleColor(424242);
        pieChart.setTransparentCircleAlpha(50);
        pieChart.setTransparentCircleRadius(55f);
        pieChart.setCenterTextSize(15);
        pieChart.setCenterTextColor(Color.WHITE);
        pieChart.setDescription(description);
        //pieChart.setDrawEntryLabels(true);
        pieChart.setRotationEnabled(true);

        //add data set
        addDataSet(pieChart, yData, xData);

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                if(h.getX() == 0) {
                    if (yData[0] != 0)
                        posp = h.getX();
                    else
                        posp = h.getX() + 1;
                }
                if(h.getX() == 1) {
                    if (yData[1] != 0)
                        posp = h.getX();
                    else
                        posp = h.getX() + 1;
                }
                if(h.getX() == 2) {
                    if (yData[2] != 0)
                        posp = h.getX();
                    else
                        posp = h.getX() + 1;
                }
                if(h.getX() == 3) {
                    if (yData[3] != 0)
                        posp = h.getX();
                    else
                        posp = h.getX() + 1;
                }

                android.util.Log.d("TAG", "onValueSelected: " + e);
                CustomDialogBox cdd=new CustomDialogBox(PollingOverActivity.this);
                cdd.show();
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            }

            @Override
            public void onNothingSelected() {

            }
        });
        //Bunk Result
        android.util.Log.d("TAG1", "run: i = " + i + "  k = " + k);
        if(((float)Integer.parseInt(i)/Integer.parseInt(totalpeeps) * 100) >= 90){
            bunkRes.setText("Huge Success");
            bunkRes.setTextColor(getResources().getColor(R.color.PieChartGreen));
        } else if((((float)Integer.parseInt(i)/Integer.parseInt(totalpeeps) * 100) >= 80) && (((float)Integer.parseInt(k)/Integer.parseInt(totalpeeps) * 100) >= 10)){
            bunkRes.setText("Huge Success");
            bunkRes.setTextColor(getResources().getColor(R.color.PieChartGreen));
        } else if(((float)Integer.parseInt(i)/Integer.parseInt(totalpeeps) * 100) >= 80){
            bunkRes.setText("Success");
            bunkRes.setTextColor(getResources().getColor(R.color.PieChartGreen));
        } else if(((float)Integer.parseInt(i)/Integer.parseInt(totalpeeps) * 100) >= 70){
            bunkRes.setText("Just Fine");
            bunkRes.setTextColor(getResources().getColor(R.color.PieChartYellow));
        } else if(((float)Integer.parseInt(i)/Integer.parseInt(totalpeeps) * 100) >= 60){
            bunkRes.setText("Not that good");
            bunkRes.setTextColor(getResources().getColor(R.color.PieChartYellow));
        } else if(((float)Integer.parseInt(i)/Integer.parseInt(totalpeeps) * 100) >= 50){
            bunkRes.setText("Not that good");
            bunkRes.setTextColor(getResources().getColor(R.color.PieChartYellow));
        } else if(((float)Integer.parseInt(i)/Integer.parseInt(totalpeeps) * 100) >= 20) {
            bunkRes.setText("Failure");
            bunkRes.setTextColor(getResources().getColor(R.color.PieChartPink));
        } else if(((float)Integer.parseInt(i)/Integer.parseInt(totalpeeps) * 100) >= 0) {
            bunkRes.setText("Huge Failure");
            bunkRes.setTextColor(getResources().getColor(R.color.PieChartPink));
        }

        setTitle(servTitle);

        //REALTIME EVEN AFTER OVER:
        Thread thread = new Thread(runnable);
        thread.start();

    }

    public static void addDataSet(PieChart pieChart, int[] yData, String[] xData){

        pieChart.setCenterText("Total Voters: " + totalpeeps + "\nVotes Made: " + m);

        ArrayList<PieEntry> yEntrys = new ArrayList<>();

        for (int i = 0; i < yData.length; i++){
            if(yData[i] != 0)
                yEntrys.add(new PieEntry(yData[i], xData[i]));
        }


        //add data
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "");
        pieDataSet.setValueFormatter(new DecimalRemover(new DecimalFormat("###,###,###")));
        pieDataSet.setSliceSpace(3);
        pieDataSet.setValueTextSize(18);
        pieDataSet.setValueTextColor(Color.WHITE);

        //set colors
        ArrayList<Integer> colors = new ArrayList<>();
        if(yData[0] != 0)
            colors.add(Color.parseColor("#66cdaa"));
        if(yData[1] != 0)
            colors.add(Color.parseColor("#ffb6c1"));
        if(yData[2] != 0)
            colors.add(Color.parseColor("#6495ed"));
        if(yData[3] != 0)
            colors.add(Color.parseColor("#eedd82"));
        pieDataSet.setColors(colors);


        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setTextColor(Color.WHITE);
        legend.setFormSize(11);
        legend.setTextSize(11);
        //legend.setYEntrySpace(10);


        //create pie obj
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
    //---------------------------------------------------------

    Runnable runnable = new Runnable() {
        public void run() {
            while (!stop) {

                try {
                    Thread.sleep(timeInterval);
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            android.util.Log.d("TAG1", "PieChartValuesUpdated");
                            yData = new int[]{Integer.parseInt(i), Integer.parseInt(j), Integer.parseInt(k), Integer.parseInt(l)};
                            addDataSet(pieChart, yData, xData);

                            android.util.Log.d("TAG1", "run: i = " + i + "  m = " + totalpeeps);
                            //Bunk Result Update Realtime
                            if(((float)Integer.parseInt(i)/Integer.parseInt(totalpeeps) * 100) >= 90){
                                bunkRes.setText("Huge Success");
                                bunkRes.setTextColor(getResources().getColor(R.color.PieChartGreen));
                                android.util.Log.d("TAG1", "HS");
                            } else if((((float)Integer.parseInt(i)/Integer.parseInt(totalpeeps) * 100) >= 80) && (((float)Integer.parseInt(k)/Integer.parseInt(totalpeeps) * 100) >= 10)){
                                bunkRes.setText("Huge Success");
                                bunkRes.setTextColor(getResources().getColor(R.color.PieChartGreen));
                                android.util.Log.d("TAG1", "HS");
                            } else if(((float)Integer.parseInt(i)/Integer.parseInt(totalpeeps) * 100) >= 80){
                                bunkRes.setText("Success");
                                bunkRes.setTextColor(getResources().getColor(R.color.PieChartGreen));
                                android.util.Log.d("TAG1", "S");
                            } else if(((float)Integer.parseInt(i)/Integer.parseInt(totalpeeps) * 100) >= 70){
                                bunkRes.setText("Just Fine");
                                bunkRes.setTextColor(getResources().getColor(R.color.PieChartYellow));
                                android.util.Log.d("TAG1", "JS");
                            } else if(((float)Integer.parseInt(i)/Integer.parseInt(totalpeeps) * 100) >= 60){
                                bunkRes.setText("Not that good");
                                bunkRes.setTextColor(getResources().getColor(R.color.PieChartYellow));
                                android.util.Log.d("TAG1", "NTG");
                            } else if(((float)Integer.parseInt(i)/Integer.parseInt(totalpeeps) * 100) >= 50){
                                bunkRes.setText("Not that good");
                                bunkRes.setTextColor(getResources().getColor(R.color.PieChartYellow));
                                android.util.Log.d("TAG1", "NTG");
                            } else if(((float)Integer.parseInt(i)/Integer.parseInt(totalpeeps) * 100) >= 20) {
                                bunkRes.setText("Failure");
                                bunkRes.setTextColor(getResources().getColor(R.color.PieChartPink));
                                android.util.Log.d("TAG1", "F");
                            } else if(((float)Integer.parseInt(i)/Integer.parseInt(totalpeeps) * 100) >= 0) {
                                bunkRes.setText("Huge Failure");
                                bunkRes.setTextColor(getResources().getColor(R.color.PieChartPink));
                                android.util.Log.d("TAG1", "HF");
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        stop = true;
        android.util.Log.d("TAG", "onDestroy: called");
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                Intent i = new Intent(this,SignInActivity.class);
                startActivity(i);
                finish();
                return true;

            case R.id.about:
                i = new Intent(this,Info.class);
                startActivity(i);
                return true;

            case R.id.clog:
                i = new Intent(this,Log.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void adjustFontScale(Configuration configuration) {
        if (configuration != null && configuration.fontScale != 1.0) {
            configuration.fontScale = (float) 1.0;
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(metrics);
            metrics.scaledDensity = configuration.fontScale * metrics.density;
            this.getResources().updateConfiguration(configuration, metrics);
        }
    }

    public void adjustDisplayScale(Configuration configuration) {
        if (configuration != null) {
            android.util.Log.d("TAG", "adjustDisplayScale: " + configuration.densityDpi);
            if(configuration.densityDpi >= 485)
                configuration.densityDpi = 500;
            else if(configuration.densityDpi >= 300)
                configuration.densityDpi = 400;
            else if(configuration.densityDpi >= 100)
                configuration.densityDpi = 200;
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(metrics);
            metrics.scaledDensity = configuration.densityDpi * metrics.density;
            this.getResources().updateConfiguration(configuration, metrics);
        }
    }
}
