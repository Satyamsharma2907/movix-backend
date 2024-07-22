package org.niit.UserAuthenticationService.domain;



import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="muzixUser")
public class User
{
    @Id
    private String email;
    private String password;
    private String name;
    private String phoneNo;



    public User() {
    }

    public  User(String email, String password, String name, String phoneNo) {

        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNo = phoneNo;

    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

//    @Override
//    public String toString() {
//        return "User{" +
//                "email='" + email + '\'' +
//                ", password='" + password + '\'' +
//                ", name='" + name + '\'' +
//                ", phoneNo='" + phoneNo + '\'' +
//                '}';
//       // return "satyam";
//    }

    public static void main(String[] args) {
        User u = new User("satyam@gmail.com","satyam123","satyam","9717719793");
        System.out.println(u.toString()
        );

    }
}
