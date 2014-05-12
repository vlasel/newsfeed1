package model.pojos;

import java.io.Serializable;
import java.util.*;


public class UserRole implements Serializable{
    private static final long serialVersionUID = 4L;
    public UserRole(){
    }


    //######### fields ##############
    private Integer id;
    private String name;
    private Set<User> userSet;
    //######### /fields ##############




    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Set<User> getUserSet() {
        return userSet;
    }
    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRole userRole = (UserRole) o;

//        if (id != null ? !id.equals(userRole.id) : userRole.id != null) return false;
        if (name != null ? !name.equals(userRole.name) : userRole.name != null) return false;
        if (userSet != null ? !userSet.equals(userRole.userSet) : userRole.userSet != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (userSet != null ? userSet.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
//        return "UserRole{" +
        return "{" +
                "id=" + id +
                ", name=" + name +
//                ", account: " + "[id:"+account.getId()+", name:"+ account.getName()+ ", surname:"+ account.getSurname()+"]" +
                /*", userSet: [" + userSet + "]"+*/
                '}';
    }



}
