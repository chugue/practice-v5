package shop.mtcoding.blog.user;


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
    @Autowired
    private UserService userService;

    @Test
    public void 회원정보수정_test(){
        // given
        User sessionUser = new User();
        sessionUser.setId(1);
        UserRequest.UpdateDTO reqDTO = new UserRequest.UpdateDTO();
        reqDTO.setUsername("안녕");
        reqDTO.setPassword("123455");
        reqDTO.setEmail("chugue@nate.com");
        // when
        userService.회원정보수정(sessionUser.getId(), reqDTO);

        // then
        Optional<User> userOP = userJPARepository.findById(1);
        System.out.println(userOP.get().getUsername());
        System.out.println(userOP.get().getEmail());
    }

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
