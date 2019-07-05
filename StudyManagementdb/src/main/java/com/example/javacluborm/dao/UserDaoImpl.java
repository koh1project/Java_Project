package com.example.javacluborm.dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import com.example.javacluborm.domain.Login;
import com.example.javacluborm.domain.Study;
import com.example.javacluborm.domain.User;

@Transactional
@Repository
public class UserDaoImpl extends BaseDao implements UserDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findALL() throws Exception {

		return getSession().createCriteria(User.class).addOrder(Order.asc("kana")).list();
	}

	@Override
	public User findById(Integer id) throws Exception {
		return (User) getSession().createCriteria(User.class).setFetchMode("userType", FetchMode.JOIN)
		   .add(Restrictions.eq("id", id)).uniqueResult();
	}

	@Override
	public void insert(User user) throws Exception {
		user.setCreated(new Date());
		getSession().save(user);
	}

	@Override
	public void delete(User user) throws Exception {
		getSession().delete(user);
	}

	@Override
	public void update(User user) throws Exception {
		System.out.println(user.getName());
		System.out.println(user.getLoginNameId());

		System.out.println(user.getId());

		getSession().update(user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findByPage(int page, int numPerPage) throws Exception {
		int offset = numPerPage * (page - 1);
		return getSession().createCriteria(User.class).setFetchMode("userType", FetchMode.JOIN)
		   .addOrder(Order.asc("kana")).setFirstResult(offset).setMaxResults(numPerPage).list();

	}

	@Override
	public int totalPages(int numPerPage) throws Exception {
		Long count = (Long) getSession().createCriteria(User.class)
		   .setProjection(Projections.rowCount())
		   .uniqueResult();
		return (int) Math.ceil((double) count / numPerPage);
	}

	@Override
	public User findByLoginIdAndLoginPass(String loginNameId, String loginPass) throws Exception {
		User user = (User) getSession().createCriteria(User.class)
		   .setFetchMode("userType", FetchMode.JOIN)
		   .setFetchMode("login", FetchMode.JOIN)
		   .add(Restrictions.eq("loginNameId", loginNameId))
		   .uniqueResult();

		if (user == null) { // ログイン名に該当するデータがない
			return null;
		}

		//ログイン処理を暗号化する
		if (BCrypt.checkpw(loginPass, user.getLogin().getPass())) {
			return user;

		} else {
			return null;
		}

	}

	@Override
	public boolean thisWordIsStudying(Integer loginUserId, Integer wordId) throws Exception {

		Study isNull = (Study) getSession().createCriteria(Study.class)
		   .setFetchMode("user", FetchMode.JOIN)
		   .setFetchMode("word", FetchMode.JOIN).setFetchMode("interval", FetchMode.JOIN)
		   .add(Restrictions.eqOrIsNull("word.id", wordId))
		   .add(Restrictions.eqOrIsNull("user.id", loginUserId))
		   .uniqueResult();

		if (isNull == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public int userProgressTotalPages(Integer userId, int numPerPage) throws Exception {
		Long count = (Long) getSession().createCriteria(Study.class)
		   .setFetchMode("user", FetchMode.JOIN)
		   .setFetchMode("word", FetchMode.JOIN).setFetchMode("interval", FetchMode.JOIN)
		   .add(Restrictions.eqOrIsNull("user.id", userId)).setProjection(Projections.rowCount())
		   .uniqueResult();

		return (int) Math.ceil((double) count / numPerPage);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Study> userProgressFindByPage(Integer userId, int page, int numPerPage)
	   throws Exception {
		int offset = numPerPage * (page - 1);

		return getSession().createCriteria(Study.class).setFetchMode("user", FetchMode.JOIN)
		   .setFetchMode("word", FetchMode.JOIN).setFetchMode("interval", FetchMode.JOIN)
		   .add(Restrictions.eqOrIsNull("user.id", userId)).setFirstResult(offset)
		   .setMaxResults(numPerPage).list();
	}

	@Override
	public void insert(Login login) throws Exception {

		getSession().save(login);

	}

	@Override
	public void update(Login login) throws Exception {
		getSession().update(login);

	}

	@Override
	public void delete(Login login) throws Exception {
		getSession().delete(login);

	}

	@Override
	public User findByIdWithLogin(Integer id) throws Exception {

		return (User) getSession().createCriteria(User.class)
		   .setFetchMode("userType", FetchMode.JOIN)
		   .setFetchMode("login", FetchMode.JOIN)
		   .add(Restrictions.eq("id", id))
		   .uniqueResult();
	}

}
