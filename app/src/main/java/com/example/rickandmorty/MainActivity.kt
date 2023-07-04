package com.example.rickandmorty

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SplashScreen(
                onSplashScreenFinished = {startActivity(Intent(this@MainActivity,Karakterler::class.java))
                    finish()}
            )
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SplashScreen(onSplashScreenFinished: () -> Unit){
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
    val isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch",true)
    LaunchedEffect(isFirstLaunch){
        delay(2000)
        sharedPreferences.edit().putBoolean("isFirstLaunch", false).apply()
        onSplashScreenFinished()
    }
    
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.rick), contentDescription ="Splash İmage" )
            Text(
                text = if (isFirstLaunch) "Welcome" else "Hello",
                style = androidx.compose.ui.text.TextStyle(fontSize = 36.sp)
            )
        }
    }
}



