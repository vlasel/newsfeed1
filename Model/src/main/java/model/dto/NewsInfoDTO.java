package model.dto;

import java.io.Serializable;

public class NewsInfoDTO implements Serializable{
    private static final long serialVersionUID = 102L;

    public NewsInfoDTO(){
    }

    Integer id;
    String name;
    String date;
    String time;
    String author;
    String annotation;


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
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAnnotation() {
        return annotation;
    }
    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewsInfoDTO newsInfoDTO = (NewsInfoDTO) o;

//        if (id != null ? !id.equals(newsInfoDTO.id) : newsInfoDTO.id != null) return false;
        if (annotation != null ? !annotation.equals(newsInfoDTO.annotation) : newsInfoDTO.annotation != null) return false;
        if (author != null ? !author.equals(newsInfoDTO.author) : newsInfoDTO.author != null) return false;
        if (date != null ? !date.equals(newsInfoDTO.date) : newsInfoDTO.date != null) return false;
        if (name != null ? !name.equals(newsInfoDTO.name) : newsInfoDTO.name != null) return false;
        if (time != null ? !time.equals(newsInfoDTO.time) : newsInfoDTO.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (annotation != null ? annotation.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NewsInfoDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", author='" + author + '\'' +
                ", annotation='" + annotation + '\'' +
                '}';
    }
}
