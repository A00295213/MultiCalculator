package com.example.multicalculator

class Calculator{
    fun add (left: Int, right: Int): Int{
        return  left + right

    }
    fun substract (left: Int, right: Int): Int{
        return  left - right

    }
    fun multiply (left: Int, right: Int): Int{
        return  left * right

    }
    fun divide (left: Int, right: Int): Int{
        if (right == 0){
            throw IllegalArgumentException(" It cannot divide by 0")
        }

        return  left / right

    }
}