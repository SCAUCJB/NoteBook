package scau.edu.cn.notebook.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

/**
 * 作用：本地数据库存储的登录对象
 */
@Table(name = "UserLocal")
public class UserLocal extends Model implements Serializable {

    //对象Id
    @Column(name = "objectId")
    String objectId;
    //姓名
    @Column(name = "Name")
    String Name;
    //邮箱
    //@Column(name = "Email")
    String Email;
    //头像
    @Column(name = "Photo")
    String Photo;
    //密码
    @Column(name = "Password")
    String Password;
    //手机号码
    @Column(name = "Number")
    String Number;

    /*public UserLocal() {
        super();
    }*/

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

   /* public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }*/

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

}
