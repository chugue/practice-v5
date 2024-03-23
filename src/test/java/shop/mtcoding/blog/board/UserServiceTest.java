package shop.mtcoding.blog.board;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import shop.mtcoding.blog.user.User;
import shop.mtcoding.blog.user.UserJPARepository;
import shop.mtcoding.blog.user.UserRequest;
import shop.mtcoding.blog.user.UserService;

import java.util.Optional;

@Import(UserService.class)
@DataJpaTest
public class UserServiceTest {
    @Autowired
    private UserJPARepository userJPARepository;

    @Test
    public void findByUsernameAndPassword_test(){
        // given
        UserRequest.LoginDTO reqDTO = new UserRequest.LoginDTO();
        reqDTO.setUsername("ssar");
        reqDTO.setPassword("1234");
        // when
        Optional<User> loginUser = userJPARepository.findByUsernameAndPassword(reqDTO.getUsername(), reqDTO.getPassword());
        // then
        System.out.println(loginUser.get().getUsername());
        System.out.println(loginUser.get().getPassword());
    }
}
