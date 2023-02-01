package com.evan.aiu.service;

import com.evan.aiu.dao.AnimalDAO;
import com.evan.aiu.dao.CommentsDAO;
import com.evan.aiu.dto.CommentsDTO;
import com.evan.aiu.entity.Animal;
import com.evan.aiu.entity.Comments;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsService {
    @Autowired
    CommentsDAO commentsDAO;
    @Autowired
    AnimalDAO animalDAO;

    public List<CommentsDTO> list(){
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        List<Comments> all = commentsDAO.findAll(sort);
        List<CommentsDTO> result = new ArrayList<>();
        for (Comments comments : all) {
            CommentsDTO commentsDTO = new CommentsDTO();
            Animal byId = animalDAO.findById(comments.getAid());
            BeanUtils.copyProperties(comments,commentsDTO);
            if( null != byId) {
                commentsDTO.setAnimalName(byId.getName());
            }
            result.add(commentsDTO);
        }
        return result;
    }


    public List<CommentsDTO> listbyaid(Integer aid){
        List<Comments> all = commentsDAO.findByAid(aid);
        List<CommentsDTO> result = new ArrayList<>();
        for (Comments comments : all) {
            CommentsDTO commentsDTO = new CommentsDTO();
            Animal byId = animalDAO.findById(comments.getAid());
            BeanUtils.copyProperties(comments,commentsDTO);
            if( null != byId) {
                commentsDTO.setAnimalName(byId.getName());
            }
            result.add(commentsDTO);
        }
        return result;
    }

    public List<CommentsDTO> listByAdoptname(String adoptname){
        List<Comments> all = commentsDAO.findByAdoptname(adoptname);
        List<CommentsDTO> result = new ArrayList<>();
        for (Comments comments : all) {
            CommentsDTO commentsDTO = new CommentsDTO();
            Animal byId = animalDAO.findById(comments.getAid());
            BeanUtils.copyProperties(comments,commentsDTO);
            if( null != byId) {
                commentsDTO.setAnimalName(byId.getName());
            }
            result.add(commentsDTO);
        }
        return result;
    }


    //当id存在时更新数据，当id不存在时插入数据
    public void addOrUpdate(Comments comments){
        commentsDAO.save(comments);
    }

    public void deleteComments(Integer id) {
        commentsDAO.deleteById(id);
    }
}
