package yongfa365;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonFeatures {
    public static void main(String[] args) {
        //@Builder
        User user1 = User.builder().id(111).userName("Name111").build();
        log.info("user1: {}", user1);

        //@Data
        User user2 = new User(222, "Name222");
        user2.setId(222);
        user2.setUserName("我是谁2222");
        user2.toString();
        user2.equals(user1);
        log.info("user2: {}", user2);


        //@AllArgsConstructor
        var user3 = new User(3333, "Name333");
        log.info("user3: {}", user3);

        //@NoArgsConstructor
        var user4 = new User();
        log.info("user4: {}", user4);

    }
}


@Data  // == @ToString+@EqualsAndHashCode+@Getter +@Setter+ @RequiredArgsConstructor!
@Builder
@AllArgsConstructor
@NoArgsConstructor
class User {
    int id;
    String userName;
}
