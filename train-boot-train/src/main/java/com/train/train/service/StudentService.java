package com.train.train.service;

import com.train.framework.common.utils.PageResult;
import com.train.framework.mybatis.service.BaseService;
import com.train.train.vo.StudentVO;
import com.train.train.query.StudentQuery;
import com.train.train.entity.StudentEntity;

import java.util.List;

/**
 * 学员
 *
 * @author jasonzhu 
 * @since 1.0.0 2024-11-15
 */
public interface StudentService extends BaseService<StudentEntity> {

    PageResult<StudentVO> page(StudentQuery query);

    void save(StudentVO vo);

    void update(StudentVO vo);

    void delete(List<Long> idList);

    List<String> getNameList(List<Long> idList);

    StudentVO getByToken(String token);

    String getUrl(Long id);

    String refreshUrl(Long id);
}