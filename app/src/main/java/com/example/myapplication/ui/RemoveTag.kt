package com.example.myapplication.ui

import java.util.Collections.replaceAll

object RemoveTag {
    fun removingTag(string: String):String{
        return string.replace("\\<[^>]*>","")
    }
}