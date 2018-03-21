package com.droal.tipcalc;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.droal.tipcalc.adapters.TipAdapter;
import com.droal.tipcalc.interfaces.ItemClickInterfaz;
import com.droal.tipcalc.interfaces.ListTipHistoryInterfaz;
import com.droal.tipcalc.models.TipRecordPOJO;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListTipHistoryFragment extends Fragment implements ListTipHistoryInterfaz, ItemClickInterfaz {


    @BindView(R.id.rv_fragm_tip_history_listado)

    RecyclerView recyclerView;

    private TipAdapter adapter;

    public final static String TIP_KEY = "tip";
    public final static String DATE_KEY = "timestamp";
    public final static String BILL_TOTAL_KEY = "total";

    //OJO dejar esta declaración al final
    //Si se antecede a otra declaración crea conflicto con esa declaracion
    Unbinder unbinder;

    public ListTipHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_tip_history, container, false);
        ButterKnife.bind(this, view);
        unbinder = ButterKnife.bind(this, view);

        initAdapter();
        initRecyclerView();
        
        return view;
    }

    private void initAdapter() {
        if(adapter == null){
            adapter = new TipAdapter(getActivity().getApplicationContext(), this);
        }
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void addToList(TipRecordPOJO tipRecordPOJO) {
        adapter.addRecord(tipRecordPOJO);
    }

 //   @Override
    public void clearList() {
        adapter.clear();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void itemClick(TipRecordPOJO tipRecordPOJO) {
        Intent intDetail = new Intent(getActivity(), TipDetailActivity.class);
        intDetail.putExtra(TIP_KEY, tipRecordPOJO.geTip());
        intDetail.putExtra(BILL_TOTAL_KEY, tipRecordPOJO.getBill());
        intDetail.putExtra(DATE_KEY, tipRecordPOJO.getDateFormatted());
        startActivity(intDetail);
    }
}
