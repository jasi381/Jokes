package com.example.jokes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jokes.ui.theme.JokesTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JokesTheme {
                UI()

            }
        }
    }
}

const val base_url ="https://v2.jokeapi.dev/"
object ApiService{
    suspend fun getApiData() :ApiResponse {
        val retrofit = Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ApiSer::class.java)
        return  service.getData()
    }

}

interface ApiSer{
    @GET("joke/Programming?amount=10")
    suspend fun getData():ApiResponse
}


@Composable
fun UI() {


    val jokesList = remember { mutableStateOf<List<Joke>>(emptyList()) }
    LaunchedEffect(key1 = Unit) {
        jokesList.value = ApiService.getApiData().jokes
    }

    LazyColumn (
        Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ){
        items(jokesList.value){jokes ->
            ItemLayout(jokes)
           if(jokesList.value.last() != jokes){
               Divider()
           }


        }
    }

}

@Composable
fun ItemLayout(joke: Joke) {

    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Monospace,
                fontSize = 16.sp
            )
        ) {
            append("Category: ")
        }
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                fontFamily = FontFamily.Monospace,
                fontSize = 15.sp,

                )
        ) {
            append(joke.category)
        }
    }


    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = annotatedString, modifier = Modifier.padding(16.dp))

        Text(
            text = joke.joke ?:"Joke not found",
            modifier = Modifier.padding(16.dp)
        )


    }
}
