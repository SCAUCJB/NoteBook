package scau.edu.cn.notebook.activity;

/*
public class RegisterActivity2 extends AppCompatActivity {
    EditText uName,uEmail,uPassword;
    Boolean isOK = true;
    String email,name,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        uName = (EditText)findViewById(R.id.user_name);
        uEmail = (EditText)findViewById(R.id.user_email);
        uPassword = (EditText)findViewById(R.id.user_password);
    }

    public void signUp(View view){
         email = uEmail.getText().toString();
         name = uName.getText().toString();
         password = uPassword.getText().toString();
        if((!email.isEmpty())&&(!name.isEmpty())&&(!password.isEmpty())){
            BmobQuery<User> bmobQuery = new BmobQuery<>();
            bmobQuery.findObjects(new FindListener<User>() {
                @Override
                public void done(List<User> list, BmobException e) {
                    if(e==null){
                        for(User user:list){
                            if(user.getName().equals(name)){
                                Toast.makeText(RegisterActivity2.this, "该昵称已存在", Toast.LENGTH_SHORT).show();
                                isOK = false;
                                break;
                            }
                            if(user.getEmail().equals(email)){
                                Toast.makeText(RegisterActivity2.this, "该邮箱已被注册", Toast.LENGTH_SHORT).show();
                                isOK = false;
                                break;
                            }
                        }
                        if(isOK) {
                            User user = new User();
                            user.setEmail(email);
                            user.setName(name);
                            user.setPassword(password);
                            user.setIncome(0);
                            user.setPayout(0);
                            user.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null) {
                                        Toast.makeText(RegisterActivity2.this, "注册成功", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(RegisterActivity2.this, "注册失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }

                    }else{
                        Toast.makeText(RegisterActivity2.this,e.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }else{
            Toast.makeText(RegisterActivity2.this, "请完善注册信息", Toast.LENGTH_SHORT).show();
        }
    }
}*/
