package uz.nits.namangantravel.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import uz.nits.namangantravel.R

class GridViewAdapter(private val context: Context, private val imageId: Array<Int>) : BaseAdapter() {
    override fun getCount(): Int {
        return imageId.size
    }

    override fun getItem(position: Int): Any {
        return imageId[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder", "UseCompatLoadingForDrawables")
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val root: View = LayoutInflater.from(parent?.context)
            .inflate(R.layout.fragment_home_cutom_layout, parent, false)

        val img = root.findViewById<ImageView>(R.id.home_grid_view_img_id)
        img.setImageDrawable(context.getDrawable(imageId[position]))

        return root
    }
}