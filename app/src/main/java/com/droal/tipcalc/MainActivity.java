package com.droal.tipcalc;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.droal.tipcalc.interfaces.ListTipHistoryInterfaz;
import com.droal.tipcalc.models.TipRecordPOJO;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    //Crear las anotaciones
    @BindView(R.id.et_content_tip_bill)
    EditText etContentTipBill;
    @BindView(R.id.et_content_tip_percent)
    EditText etContentTipPercent;
    @BindView(R.id.btn_content_tip_submint)
    Button btnContentTipSubmint;
    @BindView(R.id.btn_content_tip_increase)
    Button btnContentTipIncrease;
    @BindView(R.id.btn_content_tip_decrease)
    Button btnContentTipDecrease;
    @BindView(R.id.btn_content_tip_clear)
    Button btnContentTipClear;
    @BindView(R.id.tv_content_tip_tip)
    TextView tvContentTipTip;

    private static final float DEFAULT_PERCENTAJE = 6.0f;
    private static final float PERCENTAGE_STEP_CHANGE = 1.0f;

    //se crea una instancia de la interfaz
    //Una alternativa al uso de interfaz es instanciar el fragmento y acceder a sus métodos publicos como en clearList()
    private ListTipHistoryInterfaz listTipHistoryInterfaz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instanicar el bind de buterknife
        ButterKnife.bind(this);

        //Dado que el fragmento se encuentra dentro del Layout de la actividad
        //se puede recuperar el fragmento mediante findFragmentById
        //y hacer cast a la interfaz que implementa
        ListTipHistoryFragment listTipHistoryFragment = (ListTipHistoryFragment) getSupportFragmentManager().findFragmentById(R.id.ft_main_list_history);
        //configurar el fragmento para que retenga la instancia y no se esté instanciando cuando se cambie la configuracion o rote lapantalla
        listTipHistoryFragment.setRetainInstance(true);
        listTipHistoryInterfaz = (ListTipHistoryInterfaz)listTipHistoryFragment;

    }

    //Crear la vista del menu empleando el recurso (xml) menu_main
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //Metodo llamado cuando se selecciona un item dentro del menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        ApplicationClass applicationClass = (ApplicationClass) getApplication();
        Uri uri = null;
        //Intent implicito para pasar al navegador disponible en el dispositivo
        Intent intent = new Intent(Intent.ACTION_VIEW);

        //Se determina cual fue el item seleccionado
        switch (item.getItemId()){
            case R.id.action_about_menu:
                uri = Uri.parse(applicationClass.getUrlMenu());
                break;
            case R.id.action_about_strings:
                uri = Uri.parse(applicationClass.getUrlStrings());
                break;
        }

        intent.setData(uri);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    private void about() {

        ApplicationClass applicationClass = (ApplicationClass) getApplication();

        //Intent implicito para pasar al navegador disponible en el dispositivo
        Intent intent = new Intent(Intent.ACTION_VIEW);

        try{
            Uri uri = Uri.parse(applicationClass.getUrlMenu());
            intent.setData(uri);
            startActivity(intent);
        }catch (Exception e){
            Toast.makeText(getApplication(), getString(R.string.main_menu_uri_error), Toast.LENGTH_SHORT).show();
        }
    }


    //Listener de onclick manejado por buterknife
    //Podria ser una anotacion por cada vista
    @OnClick({R.id.btn_content_tip_submint, R.id.btn_content_tip_increase, R.id.btn_content_tip_decrease, R.id.btn_content_tip_clear})
    public void onViewClicked(View view) {

        //Ocultar el teclado
        hideKeyBoard();
        switch (view.getId()) {
            case R.id.btn_content_tip_submint:
                calculateTip();
                break;
            case R.id.btn_content_tip_increase:
                changePercentageTip(PERCENTAGE_STEP_CHANGE);
                break;
            case R.id.btn_content_tip_decrease:
                changePercentageTip(-PERCENTAGE_STEP_CHANGE);
                break;
            case R.id.btn_content_tip_clear:
                clearList();
                break;
        }
    }

    private void clearList() {
       // listTipHistoryInterfaz.clearList();
        ListTipHistoryFragment listTipHistoryFragment = (ListTipHistoryFragment) getSupportFragmentManager().findFragmentById(R.id.ft_main_list_history);
        listTipHistoryFragment.clearList();
    }

    private void changePercentageTip(float stepChange) {
        float actualPercentage = getTipPercentage();
        actualPercentage += stepChange;

        if(actualPercentage > 0){
            etContentTipPercent.setText(String.valueOf(actualPercentage));
        }
    }

    private void calculateTip() {
        String inputTotal = etContentTipBill.getText().toString().trim();

        if (!inputTotal.isEmpty()) {
            double total = Double.parseDouble(inputTotal);
            float tipPercentaje = getTipPercentage();

            TipRecordPOJO tipRecordPOJO = new TipRecordPOJO();
            tipRecordPOJO.setBill(total);
            tipRecordPOJO.setTipPercentage(tipPercentaje);
            tipRecordPOJO.setTimestamp(new Date());

            listTipHistoryInterfaz.addToList(tipRecordPOJO);

            double tip = tipRecordPOJO.geTip();

            //usa la clase String.format para formatear el recurso string pasando el unico parametro del recurso
            String stringTip = String.format(getString(R.string.global_message_tip), tip);


            tvContentTipTip.setText(stringTip);
        }
    }


    private float getTipPercentage() {
        float tipPercentage = DEFAULT_PERCENTAJE;

        String inputTipPercentage = etContentTipPercent.getText().toString().trim();
        if (!inputTipPercentage.isEmpty()) {
            tipPercentage = Float.parseFloat(inputTipPercentage);
        }else{
            etContentTipPercent.setText(String.valueOf(tipPercentage));
        }
        return tipPercentage;
    }

    //Metodo qu eoculta el teclado
    private void hideKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        try {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException ex) {
            Log.e(getLocalClassName(), Log.getStackTraceString(ex));
        }
    }
}
