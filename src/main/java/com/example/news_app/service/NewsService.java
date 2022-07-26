package com.example.news_app.service;

import com.example.news_app.component.DateFormatter;
import com.example.news_app.dto.NewsDto;
import com.example.news_app.dto.ResNewsDto;
import com.example.news_app.entity.Category;
import com.example.news_app.entity.News;
import com.example.news_app.entity.User;
import com.example.news_app.exception.ResourceNotFoundException;
import com.example.news_app.repository.CategoryRepository;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final DateFormatter dateFormatter;
    public Apiresponse add(NewsDto newsDto) {
        News news=new News();
        Category category = categoryRepository.findById(newsDto.getCategory_id()).orElseThrow(() -> new ResourceNotFoundException("Category", "id", newsDto.getCategory_id()));
        User user = userRepository.findById(newsDto.getUser_id()).orElseThrow(() -> new ResourceNotFoundException("User", "id", newsDto.getUser_id()));
        news.setName(newsDto.getName());
        news.setCategory(category);
        news.setUser(user);
        News save = newsRepository.save(news);
        if (save!=null){
            return Apiresponse.builder().message("Added").succes(true).data(toDto(save)).build();
        }
        return Apiresponse.builder().message("Xatolik yuz berdi").succes(false).build();
    }

    public Apiresponse getAll(int page, int size, String name, String categoryName, String date) throws ParseException {
        Pageable pageable=PageRequest.of(page, size);
        Page<News> all=null;
        if (name.equals("")&&categoryName.equals("")&&date.equals("")){
           all=newsRepository.findAll(pageable);
        }
        else {
            Date date1 = dateFormatter.stringToDate(date);
          all=newsRepository.findAllByNameContainingIgnoreCaseAndCategory_NameContainingIgnoreCaseAndCreatedate(name,categoryName,date1,pageable);
        }
        if (all==null){
            return Apiresponse.builder().succes(false).message("Bunday nomli News mavjud emas").build();
        }
        return Apiresponse.builder().succes(true).message("News list").data(toDTOPage(all)).build();
    }

    public Apiresponse getOne(UUID id) {
        News news = newsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("News", "id", id));
        return Apiresponse.builder().succes(true).message("News").data(toDto(news)).build();
    }

    public Apiresponse update(NewsDto newsDto, UUID id) {
        News news = newsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("News", "id", id));
        Category category = categoryRepository.findById(newsDto.getCategory_id()).orElseThrow(() -> new ResourceNotFoundException("Category", "id", newsDto.getCategory_id()));
        news.setCategory(category);
        User user = userRepository.findById(newsDto.getUser_id()).orElseThrow(() -> new ResourceNotFoundException("User", "id", newsDto.getUser_id()));
        news.setName(newsDto.getName());
        news.setUser(user);
        News save = newsRepository.save(news);
        return Apiresponse.builder().message("Updated").data(toDto(save)).succes(true).build();
    }
    public Apiresponse delete(UUID id) {
        boolean exists = newsRepository.existsById(id);
        if (exists){
            newsRepository.deleteById(id);
            return Apiresponse.builder().succes(true).message("Deleted").build();
        }
        return Apiresponse.builder().succes(false).message("Xatolik yuz berdi").build();
    }

    public ResNewsDto toDto(News news){
        ResNewsDto resNewsDto=new ResNewsDto();
        resNewsDto.setName(news.getName());
        resNewsDto.setCategoryName(news.getCategory().getName());
        resNewsDto.setCreateDate(String.valueOf(news.getCreatedate()));
        resNewsDto.setUserName(news.getUser().getFullName());
        return resNewsDto;
    }
    public Page<ResNewsDto> toDTOPage(Page<News> newsPage) {
    List<ResNewsDto> collect = newsPage.stream().map(this::toDto).collect(Collectors.toList());
    return new PageImpl<>(collect);
}

    public Apiresponse getUserNews() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<News> all = newsRepository.findAllByUser_Password(authentication.getCredentials());
        return Apiresponse.builder().data(all).build();
    }
}
