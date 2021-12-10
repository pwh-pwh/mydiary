package org.coderpwh.mydiary

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import org.coderpwh.mydiary.ui.theme.MyDiaryTheme
import java.text.SimpleDateFormat
import java.util.*

class EditActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dbHelper =MyDatabaseHelper(this,"diary.db",1)
        val db=dbHelper.writableDatabase


        setContent {
            var t by remember {
                mutableStateOf("请输入标题ʕ•ﻌ•ʔ ")
            }
            var c by remember {
                mutableStateOf("请输入内容ʕ·͡ˑ·ཻʔෆ⃛ʕ•̫͡•ོʔ ")
            }
            val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(Date())


            val saveLs:()->Unit = {
                ->
                val values1 = ContentValues().apply {
                    // 开始组装第一条数据
                    put("user", "a")
                    put("title",t )
                    put("content",c)
                    put("time",time )
                }

                db.insert("DiaryData", null, values1) // 插入第一条数据
                Toast.makeText(this, "日记添加成功(•ૢ⚈͒⌄⚈͒•ૢ)", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            var quitLs:()->Unit = {
                Toast.makeText(this, "退出到主页面｡°(°¯᷄◠¯᷅°)°｡ ", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            var st:(String)->Unit = {
                t = it
            }
            var sc:(String)->Unit = {
                c = it
            }



            MyDiaryTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.primary) {
                    Edit(t,c,st,sc,saveLs, quitLs)
                }
            }
        }
    }
}
@Composable
fun Edit(t:String,c:String,st:(String)->Unit,sc:(String)->Unit,save:()->Unit,quit:()->Unit) {
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = rememberImagePainter("https://c-ssl.duitang.com/uploads/item/202007/06/20200706220923_iteTt.thumb.300_0.jpeg"), contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .background(Color(230, 144, 117, 105))
                    .padding(10.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.lk),
                    contentDescription = "ff",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(45.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "添加日记",
                    color = Color(41, 204, 96, 255),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(50.dp))
                Button(onClick = save,
                    modifier = Modifier.clip(CircleShape)
                ) {
                    Text(text = "保存")
                }

                Button(onClick = quit,
                    modifier = Modifier.clip(CircleShape)
                ) {
                    Text(text = "退出")
                }
            }

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(30.dp)
            ) {
                TextField(value = t, onValueChange = st
                )
                Spacer(modifier = Modifier.height(20.dp))
                TextField(value = c, onValueChange = sc,
                    modifier = Modifier.height(300.dp)
                )
            }

        }

    }
}

@Composable
fun Greeting3(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    MyDiaryTheme {
        Greeting3("Android")
    }
}