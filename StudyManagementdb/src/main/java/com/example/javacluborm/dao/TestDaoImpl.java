package com.example.javacluborm.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.management.Query;
import javax.transaction.Transactional;

import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.example.javacluborm.domain.Study;

//全てのメソッドはコントローラーのループ処理で、〇番目の問題を返していく前提。
//LIMITの引数は、ユーザーが対象とする

@Transactional
@Repository
public class TestDaoImpl extends BaseDao implements TestDao {

	private Random randomGenerator;

	@Override
	public List<Study> makeQuestion(Query query) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<Study> makeQuestionByUserId(Integer userId, Integer limit, Integer index)
	   throws Exception {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Study> makeQuestion(String query) throws Exception {

		return getSession().createQuery(query).list();
	}

	@Override //	メソッド内で本日のDateを取得する。
	public List<Study> makeQuestionTodays(Integer limit, Integer index) throws Exception {
		List<Study> oneQuestion = new ArrayList<Study>();
		Date today = new Date();
		oneQuestion.add(
		   (Study) getSession().createCriteria(Study.class).setFetchMode("user", FetchMode.JOIN)
		      .setFetchMode("word", FetchMode.JOIN).setFetchMode("interval", FetchMode.JOIN)
		      .add(Restrictions.le("nextStudyDate", today)).setFirstResult(index) //結果から〇個目のオブジェクトを取得
		      .uniqueResult());

		Integer r1, r2, r3;
		for (int i = 0; i <= 3; i++) {
			do {
				r1 = randomGenerator.nextInt(limit);
			} while (r1 <= 0 || r1 == index); //0かindex以外の数になるまで
			do {
				r2 = randomGenerator.nextInt(limit);
			} while (r2 <= 0 || r2 == r1 || r2 == index); //0かindexかr1以外の数になるまで
			do {
				r3 = randomGenerator.nextInt(limit);
			} while (r3 <= 0 || r3 == r1 || r3 == r2 || r3 == index);//0かindexかr1かr2以外の数になるまで

		}
		return oneQuestion;
	}

	@Override
	public List<Study> makeQuestionBySubjectId(Integer subjectId, Integer limit, Integer index)
	   throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<Study> makeQuestionByUserIdSubjectId(Integer userId, Integer subjectId,
	   Integer limit, Integer index)
	   throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<Study> makeQuestionLimitedAll(Integer priority, Integer userId, Integer subjectId,
	   Integer limit,
	   Integer index) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
