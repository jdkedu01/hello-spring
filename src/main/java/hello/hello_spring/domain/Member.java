package hello.hello_spring.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity  // JPA에서 인식
public class Member {
    @Id  // JPA에서 인식
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}