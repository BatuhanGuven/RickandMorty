package com.example.rickandmorty

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("location")
    fun getLocations(): Call<karakterlerJson>
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

@Composable
fun CreateHomePage() {
    val context = LocalContext.current
    val locationsList = remember { mutableListOf<Result>() }
    val karakterlerList = remember { mutableListOf<ResultX>() }
    val isLocationsLoad = remember { mutableStateOf(false) }
    val isKarakterlerLoad=remember{ mutableStateOf(false)}
    val selectedIndex = remember{ mutableStateOf(-1)}

    if (isLocationsLoad.value==false){
        val callLocations: Call<karakterlerJson> = apiService.getLocations()
        callLocations.enqueue(object : Callback<karakterlerJson> {
            override fun onResponse(call: Call<karakterlerJson>, response: Response<karakterlerJson>) {
                if (response.isSuccessful) {

                    val results = response.body()!!.results
                    locationsList.addAll(results)
                    isLocationsLoad.value = true
                    Toast.makeText(context,"locations called",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<karakterlerJson>, t: Throwable) {
                Toast.makeText(context, "Locations Yüklenemedi", Toast.LENGTH_SHORT).show()
            }
        })
    }
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


    if (isLocationsLoad.value&&isKarakterlerLoad.value){
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.rick),
                    contentDescription = "Başlık"
                )
            }
            if (isLocationsLoad.value) {
                LazyRow {
                    itemsIndexed(locationsList) { index, item ->
                        var width = remember {
                            mutableStateOf(100.dp)
                        }
                        var height = remember {
                            mutableStateOf(90.dp)
                        }

                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .width(width.value)
                                .height(height.value)
                            ,
                            shape = RoundedCornerShape(130.dp),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 5.dp
                            ),
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = item.name,
                                    fontSize = 15.sp,
                                    modifier = Modifier
                                        .clickable {
                                            selectedIndex.value = index

                                        }
                                )
                            }
                        }
                    }
                }
                if(selectedIndex.value!=-1){locationsList[selectedIndex.value].name}
                if(isKarakterlerLoad.value) {
                    LazyColumn {
                        itemsIndexed(karakterlerList) { index, item ->
                            if (selectedIndex.value != -1) {

                                if (locationsList[selectedIndex.value].residents.contains(karakterlerList[index].url)) {
                                    Text(text = item.name)
                                }
                            }
                        }
                    }

                }
            }
        }
    }

}

