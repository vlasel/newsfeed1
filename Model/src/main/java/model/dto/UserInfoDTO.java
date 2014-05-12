package model.dto;

import java.io.Serializable;

public class UserInfoDTO implements Serializable{
    private static final long serialVersionUID = 103L;

    public UserInfoDTO(){
    }


	private String name;
	private String surname;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        UserInfoDTO userInfoDTO = (UserInfoDTO) obj;

        if (name != null ? !name.equals(userInfoDTO.name) : userInfoDTO.name != null) return false;
        if (surname != null ? !surname.equals(userInfoDTO.surname) : userInfoDTO.surname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "UserInfoDTO{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
