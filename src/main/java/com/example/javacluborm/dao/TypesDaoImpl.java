package com.example.javacluborm.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.example.javacluborm.domain.Subject;
import com.example.javacluborm.domain.UserType;

@Transactional
@Repository
public class TypesDaoImpl extends BaseDao implements TypesDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Subject> findAllSubjects() throws Exception {
		return getSession().createCriteria(Subject.class).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserType> findAllUserTypes() throws Exception {
		return getSession().createCriteria(UserType.class).list();
	}

	@Override
	public Subject findBySubjectId(Integer subjectId) throws Exception {
		return (Subject) getSession().createCriteria(Subject.class)
		   .add(Restrictions.eqOrIsNull("id", subjectId)).uniqueResult();
	}

	@Override
	public void insertSubject(Subject newSubject) throws Exception {
		getSession().save(newSubject);
	}

	@Override
	public void changeSubjectName(Subject newSubject) throws Exception {
		getSession().update(newSubject);

	}

	@Override
	public void insertUserTypes(UserType newUserType) throws Exception {
		getSession().save(newUserType);

	}

	@Override
	public void changeUserTypesName(UserType newUserType) throws Exception {
		getSession().update(newUserType);

	}

	//	@Override
	//	public Integer getUserTypesId(Integer loginUserId) throws Exception {
	//		User user = (User) getSession().createCriteria(User.class).add(Restrictions.eq("id", loginUserId)).uniqueResult();
	//		Integer userTypeId = user.getUserType().getId();
	//		return userTypeId;
	//	}

}
