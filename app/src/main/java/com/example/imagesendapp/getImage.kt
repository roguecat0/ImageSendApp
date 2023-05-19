package com.example.imagesendapp.ui.theme

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.imagesendapp.ImageTransformer

@Composable
fun RequestContentPermission() {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var prevUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val context = LocalContext.current
    val bitmap =  remember {
        mutableStateOf<Bitmap?>(null)
    }

    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }
    Column() {
        Spacer(modifier = Modifier.height(12.dp))
        Row(){
            Button(onClick = {
                launcher.launch("image/*")
            }) {
                Text(text = "Pick image")
            }
            Button(onClick = {
                sendData(
                    ImageTransformer
                        .toEspRegister()
                )
                Toast.makeText(context, "getransformeert en verzonden", Toast.LENGTH_SHORT).show()
            }) {
                Text(text = "send as bitmap")
            }
            Button(onClick = {
                ImageTransformer.toPolar()
                bitmap.value = ImageTransformer.getImage()
            }) {
                Text(text = "polarize")
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(){
            Button(onClick = {
                ImageTransformer.resetMat();
                bitmap.value = ImageTransformer.getImage()
            }) {
                Text(text = "reset")
            }
            Button(onClick = {
                ImageTransformer.changeResolution();
                bitmap.value = ImageTransformer.getImage()
            }) {
                Text(text = "res")
            }
            Button(onClick = {
                ImageTransformer.encodeImage();
                bitmap.value = ImageTransformer.getImage()
            }) {
                Text(text = "encode")
            }
            Button(onClick = {
                ImageTransformer.toEspRegister();
                Toast.makeText(context, "klaar met transformen", Toast.LENGTH_LONG).show()
            }) {
                Text(text = "esp")
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        imageUri?.let {
//            Log.d("uri1","uri1: "+it.toString()+"uri2: "+prevUri?.toString())
            if (prevUri == it){
//                Log.d("uri","same uri")
                bitmap.value = ImageTransformer.getImage()
            } else {
                if (Build.VERSION.SDK_INT < 28) {
                    bitmap.value = MediaStore.Images
                        .Media.getBitmap(context.contentResolver,it)
                    bitmap.value?.let { btm ->
                        ImageTransformer.setImage(btm);
                    }
                } else {
                    val source = ImageDecoder
                        .createSource(context.contentResolver,it)
                    bitmap.value = ImageDecoder.decodeBitmap(source)
                    bitmap.value?.let { btm ->
                        ImageTransformer.setImage(btm);
                    }
                }
                prevUri = it;
            }


            bitmap.value?.let {  btm ->
                Image(bitmap = btm.asImageBitmap(),
                    contentDescription ="null",
                    modifier = Modifier.size(400.dp))
            }
        }

    }
}