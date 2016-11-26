package com.appsport.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appsport.R;
import com.appsport.model.Tabata;

/**
 * @author LEBOC Philippe on 17/10/2016.
 */
public class ProgramView {

    private Context context;

    private Tabata tabata;

    // Layout principal de la vue
    private LinearLayout programSourceLayout;

    // Dispose les elements de maniere verticale dans la vue
    private LinearLayout verticalTextLayout;

    private TextView title;

    private TextView labelPart1;

    private TextView labelPart2;

    public ProgramView(Context context, Tabata tabata) {
        setContext(context);
        setTabata(tabata);

        setProgramSourceLayout(new LinearLayout(context));
        setVerticalTextLayout(new LinearLayout(context));

        // Data
        setTitle(new TextView(context));
        setLabelPart1(new TextView(context));
        setLabelPart2(new TextView(context));

        // Set childrens
        getVerticalTextLayout().addView(getTitle());
        getVerticalTextLayout().addView(getLabelPart1());
        getVerticalTextLayout().addView(getLabelPart2());

        getProgramSourceLayout().addView(getVerticalTextLayout());
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Tabata getTabata() {
        return tabata;
    }

    public void setTabata(Tabata tabata) {
        this.tabata = tabata;
    }

    public LinearLayout getProgramSourceLayout() {
        return programSourceLayout;
    }

    public void setProgramSourceLayout(LinearLayout programSourceLayout) {
        this.programSourceLayout = programSourceLayout;
        this.programSourceLayout.setOrientation(LinearLayout.HORIZONTAL);
        this.programSourceLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public LinearLayout getVerticalTextLayout() {
        return verticalTextLayout;
    }

    public void setVerticalTextLayout(LinearLayout pverticalTextLayout) {
        this.verticalTextLayout = pverticalTextLayout;
        this.verticalTextLayout.setOrientation(LinearLayout.VERTICAL);

        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lparams.setMargins(0, 6, 0, 6);
        this.verticalTextLayout.setLayoutParams(lparams);
        this.verticalTextLayout.setBackgroundColor(getContext().getResources().getColor(R.color.colorGreenPrimary, getContext().getTheme()));
        this.verticalTextLayout.setPadding(5, 5, 5, 5);
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
        this.title.setText(getTabata().getName());
        this.title.setTypeface(null, Typeface.BOLD);
        this.title.setTextColor(Color.WHITE);
    }

    public TextView getLabelPart1() {
        return labelPart1;
    }

    public void setLabelPart1(TextView labelPart1) {
        this.labelPart1 = labelPart1;
        this.labelPart1.setTextColor(Color.WHITE);
        this.labelPart1.setText("x" +
            getTabata().getMaxTabata() + " Tabatas et x" +
            getTabata().getMaxCycleCount() + " Cycles");
    }

    public TextView getLabelPart2() {
        return labelPart2;
    }

    public void setLabelPart2(TextView labelPart2) {
        this.labelPart2 = labelPart2;
        this.labelPart2.setTextColor(Color.WHITE);
        this.labelPart2.setText(
            getTabata().getMaxWorkTime() + "s Work + " +
            getTabata().getMaxRestTime() + "s Rest + " +
            getTabata().getPrepareTime() + "s Prepare");
    }
}
