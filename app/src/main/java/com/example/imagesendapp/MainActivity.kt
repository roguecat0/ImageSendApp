package com.example.imagesendapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.imagesendapp.ui.theme.ImageSendAppTheme
import com.example.imagesendapp.ui.theme.RequestContentPermission
import com.example.imagesendapp.ui.theme.sendData
import org.opencv.android.OpenCVLoader


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(OpenCVLoader.initDebug())
            Log.d("Loaded","success");
        else
            Log.d("Loaded","error");
        setContent {
            ImageSendAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RequestContentPermission()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun sendButton() {
    val mContext = LocalContext.current
    Button(onClick = {espSend(mContext)}){
        Text(text = "Click", color = Color.White)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ImageSendAppTheme {
        Greeting("Android")
    }
}

fun espSend(context: Context){
    // hier komt de zend data functie
    // die context mag overal das was om snel te testen of de knop iets deed
    Toast.makeText(context, "In deze funtie stuur je de data", Toast.LENGTH_LONG).show()
}