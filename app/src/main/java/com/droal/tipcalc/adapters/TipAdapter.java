package com.droal.tipcalc.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.droal.tipcalc.interfaces.ItemClickInterfaz;
import com.droal.tipcalc.R;
import com.droal.tipcalc.models.TipRecordPOJO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by aleydario on 1/04/17.
 */


//El ViewHolder evita que esté llamando constantemente el renderizado del contenido
public class TipAdapter extends RecyclerView.Adapter<TipAdapter.ViewHolder>{

    private List<TipRecordPOJO> dataset;
    private Context context;
    private ItemClickInterfaz itemClickInterfaz;


    public TipAdapter(Context context, ItemClickInterfaz itemClickInterfaz) {
        this.context = context;
        this.dataset = new ArrayList<>();
        this.itemClickInterfaz = itemClickInterfaz;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_tip_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TipRecordPOJO element = dataset.get(position);
        String strTip = String.format(context.getString(R.string.global_message_tip),element.geTip());

        holder.tvItemContent.setText(strTip);
        holder.tvId.setText("Item: "+position);
        holder.setItemClickListener(element,itemClickInterfaz);
    }




    @Override
    public int getItemCount() {
        return dataset.size();
    }


    //Método para aregar items al RecyclerVies
    //Esto podria hacerse desde la actividad modificando el arreglo de objetos e invicando el metodo notifyDataSetChanged() del adapter
    public void addRecord(TipRecordPOJO recordJO){
        dataset.add(0,recordJO);
        notifyDataSetChanged();
    }

    //Método para limpiar el Recyclerview
    //Esto podria hacerse desde la actividad modificando el arreglo de objetos e invicando el metodo notifyDataSetChanged() del adapter
    public void clear(){
        dataset.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_item_content)
        TextView tvItemContent;
        @BindView(R.id.tv_item_id)
        TextView tvId;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setItemClickListener(final TipRecordPOJO element, final ItemClickInterfaz itemClickInterfaz) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickInterfaz.itemClick(element);
                }
            });
        }
    }
}
