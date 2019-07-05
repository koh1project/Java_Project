package com.example.javacluborm.dao;

import java.util.List;

import javax.management.Query;

import com.example.javacluborm.domain.Study;

public interface TestDao {

//全てのメソッドはループ処理の中で、〇番目の問題を返していく前提。
//そのため、常に引数にLIMITの引数を受け取る。



	public List<Study> makeQuestion (Query query)throws Exception;

	public List<Study> makeQuestion(String query)throws Exception;
	//	メソッド内で本日のDateを取得する。
	public List<Study>makeQuestionTodays(Integer limit  ,Integer index)throws Exception;

	public List<Study> makeQuestionByUserId(Integer userId , Integer limit  ,Integer index) throws Exception;
	public List<Study> makeQuestionBySubjectId(Integer subjectId , Integer limit  ,Integer index) throws Exception;
	public List<Study> makeQuestionByUserIdSubjectId(Integer userId,
			Integer subjectId , Integer limit  ,Integer index) throws Exception;
	public List<Study> makeQuestionLimitedAll(Integer priority, Integer userId,
			Integer subjectId , Integer limit  ,Integer index) throws Exception;
}
