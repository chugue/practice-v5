package shop.mtcoding.blog.board;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.errors.exception.Exception401;
import shop.mtcoding.blog._core.errors.exception.Exception403;
import shop.mtcoding.blog._core.errors.exception.Exception404;
import shop.mtcoding.blog.user.User;
import shop.mtcoding.blog.user.UserJPARepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardJPARepository boardJPARepository;
    private final UserJPARepository userJPARepository;


    @Transactional
    public void 글삭제 (int sessionUserId, int boardId){
        Board board = boardJPARepository.findById(boardId)
                        .orElseThrow(() -> new Exception404("해당 게시글을 찾을 수 없습니다."));
        if (sessionUserId != board.getUser().getId()){
            throw new Exception403("게시글 삭제 권한이 없습니다.");
        }
        boardJPARepository.deleteById(boardId);
    }

    @Transactional
    public void 글수정(int sessionUserId, int boardId, BoardRequest.UpdateDTO reqDTO) {
        Board board = boardJPARepository.findById(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다."));

        if (sessionUserId != board.getUser().getId()){
            throw new Exception403("게시글을 수정할 권한이 없습니다.");
        }
        board.setTitle(reqDTO.getTitle());
        board.setContent(reqDTO.getContent());
    }


    public Board 게시글수정폼 (int boardId, int sessionUserId){
        Board board = boardJPARepository.findById(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다."));

        if (sessionUserId != board.getUser().getId()){
            throw new Exception403("접근 권한이 없습니다.");
        }
        return board;
    }

    @Transactional
    public void 글쓰기 (User sessionUser, BoardRequest.SaveDTO reqDTO){
        boardJPARepository.save(reqDTO.toEntity(sessionUser));
    }
}
