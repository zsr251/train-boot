package com.train.train.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.train.framework.common.api.ParamsApi;
import com.train.framework.common.exception.ServerException;
import com.train.framework.common.utils.PageResult;
import com.train.framework.datapermission.utils.DataPermissionUtils;
import com.train.framework.mybatis.service.impl.BaseServiceImpl;
import com.train.train.constant.TrainConstant;
import com.train.train.convert.StudentConvert;
import com.train.train.dao.StudentDao;
import com.train.train.entity.StudentEntity;
import com.train.train.query.StudentQuery;
import com.train.train.service.StudentService;
import com.train.train.vo.StudentVO;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 学员
 *
 * @author jasonzhu
 * @since 1.0.0 2024-11-15
 */
@Service
@AllArgsConstructor
public class StudentServiceImpl extends BaseServiceImpl<StudentDao, StudentEntity> implements StudentService {
    private final ParamsApi paramsApi;

    @Override
    public PageResult<StudentVO> page(StudentQuery query) {
        IPage<StudentEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(StudentConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
    }

    private LambdaQueryWrapper<StudentEntity> getWrapper(StudentQuery query) {
        LambdaQueryWrapper<StudentEntity> wrapper = Wrappers.lambdaQuery();
        wrapper.like(StringUtils.isNotEmpty(query.getStudentName()), StudentEntity::getStudentName, query.getStudentName());
        wrapper.like(StringUtils.isNotEmpty(query.getPhone()), StudentEntity::getPhone, query.getPhone());
        wrapper.eq(StringUtils.isNotEmpty(query.getStudentType()), StudentEntity::getStudentType, query.getStudentType());
        wrapper.eq(StringUtils.isNotEmpty(query.getSourceType()), StudentEntity::getSourceType, query.getSourceType());
        wrapper.eq(StringUtils.isNotEmpty(query.getStatus()), StudentEntity::getStatus, query.getStatus());
        wrapper.eq(StringUtils.isNotEmpty(query.getFollowupStatus()), StudentEntity::getFollowupStatus, query.getFollowupStatus());
        wrapper.like(StringUtils.isNotEmpty(query.getFollowupPerson()), StudentEntity::getFollowupPerson, query.getFollowupPerson());
        wrapper.like(StringUtils.isNotEmpty(query.getAcademicAdvisor()), StudentEntity::getAcademicAdvisor, query.getAcademicAdvisor());
        wrapper.like(StringUtils.isNotEmpty(query.getIdCard()), StudentEntity::getIdCard, query.getIdCard());
        DataPermissionUtils.wrapDataPermission(null, wrapper);
        return wrapper;
    }

    @Override
    public void save(StudentVO vo) {
        StudentEntity entity = StudentConvert.INSTANCE.convert(vo);

        baseMapper.insert(entity);
    }

    @Override
    public void update(StudentVO vo) {
        StudentEntity entity = StudentConvert.INSTANCE.convert(vo);

        updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(List<Long> idList) {
        removeByIds(idList);
    }

    @Override
    public List<String> getNameList(List<Long> idList) {
        if (idList.isEmpty()) {
            return null;
        }

        return baseMapper.selectBatchIds(idList).stream().map(StudentEntity::getStudentName).toList();
    }

    @Override
    public StudentVO getByToken(String token) {
        Validator.validateNotEmpty(token, "token不能为空");
        StudentEntity entity = baseMapper.selectOne(new LambdaQueryWrapper<StudentEntity>().eq(StudentEntity::getToken, token), false);
        if (entity == null){
            return null;
        }
        return StudentConvert.INSTANCE.convert(entity);
    }

    @Override
    public String getUrl(Long id) {
        StudentEntity entity = getById(id);
        if (entity == null) {
            throw new ServerException("未找到学员");
        }
        if (!TrainConstant.STUDENT_STATUS_NORMAL.equals(entity.getStatus())) {
            throw new ServerException("学员状态错误");
        }
        String token = entity.getToken();
        // 如果是空
        if (StrUtil.isBlank(token)) {
            token = StrUtil.uuid().replace("-", "");
            StudentEntity up = new StudentEntity();
            up.setId(id);
            up.setToken(token);
            baseMapper.updateById(up);
        }
        String u = paramsApi.getString("student_h5");
        if (Validator.isUrl(u)) {
            return StrUtil.format("{}?token={}", u, token);
        }
        return token;
    }

    @Override
    public String refreshUrl(Long id) {
        StudentEntity entity = getById(id);
        if (entity == null) {
            throw new ServerException("未找到学员");
        }
        if (!TrainConstant.STUDENT_STATUS_NORMAL.equals(entity.getStatus())) {
            throw new ServerException("学员状态错误");
        }
        String token = StrUtil.uuid().replace("-", "");
        StudentEntity up = new StudentEntity();
        up.setId(id);
        up.setToken(token);
        baseMapper.updateById(up);
        String u = paramsApi.getString("student_h5");
        if (Validator.isUrl(u)) {
            return StrUtil.format("{}?token={}", u, token);
        }
        return token;
    }


}