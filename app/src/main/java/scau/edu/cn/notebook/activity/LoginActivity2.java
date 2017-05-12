package scau.edu.cn.notebook.activity;

/*
public class LoginActivity2 extends AppCompatActivity {

    private static final String TAG = "tag";
    EditText email;
    EditText password;
    Boolean isLogin = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Bmob.initialize(this,"a67d34b9861a024e7a89ac8716526e39");
        email = (EditText) findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
    }

    public void signIn(View view){
        final String uEmail = email.getText().toString();
        final String uPassword = password.getText().toString();
        if(uEmail.isEmpty()){
            Toast.makeText(LoginActivity2.this,"请输入邮箱",Toast.LENGTH_SHORT).show();
            return;
        }else if(uPassword.isEmpty()){
            Toast.makeText(LoginActivity2.this,"请输入密码",Toast.LENGTH_SHORT).show();
            return;
        }
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if(e==null){
                    for(User user:list){
                         if(user.getEmail().equals(uEmail)&&user.getPassword().equals(uPassword)){
                             Log.d(TAG,"login successed");
                            Toast.makeText(LoginActivity2.this,"登陆成功",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.setClass(LoginActivity2.this,HomeActivity.class);
                             Bundle data = new Bundle();
                             data.putString("user_name",user.getName());
                             data.putString("user_email",user.getEmail());
                             intent.putExtras(data);
                            startActivity(intent);
                            isLogin = true;
                        }
                    }
                    if(isLogin==false)
                    Toast.makeText(LoginActivity2.this,"邮箱或密码错误",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity2.this,e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void signUp(View view){
        Intent intent = new Intent();
        intent.setClass(this,RegisterActivity.class);
        startActivity(intent);
    }
}*/
