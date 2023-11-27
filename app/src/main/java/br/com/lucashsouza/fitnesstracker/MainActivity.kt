package br.com.lucashsouza.fitnesstracker

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.tiagoaguiar.fitnesstracker.R

class MainActivity : AppCompatActivity() {

    private lateinit var llImc: LinearLayout
    private lateinit var rvMain: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainItems = mutableListOf<MainItem>()
        mainItems.add(
            MainItem(
                id = 1,
                drawableId = R.drawable.baseline_wb_sunny_24,
                textStringId = R.string.imc,
                color = Color.GREEN
            )
        )

        mainItems.add(
            MainItem(
                id = 2,
                drawableId = R.drawable.baseline_logo_dev_24,
                textStringId = R.string.tmb,
                color = Color.YELLOW
            )
        )

        val adapter = MainAdapter(mainItems) { id ->
            when (id) {
                1 -> {
                    val intent = Intent(this@MainActivity, ImcActivity::class.java)
                    startActivity(intent)
                }

                2 -> {
                    // abrir uma outra activity
                }
            }
            Log.i("Teste", "clicou $id!!")
        }

        rvMain = findViewById(R.id.rv_main)
        rvMain.adapter = adapter
        rvMain.layoutManager = GridLayoutManager(this, 2)
    }

    private inner class MainAdapter(
        private val mainItems: List<MainItem>,
        private val onItemClickListener: (Int) -> Unit
    ) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.main_item, parent, false)
            return MainViewHolder(view)
        }

        override fun getItemCount(): Int {
            return mainItems.size
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemCurrent = mainItems[position]
            holder.bind(itemCurrent)
        }

        private inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(item: MainItem) {
                val container: LinearLayout = itemView.findViewById(R.id.item_container_imc)
                val img: ImageView = itemView.findViewById(R.id.item_img_icon)
                val name: TextView = itemView.findViewById(R.id.item_txt_name)

                container.setBackgroundColor(item.color)
                img.setImageResource(item.drawableId)
                name.setText(item.textStringId)

                container.setOnClickListener {
                    onItemClickListener.invoke(item.id)
                }
            }
        }
    }

}