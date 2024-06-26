package shop.mtcoding.blog.user;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.errors.exception.Exception400;
import shop.mtcoding.blog._core.errors.exception.Exception401;
import shop.mtcoding.blog._core.errors.exception.Exception404;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserJPARepository userJPARepository;

    @Transactional
    public User 회원정보수정 (int id, UserRequest.UpdateDTO reqDTO){
        User user = userJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("회원정보를 찾을 수 없습니다."));
        user.setUsername(reqDTO.getUsername());
        user.setPassword(reqDTO.getPassword());
        user.setEmail(reqDTO.getEmail());
        return user;
    }

    public User 회원수정폼 (int id){
        return userJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("회원정보를 찾을 수 없습니다"));
    }


    public User 로그인 (UserRequest.LoginDTO reqDTO){
        User sessionUser = userJPARepository.findByUsernameAndPassword(reqDTO.getUsername(), reqDTO.getPassword())
                .orElseThrow(() -> new Exception401("인증되지 않았습니다."));
        return sessionUser;
    }

    @Transactional
    public void 회원가입 (UserRequest.JoinDTO reqDTO){
        Optional<User> userOP = userJPARepository.findByUsername(reqDTO.getUsername());

        if (userOP.isPresent()){
            throw new Exception400("중복된 유저네임입니다.");
        }
        userJPARepository.save(reqDTO.toEntity());
    }
}
