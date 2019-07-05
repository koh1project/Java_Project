package com.example.javacluborm.dao;

import java.util.List;

import com.example.javacluborm.domain.Login;
import com.example.javacluborm.domain.Study;
import com.example.javacluborm.domain.User;

public interface UserDao {

	public List<User> findALL() throws Exception;

	public User findById(Integer id) throws Exception;

	public User findByIdWithLogin(Integer id) throws Exception;

	public void insert(User user) throws Exception;

	public void delete(Login login) throws Exception;

	public void update(User user) throws Exception;

	public void delete(User user) throws Exception;

	public void insert(Login login) throws Exception;

	public void update(Login login) throws Exception;

	public boolean thisWordIsStudying(Integer loginUserId, Integer wordId) throws Exception;

	public List<User> findByPage(int page, int numPerPage) throws Exception;

	public int totalPages(int numPerPage) throws Exception;

	public User findByLoginIdAndLoginPass(String loginId, String loginPass) throws Exception;

	public int userProgressTotalPages(Integer userId, int numPerPage) throws Exception;

	public List<Study> userProgressFindByPage(Integer userId, int page, int numPerPage)
	   throws Exception;

}
