package com.droal.tipcalc.interfaces;

import com.droal.tipcalc.models.TipRecordPOJO;

/**
 * Created by aleydario on 28/03/17.
 *
 * Esta interfaz permite a la actividad MainActivity ejecutar metodos del fragmento ListTipHistoryFragment
 * El fragmento debe implementar la interfaz y los metodos
 * La actividad debe crear una instancia de la interfaz para acceder a los metodos
 */


public interface ListTipHistoryInterfaz {
    void addToList(TipRecordPOJO tipRecordPOJO);
    //void clearList();
}
