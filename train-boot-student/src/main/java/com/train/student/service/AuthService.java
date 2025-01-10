package com.train.student.service;

import com.train.student.vo.StudentChangePasswordVO;
import com.train.student.vo.StudentLoginVO;
import com.train.train.vo.StudentVO;

public interface AuthService {
    String login(StudentLoginVO loginVO);

    void logout(String token);

    StudentVO checkToken(String token);

    void changePassword(String token, StudentChangePasswordVO vo);

    void kickOut(Long id);
}
