package study.querydsl.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberDto {

    String username;

    Integer age;

    public MemberDto(String username, Integer age) {
        this.username = username;
        this.age = age;
    }
}
