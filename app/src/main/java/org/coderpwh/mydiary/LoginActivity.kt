package org.coderpwh.mydiary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import org.coderpwh.mydiary.ui.theme.MyDiaryTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = getPreferences(Context.MODE_PRIVATE)
        var isRemember = prefs.getBoolean("remember_password", false)

        setContent {
            MyDiaryTheme {

                var account by remember {
                    mutableStateOf("")
                }
                var password by remember {
                    mutableStateOf("")
                }
                var btnFlag by remember {
                    mutableStateOf(false)
                }

                if (isRemember) {
                    account = prefs.getString("account", "").toString()
                    password = prefs.getString("password","").toString()
                    btnFlag = true
                }

                val regist:()->Unit = {
                    ->
                    if (account.trim().equals("")||password.trim().equals("")) {
                        Toast.makeText(this, "用户名或者密码未填写", Toast.LENGTH_SHORT).show()
                    }else if(prefs.getString("account","")!!.length>0) {
                        Toast.makeText(this, "用户名已存在", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        val editor = prefs.edit()
                        if (btnFlag) { // 检查复选框是否被选中
                            editor.putBoolean("remember_password", true)
                            editor.putString("account", account)
                            editor.putString("password", password)

                            Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show()
                        } else {
                            editor.clear()
                        }
                        editor.apply()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                }

                val login:()->Unit = {
                    ->
                    val pw = prefs.getString("password","")
                    if (password.length<=0||account.length<=0) {
                        Toast.makeText(this, "用户名或者密码未填写", Toast.LENGTH_SHORT).show()
                    }
                    else if (!pw.equals(password)) {
                        Toast.makeText(this, "用户名或者密码错误", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(this, "登陆成功！", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }

                }

                var rm:(Boolean)->Unit = {
                    btnFlag = it
                }

                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.primary) {
                    Login(
                        btnFlag, account, password, acLs = {
                        account = it
                    },
                        pwLs = {
                            password = it
                        },
                        rg = regist,
                        lg = login,
                        rm
                    )
                }
            }
        }
    }
}

@Composable
fun Login(
    isRemember:Boolean,
    account:String,password:String,
    acLs:(String)->Unit,
    pwLs:(String)->Unit,
    rg:()->Unit,
    lg:()->Unit,
    rm:(Boolean)->Unit
) {
    MyDiaryTheme() {
        Box(Modifier.fillMaxSize(1f)) {
            Image(
                painter = rememberImagePainter("https://c-ssl.duitang.com/uploads/blog/202108/14/20210814083627_b6ff4.thumb.300_0.jpeg"), contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "我的日记本",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 0.dp, vertical = 20.dp),
                    color = MaterialTheme.colors.primary,
                    fontSize = 28.sp,
                    fontWeight = FontWeight(3)
                )
                Image(
                    painter = painterResource(id = R.drawable.lk),
                    contentDescription = "ff",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(130.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
            Spacer(modifier = Modifier.height(40.dp))
            Column() {
                Spacer(modifier = Modifier.weight(1f))
                Column(
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxWidth(1f)
                ) {
                    Spacer(modifier = Modifier.height(30.dp))
//账号框
                    Row {
                        Text(
                            text = "账号:",
                            color = MaterialTheme.colors.primary,
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(10.dp)
                        )
                        TextField(
                            value = account,
                            onValueChange = acLs,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 10.dp)
                        )


                    }
//密码框
                    Row {
                        Text(
                            text = "密码:",
                            color = MaterialTheme.colors.primary,
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(10.dp)
                        )
                        TextField(
                            value = password,
                            onValueChange =pwLs,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 10.dp)
                        )


                    }
//                    密码确认框
                    Row(horizontalArrangement = Arrangement.Start) {
                        Spacer(modifier = Modifier.width(10.dp))
                        Checkbox(checked = isRemember, onCheckedChange = rm)
                        Text(text = "记住密码", color = Color(32, 197, 38, 255))
                    }

                    Spacer(modifier = Modifier.height(30.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth(1f)

                    ) {
                        Button(
                            onClick = lg,
                            modifier = Modifier
                                .size(width = 200.dp, height = 50.dp)
                                .clip(CircleShape)

                        ) {
                            Text(
                                text = "登录",
                                fontSize = 24.sp,
                                color = MaterialTheme.colors.onPrimary
                            )
                        }

                        Spacer(modifier = Modifier.height(15.dp))
                        Button(
                            onClick = rg,
                            modifier = Modifier
                                .size(width = 200.dp, height = 50.dp)
                                .clip(CircleShape)
                        ) {
                            Text(
                                text = "注册",
                                fontSize = 24.sp,
                                color = MaterialTheme.colors.onPrimary
                            )
                        }



                    }

                }
            }

        }



    }

}

@Composable
fun Greeting2(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    MyDiaryTheme {
        Greeting2("Android")
    }
}