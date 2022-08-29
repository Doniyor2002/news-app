package com.example.news_app.service;

import com.example.news_app.component.DateFormatter;
import com.example.news_app.dto.CommentDto;
import com.example.news_app.dto.NewsDto;
import com.example.news_app.dto.ResCommentDto;
import com.example.news_app.entity.Category;
import com.example.news_app.entity.Comment;
import com.example.news_app.entity.News;
import com.example.news_app.entity.User;
import com.example.news_app.exception.ResourceNotFoundException;
import com.example.news_app.repository.CommentRepository;
import com.example.news_app.repository.NewsRepository;
import com.example.news_app.repository.UserRepository;
import com.example.news_app.response.Apiresponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final NewsRepository newsRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final DateFormatter dateFormatter;
    public Apiresponse add(CommentDto commentDto) {
        Comment comment=new Comment();
        Object credentials = SecurityContextHolder.getContext().getAuthentication().getCredentials();
        User user1 = userRepository.findByPassword(credentials).orElseThrow(() -> new ResourceNotFoundException("User", "name", SecurityContextHolder.getContext().getAuthentication().getName()));
        comment.setUser(user1);
        comment.setName(commentDto.getName());
        News news = newsRepository.findById(commentDto.getNews_id()).orElseThrow(() -> new ResourceNotFoundException("News", "id", commentDto.getNews_id()));
        comment.setNews(news);
        Comment save = commentRepository.save(comment);
        if (save!=null){
            return Apiresponse.builder().message("Added").succes(true).data(toDto(save)).build();
        }
        return Apiresponse.builder().message("Xatolik yuz berdi").succes(false).build();
    }

    public Apiresponse getAll(int page, int size, String name, String date) throws ParseException {
        Pageable pageable=PageRequest.of(page, size);
        Page<Comment> all=null;
        if (name.equals("")&&date.equals("")){
           all=commentRepository.findAll(pageable);
        }
        else {
            Date date1 = dateFormatter.stringToDate(date);
          all=commentRepository.findAllByNameContainingIgnoreCaseAndCreatedate(name,date1,pageable);
        }
        if (all==null){
            return Apiresponse.builder().succes(false).message("Bunday nomli Comment mavjud emas").build();
        }
        return Apiresponse.builder().succes(true).message("Comment list").data(toDTOPage(all)).build();
    }

    public Apiresponse getOne(UUID id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
        return Apiresponse.builder().succes(true).message("Comment").data(toDto(comment)).build();
    }

    public Apiresponse update(CommentDto commentDto, UUID id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
        News news = newsRepository.findById(commentDto.getNews_id()).orElseThrow(() -> new ResourceNotFoundException("News", "id", commentDto.getNews_id()));
        comment.setName(commentDto.getName());
        comment.setNews(news);
        Comment save = commentRepository.save(comment);
        return Apiresponse.builder().message("Updated").data(toDto(save)).succes(true).build();
    }
    public Apiresponse delete(UUID id) {
        boolean exists = commentRepository.existsById(id);
        if (exists){
            commentRepository.deleteById(id);
            return Apiresponse.builder().succes(true).message("Deleted").build();
        }
        return Apiresponse.builder().succes(false).message("Xatolik yuz berdi").build();
    }
    public Apiresponse getUserComment(HttpServletRequest request,int page, int size) {
        Pageable pageable=PageRequest.of(page, size);
        String phone = userService.getPhonetoUser(request);
        Page<Comment> all = commentRepository.findAllByUser_Phone(phone,pageable);
        return Apiresponse.builder().data(toDTOPage(all)).message("News List").succes(true).build();
    }
    public ResCommentDto toDto(Comment comment){
        ResCommentDto resCommentDto=new ResCommentDto();
        resCommentDto.setCreateDate(String.valueOf(comment.getCreatedate()));
        resCommentDto.setUserName(comment.getUser().getFullName());
        resCommentDto.setName(comment.getName());
        resCommentDto.setNewsName(comment.getNews().getName());
        return resCommentDto;
    }
    public Page<ResCommentDto> toDTOPage(Page<Comment> comments) {
    List<ResCommentDto> collect = comments.stream().map(this::toDto).collect(Collectors.toList());
    return new PageImpl<>(collect);
}



}
