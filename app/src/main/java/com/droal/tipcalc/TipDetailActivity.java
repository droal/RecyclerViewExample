package com.droal.tipcalc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TipDetailActivity extends AppCompatActivity {


    @BindView(R.id.tv_detail_total)
    TextView tvDetailTotal;
    @BindView(R.id.tv_detail_tip)
    TextView tvDetailTip;
    @BindView(R.id.tv_detail_timestamp)
    TextView tvDetailTimestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_detail);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String total = String.format(getString(R.string.detail_message_bill),
                intent.getDoubleExtra(ListTipHistoryFragment.BILL_TOTAL_KEY, 0d));
        String tip = String.format(getString(R.string.global_message_tip),
                intent.getDoubleExtra(ListTipHistoryFragment.TIP_KEY, 0d));

        tvDetailTotal.setText(total);
        tvDetailTip.setText(tip);
        tvDetailTimestamp.setText(intent.getStringExtra(ListTipHistoryFragment.DATE_KEY));
    }
}
