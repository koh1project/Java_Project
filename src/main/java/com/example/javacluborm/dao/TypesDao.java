package com.example.javacluborm.dao;

import java.util.List;

import com.example.javacluborm.domain.Subject;
import com.example.javacluborm.domain.UserType;

public interface TypesDao {

	public  List<Subject> findAllSubjects() throws Exception;
	public  List<UserType> findAllUserTypes() throws Exception;
	public  void insertSubject(Subject newSubject) throws Exception;
	public  void changeSubjectName(Subject newSubject) throws Exception;
	public  void insertUserTypes(UserType newUserType) throws Exception;
	public  void changeUserTypesName(UserType newUserType) throws Exception;
	public Subject findBySubjectId (Integer subjectId)throws Exception;



//	public Integer getUserTypesId(Integer loginUserId)throws Exception;

//	外部キーを対象とするため削除機能は持たせない
}
