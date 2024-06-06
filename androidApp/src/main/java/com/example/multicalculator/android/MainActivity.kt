    package com.example.multicalculator.android

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp

    class MainActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
                MyApplicationTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                       CalcView()
                    }
                }
            }
        }
    }







    @SuppressLint("UnrememberedMutableState")
@Composable
fun CalcView() {
        var leftNumber by rememberSaveable { mutableStateOf(0) }
        var rightNumber by rememberSaveable { mutableStateOf(0) }
        var operation by rememberSaveable { mutableStateOf("") }
        var complete by rememberSaveable { mutableStateOf(false) }
        val displayText: MutableState<String> = mutableStateOf("0")

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(color = Color.LightGray)
        ) {
            Row {
                CalcDisplay(display = displayText)

            }
            Row {
                Column {
                    CalcOperationButton(operation = "+", display = displayText)
                    CalcOperationButton(operation = "-", display = displayText)
                    CalcOperationButton(operation = "*", display = displayText)
                    CalcOperationButton(operation = "/", display = displayText)

                }
            }
            for (i in 7 downTo 1 step 3) {
                CalcRow(startNum = i , numButtons = 3, onPress = {numberPress(it)})
            }
            Row {
                CalcNumericButton(number = 0, onPress = {numberPress(it)})
                CalcEqualsButton(onPress = {equalPress()})
            }

        }



        fun numberPress(btnNum: Int) {
            if (complete){
                leftNumber = 0
                rightNumber= 0
                operation =""
                complete= false
            }
            if (operation.isNotEmpty() && !complete){
                rightNumber = rightNumber * 10 + btnNum
            }else if (operation.isEmpty() && !complete){
                leftNumber = leftNumber * 10 + btnNum
            }
            displayText.value = if(operation.isEmpty()) leftNumber.toString() else rightNumber.toString()
        }
        fun operationPress (op : String){
            if (!complete){
                operation = op
            }
        }
        fun equalPress(){
            if(operation.isNotEmpty() && ! complete){
                var ans = 0
                when (operation) {
                    "+" -> ans = leftNumber + rightNumber
                    "-" -> ans = leftNumber - rightNumber
                    "*" -> ans = leftNumber * rightNumber
                    "/" -> ans = leftNumber / rightNumber

                }
                displayText.value = ans.toString()
                leftNumber = ans
                rightNumber = 0
                operation = ""
                complete = true
            }
        }
    }


@Composable
fun CalcRow(startNum: Int, numButtons: Int, onPress: (Int)-> Unit){
    val endNum = startNum + numButtons

    Row(modifier = Modifier.padding(0.dp
    )) {
        for (i in startNum until endNum){
            CalcNumericButton(number = i, display = display )

        }

    }
}


@Composable
    fun CalcDisplay(display: MutableState<String>){
        Text(text = display.value,
            modifier = Modifier
                .height(50.dp)
                .padding(5.dp))

    }


@Composable
fun CalcNumericButton(number: Int,display: MutableState<String>){
    Button(onClick = { display.value += number },
        modifier = Modifier.padding(4.dp)) {
        Text(text = number.toString())


    }

}

@Composable
fun CalcOperationButton(operation: String ,display: MutableState<String>){
    Button(onClick = { },
        modifier = Modifier.padding(4.dp)) {
        Text(text = operation)

    }

}

@Composable
fun CalcEqualsButton(display: MutableState<String>){
    Button(onClick = { display.value = "0" },
        modifier = Modifier.padding(4.dp)) {
        Text(text = "=")

    }
}

@Preview
@Composable
fun CalcViewPreview(){
    CalcView()
}
