package com.example.rickandmorty

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface ApiService {
    @GET("character")
    fun getCharacters(): Call<lazyKarakterler>
}

const val BASE_URL = "https://rickandmortyapi.com/api/"
val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val apiService: ApiService = retrofit.create(ApiService::class.java)

class Karakterler : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CreateHomePage()
        }
    }
}
@Preview(showBackground = true, device = "spec:width=411dp,height=891dp", showSystemUi = true)
@Composable
fun CreateHomePage() {
    val context = LocalContext.current
    val karakterlerList = remember { mutableListOf<ResultX>() }
    val isKarakterlerLoad=remember{ mutableStateOf(false)}
    var selectedIndex = remember{ mutableStateOf(-1)}
    val myFontFamily = FontFamily(Font(R.font.get_schwifty))
    if (isKarakterlerLoad.value==false){

        val callKarakterler: Call<lazyKarakterler> = apiService.getCharacters()
        callKarakterler.enqueue(object : Callback<lazyKarakterler>{
            override fun onResponse(call: Call<lazyKarakterler>, response: Response<lazyKarakterler>) {
                if (response.isSuccessful){
                    val results = response.body()
                    karakterlerList.addAll(response.body()!!.results)
                    isKarakterlerLoad.value=true
                }
            }
            override fun onFailure(call: Call<lazyKarakterler>, t: Throwable) {
                Toast.makeText(context, "Karakterler Yüklenemedi", Toast.LENGTH_SHORT).show()

            }

        })
    }


    if (isKarakterlerLoad.value) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.rick),
                    contentDescription = "Başlık"
                )
            }

            LazyColumn(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally){
                itemsIndexed(karakterlerList) { index, item ->
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .border(2.dp, Color.Black, RoundedCornerShape(10.dp))
                        .clickable {
                            val myArrayList = ArrayList<String>(item.episode)
                            val intent = Intent(context, karakterOzellikler::class.java)
                            intent.putExtra("created", item.created)
                            intent.putExtra("gender", item.gender)
                            intent.putExtra("id", item.id)
                            intent.putExtra("image", item.image)
                            intent.putExtra("species", item.species)
                            intent.putExtra("name", item.name)
                            intent.putExtra("status", item.status)
                            intent.putExtra("type", item.type)
                            intent.putExtra("url", item.url)
                            intent.putStringArrayListExtra("episodes", myArrayList)
                            context.startActivity(intent)
                        }
                        ){
                        Row(modifier= Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                            horizontalArrangement = Arrangement.SpaceBetween) {
                            AsyncImage(model = item.image, contentDescription = "karakter resmi", modifier = Modifier.clip(
                                RoundedCornerShape(10.dp)))
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                                if(item.gender=="Male"){
                                    Box(modifier = Modifier.fillMaxWidth()){
                                        Image(painter = painterResource(id = R.drawable.male), contentDescription = "male",Modifier.alpha(0.4f))

                                    }
                                }
                                if(item.gender=="Female"){
                                    Box(modifier = Modifier.fillMaxWidth()){
                                        Image(painter = painterResource(id = R.drawable.female), contentDescription = "female",Modifier.alpha(0.4f))
                                    }
                                }
                                Text(text = item.name, style = androidx.compose.ui.text.TextStyle(fontFamily = myFontFamily, fontSize = 35.sp))
                            }
                        }
                    }
                }
            }
        }
    }
}



