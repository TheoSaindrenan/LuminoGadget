package fr.eseo.saindrenan.adapter

import android.bluetooth.BluetoothClass
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.eseo.saindrenan.R

class ExempleAdapter(private val deviceList: Array<BluetoothClass.Device>, private val onClick: ((selectedDevice: BluetoothClass.Device) -> Unit)? = null) : RecyclerView.Adapter<ExempleAdapter.ViewHolder>() {

    // Comment s'affiche ma vue
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun showItem(device: BluetoothClass.Device, onClick: ((selectedDevice: BluetoothClass.Device) -> Unit)? = null) {
            itemView.findViewById<TextView>(androidx.appcompat.R.id.list_item).text = "device"

            if(onClick != null) {
                itemView.setOnClickListener {
                    onClick(device)
                }
            }
        }
    }

    // Retourne une « vue » / « layout » pour chaque élément de la liste
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    // Connect la vue ET la données
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.showItem(deviceList[position], onClick)
    }

    override fun getItemCount(): Int {
        return deviceList.size
    }

}