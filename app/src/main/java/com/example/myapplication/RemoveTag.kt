package com.example.myapplication

object RemoveTag {
    fun removingTag(string: String):String{
        return string.replace("<.*?>".toRegex(),"")
    }
}