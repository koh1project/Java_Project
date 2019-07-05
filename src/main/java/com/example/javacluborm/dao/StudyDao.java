package com.example.javacluborm.dao;

import java.util.List;

import com.example.javacluborm.domain.Study;

public interface StudyDao {
	public List<Study> findALL() throws Exception;

	public List<Study> findTodayListByPage(Integer loginUserId, int page, int numPerPage)
	   throws Exception;

	public Study findById(Integer loginUserId, Integer wordId) throws Exception;

	public Study findById(Integer studyId) throws Exception;

	public void insert(Study study) throws Exception;

	public void delete(Study study) throws Exception;

	public void update(Study study) throws Exception;

	public List<Study> findStudiesByPage(Integer loginUserId, int page, int numPerPage)
	   throws Exception;

	public int totalStudiesPages(Integer loginUserId, int numPerPage) throws Exception;

	public List<Study> findBySubject(Integer subjectId, Integer loginUserId, Integer exceptionId)
	   throws Exception;

	public int countStudiesBySubject(Integer thisSubjectId, Integer loginUserId) throws Exception;

	public int countStudiesByLoginUser(Integer loginUserId) throws Exception;

	public List<Study> findStudyiesRandom(Integer loginUserId, Integer exceptionId) throws Exception;

	public List<Study> findCompulsoryStudies(Integer exceptionId) throws Exception;

	public int totalStudy() throws Exception;

	public List<Study> findByWordId(Integer wordId) throws Exception;

	public int totalTodayStudies(Integer loginUserId, int numPerPage) throws Exception;
}
