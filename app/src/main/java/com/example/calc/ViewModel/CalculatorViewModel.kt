package com.example.calc.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.subjects.BehaviorSubject

class CalculatorViewModel : ViewModel() {

    private val expressionSubject: BehaviorSubject<String> = BehaviorSubject.createDefault("")
    private val _result: MutableLiveData<String> = MutableLiveData()
    val result: MutableLiveData<String>
        get() = _result

    init {
        expressionSubject
            .map { calculateExpression(it) }
            .distinctUntilChanged()
            .subscribe { result ->
                _result.postValue(result)
            }
    }

    fun calculateExpression(expression: String): String {
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
                        if (e == '+' || e == '-' || e == '*' || e == '/' || e == '%') {
                            return "invalid"
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
                        if (e == '+' || e == '-' || e == '*' || e == '/' || e == '%') {
                            return "invalid"
                        }
                        secondStr += e
                    }
                }
            }
            if (!foundOp) {
                return "invalid"
            }
            if(secondStr.isEmpty() && (op=='+' || op=='-' || op=='/' || op=='*')){
                return "invalid"
            }

            when (op) {
                '+' -> {
                    _result.postValue((firstStr.toDouble() + secondStr.toDouble()).toString())
                }

                '*' -> {
                    _result.postValue((firstStr.toDouble() * secondStr.toDouble()).toString())
                }

                '/' -> {
                    if (secondStr.toDouble() != 0.0) {
                        _result.postValue((firstStr.toDouble() / secondStr.toDouble()).toString())
                    } else {
                        _result.postValue("∞")
                    }
                }

                '-' -> {
                    _result.postValue((firstStr.toDouble() - secondStr.toDouble()).toString())
                }

                '%' -> {
                    _result.postValue((firstStr.toDouble()/100).toString())
                }
            }
        }
        return "0"
    }

    fun updateExpression(expression: String) {
        expressionSubject.onNext(expression)
    }

}