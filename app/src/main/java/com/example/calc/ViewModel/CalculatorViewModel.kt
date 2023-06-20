package com.example.calc.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {

    private val _result = MutableLiveData<String>()
    val result: LiveData<String>
        get() = _result

    fun calculateExpression(expression: String) {
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
//                            Toast.makeText(applicationContext,"Invalid Input",Toast.LENGTH_SHORT).show()
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
//                            Toast.makeText(applicationContext,"Invalid Input",Toast.LENGTH_SHORT).show()
                            return
                        }
                        secondStr += e
                    }
                }
            }

            var res = 0.0

            when (op) {
                '+' -> {
                    res = firstStr.toDouble() + secondStr.toDouble()
                }
                '*' -> {
                    res = firstStr.toDouble() * secondStr.toDouble()
                }
                '/' -> {
                    if (secondStr.toDouble() != 0.0) {
                        res = firstStr.toDouble() / secondStr.toDouble()
                    } else {
                        // Handle division by zero
                        _result.value=res.toString();
                        return
                    }
                }
                '-' -> {
                    res = firstStr.toDouble() - secondStr.toDouble()
                }
                '%' -> {
                    res = firstStr.toDouble() % secondStr.toDouble()
                }
            }
            _result.postValue(res.toString())
        }
    }

}