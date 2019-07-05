package com.example.javacluborm.dao;

import java.util.List;

import com.example.javacluborm.domain.Word;


public interface WordDao {
	public List<Word>query(String query) throws Exception;
//	public List<Study> findALL() throws Exception;
//	public List<Study> findTodayListByPage(int page, int numPerPage)throws Exception;
//	public Study findById(Integer loginUserId , Integer wordId) throws Exception;
	public Word findById(Integer wordId) throws Exception;
	public void insert(Word word) throws Exception;
	public void delete(Word word) throws Exception;
	public void update(Word word) throws Exception;
	public List<Word> findWordsByPage(int page, int numPerPage) throws Exception;
//	public List<Study> findStudiesByPage(int page, int numPerPage) throws Exception;
	public int totalWordsPages(int numPerPage) throws Exception;
//	public int totalStudiesPages(int numPerPage) throws Exception;
	public List<Word> findAllWord() throws Exception;
	public List<Word> searchWordsByPage(String keyWord,int page, int numPerPage) throws Exception;
	public int totalsearchWordsPages(String keyWord,  int numPerPage) throws Exception;


}
