package com.example.calc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var editText: TextView
    private var expression = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText = findViewById(R.id.editText)
    }
    private fun isOperator(input: String): Boolean {
        return input in listOf("+", "-", "*", "/", "%")
    }
    fun getExpression(view: View) {
        expression = editText.text.toString()

        // Remove leading zero if the expression is "0"
        if (expression == "0") {
            expression = ""
        }

        val buttonSelect = view as Button
        val input = buttonSelect.text.toString()
        // Check if the expression is empty and the input is an operator
        if ((expression.isEmpty() && isOperator(input))) {
            // Ignore the operator press in the beginning
            Toast.makeText(applicationContext,"Invalid Input",Toast.LENGTH_SHORT).show()
            return
        }
        // Check if the last character of the expression is an operator
        if (isOperator(expression.lastOrNull().toString()) && isOperator(input)) {
            // Replace the last operator with the new operator
            expression = expression.dropLast(1)
        }


        when (buttonSelect.id) {
            R.id.bu1 -> expression += "1"
            R.id.bu2 -> expression += "2"
            R.id.bu3 -> expression += "3"
            R.id.bu4 -> expression += "4"
            R.id.bu5 -> expression += "5"
            R.id.bu6 -> expression += "6"
            R.id.bu7 -> expression += "7"
            R.id.bu8 -> expression += "8"
            R.id.bu9 -> expression += "9"
            R.id.bu0 -> expression += "0"
            R.id.buDot -> {
                // Add a dot only if the expression doesn't already contain one
                if (!expression.contains(".")) {
                    expression += "."
                }
            }
            R.id.buPlus -> expression += "+"
            R.id.buMultiply -> expression += "*"
            R.id.buDivide -> expression += "/"
            R.id.buMinus -> expression += "-"
            R.id.buPercent -> expression += "%"
            R.id.buPlusMinus -> {
                if (expression.isNotEmpty() && expression[0] != '-') {
                    expression = "-$expression"
                } else if (expression.isNotEmpty() && expression[0] == '-') {
                    expression = expression.substring(1)
                }
            }
        }

        editText.setText(expression)
    }

    fun acEvent(view: View) {
        expression = ""
        editText.setText("0")
    }
    fun cEvent(view: View) {
        if(expression.isEmpty()){
            return
        }
        expression = expression.dropLast(1)
        editText.setText(expression)
    }

    fun equalEvent(view: View) {
        if (expression.isNotEmpty()) {
            var firstStr = ""
            var secondStr = ""
            var foundOp = false
            var op = '+'

            // Check if the expression starts with '-'
            if (expression.startsWith('-')) {
                firstStr += "-"
                // Skip the first character as it has been added to firstStr
                for (i in 1 until expression.length) {
                    val e = expression[i]
                    if (!foundOp) {
                        if (e == '+' || e == '-' || e == '*' || e == '/' || e == '%') {
                            op = e
                            foundOp = true
                        } else {
                            firstStr += e
                        }
                    } else {
                        if (e == '+' || e == '-' || e == '*' || e == '/' || e == '%'){
                            Toast.makeText(applicationContext,"Invalid Input",Toast.LENGTH_SHORT).show()
                            return
                        }
                        secondStr += e
                    }
                }
            } else {
                // Expression does not start with '-', proceed as before
                for (e in expression) {
                    if (!foundOp) {
                        if (e == '+' || e == '-' || e == '*' || e == '/' || e == '%') {
                            op = e
                            foundOp = true
                        } else {
                            firstStr += e
                        }
                    } else {
                        if (e == '+' || e == '-' || e == '*' || e == '/' || e == '%'){
                            Toast.makeText(applicationContext,"Invalid Input",Toast.LENGTH_SHORT).show()
                            return
                        }
                        secondStr += e
                    }
                }
            }

            var result = 0.0

            when (op) {
                '+' -> {
                    result = firstStr.toDouble() + secondStr.toDouble()
                }
                '*' -> {
                    result = firstStr.toDouble() * secondStr.toDouble()
                }
                '/' -> {
                    if (secondStr.toDouble() != 0.0) {
                        result = firstStr.toDouble() / secondStr.toDouble()
                    } else {
                        // Handle division by zero
                        editText.setText("∞")
                        return
                    }
                }
                '-' -> {
                    result = firstStr.toDouble() - secondStr.toDouble()
                }
                '%' -> {
                    result = firstStr.toDouble() % secondStr.toDouble()
                }
            }

            editText.setText(result.toString())
        }
    }
}
