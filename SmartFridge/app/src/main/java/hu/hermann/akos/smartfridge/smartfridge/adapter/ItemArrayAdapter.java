package hu.hermann.akos.smartfridge.smartfridge.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import hu.hermann.akos.smartfridge.smartfridge.R;
import hu.hermann.akos.smartfridge.smartfridge.model.Item;

/**
 * Created by a.hermann on 2015. 12. 07..
 */
public class ItemArrayAdapter extends ArrayAdapter<Item> {

    private Context context;
    private int resource;
    private int textViewResourceId;
    private List<Item> objects;

    public ItemArrayAdapter(Context context, int resource, int textViewResourceId, List<Item> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = convertView;
        ItemHolder holder;
        if(row == null) {
            row = inflater.inflate(textViewResourceId, parent, false);
            Item item = objects.get(position);
            holder = new ItemHolder();
            holder.tvName = (TextView) row.findViewById(R.id.tvName);
            holder.tvAmount = (TextView) row.findViewById(R.id.tvAmount);
            holder.tvPrice = (TextView) row.findViewById(R.id.tvPrice);
            holder.tvPrice.setText(String.valueOf(item.getPrice()));
            holder.tvAmount.setText(String.valueOf(item.getAmount()));
            holder.tvName.setText(item.getName());
            row.setTag(holder);
        }else{
            holder = (ItemHolder) row.getTag();
        }
        return row;
    }

    public class ItemHolder{
        TextView tvName;
        TextView tvAmount;
        TextView tvPrice;
    }
}
