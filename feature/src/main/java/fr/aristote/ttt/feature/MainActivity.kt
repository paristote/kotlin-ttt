package fr.aristote.ttt.feature

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View {

    private lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setClickListeners()
        presenter = MainPresenter(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menu_main_switch -> {
                presenter.onSwitchPlayers()
                true
            }
            R.id.menu_main_reset -> {
                presenter.onGameFinished()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setClickListeners() {
        topLeftButton.setOnClickListener {
            handleClick(it)
        }
        topCenterButton.setOnClickListener {
            handleClick(it)
        }
        topRightButton.setOnClickListener {
            handleClick(it)
        }
        centerLeftButton.setOnClickListener {
            handleClick(it)
        }
        middleButton.setOnClickListener {
            handleClick(it)
        }
        centerRightButton.setOnClickListener {
            handleClick(it)
        }
        bottomLeftButton.setOnClickListener {
            handleClick(it)
        }
        bottomCenterButton.setOnClickListener {
            handleClick(it)
        }
        bottomRightButton.setOnClickListener {
            handleClick(it)
        }
    }

    private fun handleClick(cell: View) {
        when (cell.id) {
            R.id.topLeftButton -> presenter.onCellPressed(Cell.TOP_LEFT)
            R.id.topCenterButton -> presenter.onCellPressed(Cell.TOP_CENTER)
            R.id.topRightButton -> presenter.onCellPressed(Cell.TOP_RIGHT)
            R.id.centerLeftButton -> presenter.onCellPressed(Cell.CENTER_LEFT)
            R.id.middleButton -> presenter.onCellPressed(Cell.MIDDLE)
            R.id.centerRightButton -> presenter.onCellPressed(Cell.CENTER_RIGHT)
            R.id.bottomLeftButton -> presenter.onCellPressed(Cell.BOTTOM_LEFT)
            R.id.bottomCenterButton -> presenter.onCellPressed(Cell.BOTTOM_CENTER)
            R.id.bottomRightButton -> presenter.onCellPressed(Cell.BOTTOM_RIGHT)
            else -> {
                Log.e("GameStatus", "ERROR: Unknown cell pressed ${cell.id}")
            }
        }
    }

    override fun updateCell(cell: Cell, cellStatus: CellStatus) {
        when (cell) {
            Cell.TOP_LEFT -> topLeftButton.text = cellStatus.toString()
            Cell.TOP_CENTER -> topCenterButton.text = cellStatus.toString()
            Cell.TOP_RIGHT -> topRightButton.text = cellStatus.toString()
            Cell.CENTER_LEFT -> centerLeftButton.text = cellStatus.toString()
            Cell.MIDDLE -> middleButton.text = cellStatus.toString()
            Cell.CENTER_RIGHT -> centerRightButton.text = cellStatus.toString()
            Cell.BOTTOM_LEFT -> bottomLeftButton.text = cellStatus.toString()
            Cell.BOTTOM_CENTER -> bottomCenterButton.text = cellStatus.toString()
            Cell.BOTTOM_RIGHT -> bottomRightButton.text = cellStatus.toString()
        }
    }

    override fun showResult(resultMessage: String) {
        val dialog = OneButtonDialog()
        dialog.onFinish { presenter.onGameFinished() }
        val arguments = Bundle()
        arguments.putString(OneButtonDialog.MESSAGE, resultMessage)
        arguments.putString(OneButtonDialog.BUTTON_LABEL, "NEW GAME")
        dialog.arguments = arguments
        dialog.show(fragmentManager, "OneButtonDialog")
    }

    override fun showToast(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.view.setBackgroundColor(resources.getColor(android.R.color.holo_blue_light))
        toast.show()
    }

    override fun resetBoard() {
        topLeftButton.text = ""
        topCenterButton.text = ""
        topRightButton.text = ""
        centerLeftButton.text = ""
        middleButton.text = ""
        centerRightButton.text = ""
        bottomLeftButton.text = ""
        bottomCenterButton.text = ""
        bottomRightButton.text = ""
    }
}
