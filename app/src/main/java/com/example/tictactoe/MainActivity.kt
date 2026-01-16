package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    enum class Turn
    {
        NOUGHT,
        CROSS
    }

    private var firstTurn = Turn.CROSS
    private var currentTurn = Turn.CROSS

    private var boardList = mutableListOf<Button>()

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initBoard()
    }

    private fun initBoard()
    {
        boardList.add(binding.a1)
        boardList.add(binding.a2)
        boardList.add(binding.a3)
        boardList.add(binding.b1)
        boardList.add(binding.b2)
        boardList.add(binding.b3)
        boardList.add(binding.c1)
        boardList.add(binding.c2)
        boardList.add(binding.c3)
    }

    fun boardTapped(view: View)
    {
        if(view !is Button)
            return
        addToBoard(view)

        if(checkForVictory(NOUGHT))
        {
            result("Noughts Win!")
        }
        else if(checkForVictory(CROSS))
        {
            result("Crosses Win!")
        }

        if(fullBoard())
        {
            result("Draw")
        }
    }

    private fun checkForVictory(symbol: String): Boolean
    {
        //Horizontal Victory
        if(match(binding.a1,symbol) && match(binding.a2,symbol) && match(binding.a3,symbol))
            return true
        if(match(binding.b1,symbol) && match(binding.b2,symbol) && match(binding.b3,symbol))
            return true
        if(match(binding.c1,symbol) && match(binding.c2,symbol) && match(binding.c3,symbol))
            return true

        //Vertical Victory
        if(match(binding.a1,symbol) && match(binding.b1,symbol) && match(binding.c1,symbol))
            return true
        if(match(binding.a2,symbol) && match(binding.b2,symbol) && match(binding.c2,symbol))
            return true
        if(match(binding.a3,symbol) && match(binding.b3,symbol) && match(binding.c3,symbol))
            return true

        //Diagonal Victory
        if(match(binding.a1,symbol) && match(binding.b2,symbol) && match(binding.c3,symbol))
            return true
        if(match(binding.a3,symbol) && match(binding.b2,symbol) && match(binding.c1,symbol))
            return true

        return false
    }

    private fun match(button: Button, symbol : String): Boolean = button.text === symbol

    private fun result(title: String)
    {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setPositiveButton("New Game")
            { _,_ ->
                resetBoard()
            }
            .setCancelable(false)
            .show()
    }

    private fun resetBoard()
    {
        for(button in boardList)
        {
            button.text = ""
        }

        firstTurn = Turn.CROSS
        currentTurn = firstTurn
        setTurnLabel()
    }

    private fun fullBoard(): Boolean
    {
        for(button in boardList)
        {
            if(button.text == "")
                return false
        }
        return true
    }

    private fun addToBoard(button: Button)
    {
        if(button.text !== "")
            return

        if(currentTurn === Turn.NOUGHT)
        {
            button.text = NOUGHT
            currentTurn = Turn.CROSS
        }
        else
        {
            button.text = CROSS
            currentTurn = Turn.NOUGHT
        }
        setTurnLabel()
    }

    private fun setTurnLabel()
    {
        var turnText = ""
        turnText = if(currentTurn === Turn.CROSS)
            "Turn $CROSS"
        else
            "Turn $NOUGHT"

        binding.turnTitle.text = turnText
    }

    companion object
    {
        const val NOUGHT = "O"
        const val CROSS = "X"
    }
}