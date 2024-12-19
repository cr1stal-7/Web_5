package com.example.airplaneapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.airplaneapp.ui.theme.AirplaneAppTheme
import com.example.airplaneapp.ui.Navigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AirplaneAppTheme {
                Navigation()
            }
        }
    }
}