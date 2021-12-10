package org.coderpwh.mydiary

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import org.coderpwh.mydiary.ui.theme.MyDiaryTheme

class DetailActivity : ComponentActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dbHelper =MyDatabaseHelper(this,"diary.db",1)
        val db=dbHelper.writableDatabase
        val bundle=this.intent.extras
        var title = bundle?.get("标题").toString()
        val cursor = db.query("DiaryData",
            null, "title=?", arrayOf(title),null, null, null )
        var c = "content"
        var ti = "time"
        if (cursor.moveToFirst()) {
            do {
                // 遍历Cursor对象，取出数据并打印
                title = cursor.getString(cursor.getColumnIndex("title"))
                c = cursor.getString(cursor.getColumnIndex("content"))
                ti = cursor.getString(cursor.getColumnIndex("time"))


            } while (cursor.moveToNext())
        }
        cursor.close()

        var del:()->Unit = {
            Toast.makeText(this, "删除成功！", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        var exit:()->Unit = {
            Toast.makeText(this, "返回首页！", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        setContent {
            MyDiaryTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.primary) {
                    Detail(title,c,ti,del, exit = exit)
                }
            }
        }
    }
}

@Composable
fun Detail(t:String,c:String,ti:String,del:()->Unit,exit:()->Unit) {
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = rememberImagePainter("https://c-ssl.duitang.com/uploads/item/202007/06/20200706220922_haJGH.thumb.300_0.jpeg"), contentDescription = null,
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
                    text = "日记内容",
                    color = Color(41, 204, 96, 255),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(50.dp))
                Button(onClick = del,
                    modifier = Modifier.clip(CircleShape)
                ) {
                    Text(text = "删除")
                }

                Button(onClick = exit,
                    modifier = Modifier.clip(CircleShape)
                ) {
                    Text(text = "退出")
                }
            }



            Column(modifier = Modifier
                .padding(30.dp)
                .border(
                    width = 10.dp, shape = RoundedCornerShape(10), color = Color(
                        48,
                        190,
                        129,
                        155
                    )
                )
            ) {

                TextField(value = ti, onValueChange = {})
                TextField(value = t, onValueChange = {})
                Spacer(modifier = Modifier.height(20.dp))
                TextField(value = c, onValueChange = {},
                    modifier = Modifier.height(300.dp),
                    enabled = true, readOnly = true
                )
            }

        }

    }
}

@Composable
fun Greeting4(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
    MyDiaryTheme {
        Greeting4("Android")
    }
}