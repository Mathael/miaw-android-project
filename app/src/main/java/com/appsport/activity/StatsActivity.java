package com.appsport.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.appsport.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {

    // we're going to display pie chart for school attendance
    private int[] yValues = {21, 2, 2};
    private String[] xValues = {"Present Days", "Absents", "Leaves"};

    // colors for different sections in pieChart
    public static  final int[] MY_COLORS = {
            Color.rgb(84,124,101), Color.rgb(64,64,64), Color.rgb(153,19,0),
            Color.rgb(38,40,53), Color.rgb(215,60,55)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);


        final PieChart chart = (PieChart) findViewById(R.id.chart);
        //   mChart.setUsePercentValues(true);
        chart.setDescription(new Description());
        chart.setRotationEnabled(true);
        setDataForPieChart(chart);

    }

    public void setDataForPieChart(PieChart mChart) {
        ArrayList<PieEntry> yVals1 = new ArrayList<>();

        for (int i = 0; i < yValues.length; i++)
            yVals1.add(new PieEntry(yValues[i], i));

        ArrayList<String> xVals = new ArrayList<>();

        for (int i = 0; i < xValues.length; i++)
            xVals.add(xValues[i]);

        // create pieDataSet
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // adding colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        // Added My Own colors
        for (int c : MY_COLORS)
            colors.add(c);


        dataSet.setColors(colors);

        //  create pie data object and set xValues and yValues and set it to the pieChart
        PieData data = new PieData(dataSet);
        //   data.setValueFormatter(new DefaultValueFormatter());
        //   data.setValueFormatter(new PercentFormatter());

        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);

        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        // refresh/update pie chart
        mChart.invalidate();

        // animate piechart
        mChart.animateXY(1400, 1400);


        // Legends to show on bottom of the graph
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);
    }
}
