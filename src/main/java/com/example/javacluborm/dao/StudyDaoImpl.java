package com.example.javacluborm.dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.FetchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.example.javacluborm.domain.Study;

@Transactional
@Repository
public class StudyDaoImpl extends BaseDao implements StudyDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Study> findALL() throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		return getSession().createCriteria(Study.class).setFetchMode("user", FetchMode.JOIN)
		   .setFetchMode("word", FetchMode.JOIN).setFetchMode("interval", FetchMode.JOIN).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Study> findTodayListByPage(Integer loginUserId, int page, int numPerPage)
	   throws Exception {
		Date today = new Date();
		int offset = numPerPage * (page - 1);

		return getSession().createCriteria(Study.class).setFetchMode("user", FetchMode.JOIN)
		   .setFetchMode("word", FetchMode.JOIN).setFetchMode("interval", FetchMode.JOIN)
		   .setFetchMode("word.subject", FetchMode.JOIN).add(Restrictions.le("nextStudyDate", today))
		   .add(Restrictions.eqOrIsNull("user.id", loginUserId))
		   .setFirstResult(offset).setMaxResults(numPerPage).list();
	}

	@Override
	public Study findById(Integer loginUserId, Integer wordId) throws Exception {
		return (Study) getSession().createCriteria(Study.class).setFetchMode("user", FetchMode.JOIN)
		   .setFetchMode("word", FetchMode.JOIN).setFetchMode("interval", FetchMode.JOIN)
		   .setFetchMode("word.subject", FetchMode.JOIN).setFetchMode("word.user", FetchMode.JOIN)
		   .add(Restrictions.eqOrIsNull("word.id", wordId))
		   .add(Restrictions.eqOrIsNull("user.id", loginUserId)).uniqueResult();
	}

	@Override
	public void insert(Study study) throws Exception {
		study.setCreated(new Date());
		getSession().save(study);

	}

	@Override
	public void delete(Study study) throws Exception {
		getSession().delete(study);

	}

	@Override
	public void update(Study study) throws Exception {
		getSession().update(study);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Study> findStudiesByPage(Integer loginUserId, int page, int numPerPage)
	   throws Exception {
		int offset = numPerPage * (page - 1);

		return getSession().createCriteria(Study.class).setFetchMode("user", FetchMode.JOIN)
		   .setFetchMode("word", FetchMode.JOIN).setFetchMode("interval", FetchMode.JOIN)
		   .setFetchMode("word.subject", FetchMode.JOIN)
		   .add(Restrictions.eqOrIsNull("user.id", loginUserId))
		   .setFirstResult(offset).setMaxResults(numPerPage).list();
	}

	@Override
	public int totalStudiesPages(Integer loginUserId, int numPerPage) throws Exception {
		Long count = (Long) getSession().createCriteria(Study.class)
		   .setFetchMode("user", FetchMode.JOIN)
		   .add(Restrictions.eqOrIsNull("user.id", loginUserId)).setProjection(Projections.rowCount())
		   .uniqueResult();

		return (int) Math.ceil((double) count / numPerPage);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Study> findBySubject(Integer subjectId, Integer loginUserId, Integer exceptionId)
	   throws Exception {

		return getSession().createCriteria(Study.class).setFetchMode("user", FetchMode.JOIN)
		   .setFetchMode("word", FetchMode.JOIN).setFetchMode("interval", FetchMode.JOIN)
		   .setFetchMode("word.subject", FetchMode.JOIN).createAlias("word", "word")
		   .createAlias("word.subject", "word.subject")
		   .add(Restrictions.eqOrIsNull("word.subject.id", subjectId))
		   .add(Restrictions.eqOrIsNull("user.id", loginUserId))
		   .add(Restrictions.neOrIsNotNull("id", exceptionId))
		   .list();
	}

	@Override
	public int countStudiesBySubject(Integer thisSubjectId, Integer loginUserId) throws Exception {

		Long l = (Long) getSession().createCriteria(Study.class).setFetchMode("user", FetchMode.JOIN)
		   .setFetchMode("interval", FetchMode.JOIN).setFetchMode("word", FetchMode.JOIN)
		   .setFetchMode("word.subject", FetchMode.JOIN).createAlias("word", "w")
		   .createAlias("w.subject", "s")
		   .add(Restrictions.eqOrIsNull("user.id", loginUserId))
		   .add(Restrictions.eq("s.id", thisSubjectId))
		   .setProjection(Projections.rowCount()).uniqueResult();

		Integer i = new Integer(l.toString());
		return i;
	}

	@Override
	public int countStudiesByLoginUser(Integer loginUserId) throws Exception {

		Long l = (Long) getSession().createCriteria(Study.class).setFetchMode("user", FetchMode.JOIN)
		   .setFetchMode("word", FetchMode.JOIN).setFetchMode("interval", FetchMode.JOIN)
		   .setFetchMode("word.subject", FetchMode.JOIN)
		   .add(Restrictions.eqOrIsNull("user.id", loginUserId))
		   .setProjection(Projections.rowCount()).uniqueResult();
		Integer i = new Integer(l.toString());

		return i;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Study> findStudyiesRandom(Integer loginUserId, Integer exceptionId)
	   throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		return getSession().createCriteria(Study.class).setFetchMode("user", FetchMode.JOIN)
		   .setFetchMode("word", FetchMode.JOIN).setFetchMode("interval", FetchMode.JOIN)
		   .setFetchMode("word.subject", FetchMode.JOIN)
		   .add(Restrictions.eqOrIsNull("user.id", loginUserId))
		   .add(Restrictions.neOrIsNotNull("id", exceptionId)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Study> findCompulsoryStudies(Integer exceptionId) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		return getSession().createCriteria(Study.class).setFetchMode("user", FetchMode.JOIN)
		   .setFetchMode("word", FetchMode.JOIN).setFetchMode("interval", FetchMode.JOIN)
		   .setFetchMode("word.subject", FetchMode.JOIN).createAlias("word", "w")
		   .add(Restrictions.eqOrIsNull("w.compulsory", 1))
		   .add(Restrictions.neOrIsNotNull("id", exceptionId))
		   .list();
	}

	@Override
	public int totalStudy() throws Exception {
		Long l = (Long) getSession().createCriteria(Study.class).setProjection(Projections.rowCount())
		   .uniqueResult();
		Integer i = new Integer(l.toString());
		return (int) i;
	}

	@Override
	public Study findById(Integer studyId) throws Exception {

		return (Study) getSession().createCriteria(Study.class).setFetchMode("user", FetchMode.JOIN)
		   .setFetchMode("word", FetchMode.JOIN).setFetchMode("nextInterval", FetchMode.JOIN)
		   .setFetchMode("word.subject", FetchMode.JOIN)
		   .add(Restrictions.eqOrIsNull("id", studyId)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Study> findByWordId(Integer wordId) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		return getSession().createCriteria(Study.class).setFetchMode("user", FetchMode.JOIN)
		   .setFetchMode("word", FetchMode.JOIN).setFetchMode("interval", FetchMode.JOIN)
		   .setFetchMode("word.subject", FetchMode.JOIN)
		   .add(Restrictions.eqOrIsNull("word.id", wordId))
		   .list();
	}

	@Override
	public int totalTodayStudies(Integer loginUserId, int numPerPage) throws Exception {
		Date today = new Date();
		Long count = (Long) getSession().createCriteria(Study.class)
		   .setFetchMode("user", FetchMode.JOIN)
		   .add(Restrictions.eqOrIsNull("user.id", loginUserId))
		   .add(Restrictions.le("nextStudyDate", today)).setProjection(Projections.rowCount())
		   .uniqueResult();

		return (int) Math.ceil((double) count / numPerPage);
	}

}
