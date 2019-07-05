package com.example.javacluborm.dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.FetchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.example.javacluborm.domain.Word;

@Transactional
@Repository
public class WordDaoImpl extends BaseDao implements WordDao {


	@SuppressWarnings("unchecked")
	public List<Word> findAllWord() throws Exception {

		return getSession().createCriteria(Word.class).setFetchMode("user", FetchMode.JOIN)
		   .setFetchMode("subject", FetchMode.JOIN).list();
	}

	//	@SuppressWarnings("unchecked")
	//	@Override
	//	public List<Study> findTodayListByPage(int page, int numPerPage) throws Exception {
	//		Date today = new Date();
	//		int offset = numPerPage * (page - 1);
	//
	//		return getSession().createCriteria(Study.class).setFetchMode("user", FetchMode.JOIN)
	//				.setFetchMode("word", FetchMode.JOIN).setFetchMode("interval", FetchMode.JOIN)
	//				.add(Restrictions.le("nextStudyDate", today)).setFirstResult(offset).setMaxResults(numPerPage).list();
	//	}

	@Override
	public void insert(Word word) throws Exception {

		word.setCreated(new Date());
		getSession().save(word);
	}

	@Override
	public void delete(Word word) throws Exception {
		getSession().delete(word);

	}

	@Override
	public void update(Word word) throws Exception {
		getSession().update(word);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Word> findWordsByPage(int page, int numPerPage) throws Exception {
		int offset = numPerPage * (page - 1);

		return getSession().createCriteria(Word.class).setFetchMode("user", FetchMode.JOIN)
		   .setFetchMode("subject", FetchMode.JOIN).setFirstResult(offset).setMaxResults(numPerPage)
		   .list();
	}

	//	@SuppressWarnings("unchecked")
	//	@Override
	//	public List<Study> findStudiesByPage(int page, int numPerPage) throws Exception {
	//		int offset = numPerPage * (page - 1);
	//
	//		return getSession().createCriteria(Study.class).setFetchMode("user", FetchMode.JOIN)
	//				.setFetchMode("word", FetchMode.JOIN).setFetchMode("interval", FetchMode.JOIN).setFirstResult(offset)
	//				.setMaxResults(numPerPage).list();
	//	}

	@Override
	public int totalWordsPages(int numPerPage) throws Exception {
		Long count = (Long) getSession().createCriteria(Word.class)
		   .setProjection(Projections.rowCount())
		   .uniqueResult();

		return (int) Math.ceil((double) count / numPerPage);
	}

	//	@Override
	//	public Study findById(Integer loginUserId, Integer wordId) throws Exception {
	//
	//		Study result = (Study) getSession().createCriteria(Study.class).setFetchMode("user", FetchMode.JOIN)
	//				.setFetchMode("word", FetchMode.JOIN).setFetchMode("interval", FetchMode.JOIN)
	//				.add(Restrictions.eqOrIsNull("word.id", wordId)).add(Restrictions.eqOrIsNull("user.id", loginUserId))
	//				.uniqueResult();
	//
	//		return result;
	//	}

	@Override
	public Word findById(Integer wordId) throws Exception {

		return (Word) getSession().createCriteria(Word.class).setFetchMode("user", FetchMode.JOIN)
		   .setFetchMode("subject", FetchMode.JOIN).add(Restrictions.eqOrIsNull("id", wordId))
		   .uniqueResult();
			}

	@SuppressWarnings("unchecked")
	@Override
	public List<Word> searchWordsByPage(String keyWord, int page, int numPerPage) throws Exception {
		int offset = numPerPage * (page - 1);

		return getSession().createCriteria(Word.class).setFetchMode("user", FetchMode.JOIN)
		   .setFetchMode("subject", FetchMode.JOIN)
		   .add(Restrictions.or(Restrictions.like("name", "%" + keyWord + "%"),
		      (Restrictions.like("detail", "%" + keyWord + "%"))))
		   .setFirstResult(offset).setMaxResults(numPerPage).list();
	}

	@Override
	public int totalsearchWordsPages(String keyWord, int numPerPage) throws Exception {

		Long count = (Long) getSession().createCriteria(Word.class)
		   .add(Restrictions.or(Restrictions.like("name", "%" + keyWord + "%"),
		      (Restrictions.like("detail", "%" + keyWord + "%"))))
		   //		   .add(Restrictions.like("name", "%" + keyWord + "%"))
		   //		   .add(Restrictions.like("detail", "%" + keyWord + "%"))
		   .setProjection(Projections.rowCount())
		   .uniqueResult();

		return (int) Math.ceil((double) count / numPerPage);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Word> query(String query) throws Exception {

		return getSession().createQuery(query).list();
	}

	//	@Override
	//	public int totalStudiesPages(int numPerPage) throws Exception {
	//		Long count = (Long) getSession().createCriteria(Study.class).setProjection(Projections.rowCount())
	//				.uniqueResult();
	//
	//		return (int) Math.ceil((double) count / numPerPage);
	//
	//	}
}
