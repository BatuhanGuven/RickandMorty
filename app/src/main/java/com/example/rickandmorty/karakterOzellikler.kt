package com.example.rickandmorty

import android.content.Intent
import android.graphics.Paint
import android.graphics.Paint.Align
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.textInputServiceFactory
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.rickandmorty.ui.theme.RickAndMortyTheme

class karakterOzellikler : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowCharacterDetails(intent = intent, activity = this)
        }
    }
}
@Composable
fun BackButton(onBackPressed: () -> Unit) {
    IconButton(
        onClick = onBackPressed,
        modifier = Modifier.padding(5.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Geri"
        )
    }
}

@Composable
fun ShowCharacterDetails(intent: Intent,activity: ComponentActivity) {
    val created = intent.getStringExtra("created")
    val gender = intent.getStringExtra("gender")
    val id = intent.getStringExtra("id")
    val image = intent.getStringExtra("image")
    val species = intent.getStringExtra("species")
    val name = intent.getStringExtra("name")
    val status = intent.getStringExtra("status")
    val episodes = intent.getStringArrayListExtra("episodes")
    Column(modifier = Modifier
        .fillMaxSize()
        ) {
        BackButton {
            activity.finish()
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            ,
            contentAlignment = Alignment.Center){
            Text(text = name!!, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
            contentAlignment = Alignment.Center) {
            AsyncImage(
                model = image,
                contentDescription = "karakter resmi",
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp)
                    .clip(
                        RoundedCornerShape(50.dp)
                    )
            )
        }

        Column() {
            Card(modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(5.dp))
                .padding(10.dp)) {
                Text(text = "Status: $status", fontSize = 20.sp, modifier = Modifier.padding(10.dp))
            }
            Card(modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(5.dp))
                .padding(10.dp)) {
                Text(text = "Species: $species", fontSize = 20.sp , modifier = Modifier.padding(10.dp))
            }
            Card(modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(5.dp))
                .padding(10.dp)) {
                Text(text = "Gender: $gender", fontSize = 20.sp, modifier = Modifier.padding(10.dp))
            }
            Card(modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(5.dp))
                .padding(10.dp)) {
                Text(text = "Episodes: ${episodes!!.joinToString(",") { it.substring(40) }}", fontSize = 20.sp, modifier = Modifier.padding(10.dp))
            }
            Card(modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(5.dp))
                .padding(10.dp)) {
                Text(text = "Created at " +
                        "(API): ${created}", fontSize = 20.sp, modifier = Modifier.padding(10.dp))
            }

        }
    }
}

