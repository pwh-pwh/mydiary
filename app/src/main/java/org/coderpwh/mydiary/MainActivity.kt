package org.coderpwh.mydiary

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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

class MainActivity : ComponentActivity() {
    private var datas = ArrayList<String>()
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val editLt:()->Unit = {
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
            finish()
        }

        val dbHelper =MyDatabaseHelper(this,"diary.db",1)
        dbHelper.writableDatabase
        val db=dbHelper.writableDatabase

        val cursor = db.query("DiaryData", null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                // 遍历Cursor对象，取出数据并打印
                val title = cursor.getString(cursor.getColumnIndex("title"))
                Log.d("MainActivity", "title is $title")
                datas.add(title)
            } while (cursor.moveToNext())
        }
        cursor.close()
        setContent {
            MyDiaryTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.primary) {
                    Main(editLt = editLt,this,datas)
                }
            }
        }
    }



}

@Composable
fun Main(editLt:()->Unit,ac: ComponentActivity,datas:List<String>) {
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = rememberImagePainter("https://c-ssl.duitang.com/uploads/blog/202104/15/20210415152809_e47aa.thumb.300_0.jpg"), contentDescription = null,
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
                        .clickable { }
                )
                Spacer(modifier = Modifier.width(70.dp))

                Text(
                    text = "我的日记本",
                    color = Color(41, 204, 96, 255),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(60.dp))

                Icon(
                    Icons.Filled.Add,
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clickable(onClick = editLt)

                )


            }

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
            ) {
                DataList(messages = datas, ac = ac)
            }

        }

    }
}

@Composable
fun DataList(messages:List<String>,ac:ComponentActivity) {
    Text(text = "test")
    LazyColumn() {

        messages.forEach{
            e->
            item(e) {
                Surface(
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(start = 22.dp)
                        .fillMaxWidth(0.9f)
                        .height(40.dp)
                        .alpha(0.8f),
                    color = Color(4,4,4,100)

                ) {
                    Text(
                        fontSize=25.sp,
                        textAlign = TextAlign.Center,
                        text = e,
                        modifier = Modifier
                            .clickable(onClick = {
                                Toast
                                    .makeText(ac, "打开" + e.toString() + "日记", Toast.LENGTH_SHORT)
                                    .show()
                                val intent = Intent(ac, DetailActivity::class.java)
                                intent.putExtra("标题", e.toString())
                                ac.startActivity(intent)
                            }
                            )
                            .padding(start = 6.dp),
                        color = MaterialTheme.colors.primary
                    )

                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }

    }
}
