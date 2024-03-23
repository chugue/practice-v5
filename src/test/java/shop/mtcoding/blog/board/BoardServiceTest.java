package shop.mtcoding.blog.board;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import shop.mtcoding.blog.user.User;
import shop.mtcoding.blog.user.UserJPARepository;

import java.util.List;

@Import(BoardService.class)
@DataJpaTest
public class BoardServiceTest {
    @Autowired
    private BoardService boardService;
    @Autowired
    private UserJPARepository userJPARepository;
    @Autowired
    private BoardJPARepository boardJPARepository;

    @Test
    public void 글쓰기_test(){
        // given
        User sessionUser = new User();
        sessionUser.builder()
                .id(1).build();
        sessionUser = userJPARepository.save(sessionUser);
        BoardRequest.SaveDTO reqDTO = new BoardRequest.SaveDTO();
        reqDTO.setTitle("안녕");
        reqDTO.setContent("내용입니다.");
        // when
        boardJPARepository.save(reqDTO.toEntity(sessionUser));
        // then
        List<Board> boardList = boardJPARepository.findAll();
        System.out.println(boardList.size());
    }
}
