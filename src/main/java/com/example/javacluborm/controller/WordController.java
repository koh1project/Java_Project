package com.example.javacluborm.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.javacluborm.dao.StudyDao;
import com.example.javacluborm.dao.TypesDao;
import com.example.javacluborm.dao.UserDao;
import com.example.javacluborm.dao.WordDao;
import com.example.javacluborm.domain.Study;
import com.example.javacluborm.domain.Subject;
import com.example.javacluborm.domain.User;
import com.example.javacluborm.domain.Word;

@Controller
public class WordController {

	private static final int NUM_PER_PAGE = 10;

	@Autowired
	private WordDao	wordDao;
	@Autowired
	private UserDao	userDao;
	@Autowired
	private TypesDao	typesDao;
	@Autowired
	private StudyDao	studyDao;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}

	@RequestMapping(value = { "/", "/listWordsToStudyToday" }, method = RequestMethod.GET)

	public String list(
	   @RequestParam(name = "p", defaultValue = "1") Integer page,
//	   @RequestParam(name = "keyWord", defaultValue = "") String keyWord,
	   Model model, HttpSession session)
	   throws Exception {
		Integer loginUserId = (Integer) session.getAttribute("loginUserId");
		List<Study> studiesList = new ArrayList<>();
		int totalPages;

		studiesList = studyDao.findTodayListByPage(loginUserId, page, NUM_PER_PAGE);
		totalPages = studyDao.totalTodayStudies(loginUserId, NUM_PER_PAGE);
		System.out.println(studiesList.size());
		System.err.println(totalPages);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", page);
		model.addAttribute("studiesList", studiesList);

		return "word/listWordsToStudyToday";

	}

	@RequestMapping(value = "/detailWord/{id}")
	public String detailWord(@PathVariable("id") Integer wordId, Model model, HttpSession session)
	   throws Exception {
		Integer loginUserId = (Integer) session.getAttribute("loginUserId");

		// ユーザが学習中かどうかの判断メソッド
		boolean checkStudying = userDao.thisWordIsStudying(loginUserId, wordId);

		if (checkStudying) {
			Study study = studyDao.findById(loginUserId, wordId);
			model.addAttribute("study", study);
			model.addAttribute("word", study.getWord());
			model.addAttribute("loginUserId", session.getAttribute("loginUserId"));
System.out.println(study.getWord().getUser().getName());
			return "word/detailWord";
		} else {
			// ユーザーが未学習の単語なら

			Word word = wordDao.findById(wordId);
			model.addAttribute("word", word);
			model.addAttribute("study", "");
			model.addAttribute("loginUserId", session.getAttribute("loginUserId"));
System.out.println(word.getUser().getName());
			return "word/detailWord";
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/listWords", method = RequestMethod.GET)
	public String listWords(
	   @RequestParam(name = "p", defaultValue = "1") Integer page,
	   @RequestParam(name = "keyWord", defaultValue = "") String keyWord,
	   @RequestParam(name = "compulsory", required = false) Boolean compulsory,
	   @RequestParam(name = "subject", required = false) Integer[] subjectId,

	   Model model,
	   HttpSession session) throws Exception {
		List<Word> wordsList = new ArrayList<>();
		int totalPages = 0;
		boolean whereNothing = false;
		StringBuilder appendixtURL = new StringBuilder();
		boolean over2pages = false;

		if (page  >= 2) {
			over2pages = true;
		}

		//		初回
		if (keyWord.isEmpty() && compulsory == null && subjectId == null) {
			System.out.println("初回");
			wordsList = wordDao.findWordsByPage(page, NUM_PER_PAGE);
			totalPages = wordDao.totalWordsPages(NUM_PER_PAGE);






		} else if (keyWord.length() > 0 && compulsory == null && subjectId == null) {
			//			キーワードのみの検索
			System.out.println("キーワードのみ");
			appendixtURL.append("&keyWord=" + keyWord);
			wordsList = wordDao.searchWordsByPage(keyWord, page, NUM_PER_PAGE);
			totalPages = wordDao.totalsearchWordsPages(keyWord, NUM_PER_PAGE);



//			HQL作成


		}else if(over2pages){

			List<Word> tempWord = (List<Word>) session.getAttribute("tempWord");
			session.setAttribute("tempWord",tempWord);

			totalPages = (tempWord.size() / NUM_PER_PAGE);
			System.out.println((page - 1));
			int offset = NUM_PER_PAGE * (page - 1);

			if (offset + NUM_PER_PAGE < tempWord.size()) {
				for (int i = 0; i < NUM_PER_PAGE; i++) {
					wordsList.add(tempWord.get(offset + i));
				}
			} else {
				for (int i = offset; i < tempWord.size(); i++) {
					wordsList.add(tempWord.get(i));
				}
			}

		}else {

			System.out.println("HQL作成");
			StringBuilder q = new StringBuilder();
			q.append(
			   "Select word From Word as word left join fetch word.user as user left join fetch word.subject as subject ");
			StringBuilder whereSection = new StringBuilder();
			whereSection.append("where ");

			if (compulsory) {
				whereNothing = false;
				whereSection.append(" word.compulsory = 1 and ");
				appendixtURL.append("&compulsory=true");
			} else {
				whereNothing = true;
				appendixtURL.append("&compulsory=false");
			}

			if ((keyWord.length() > 0)) {
				appendixtURL.append("&keyWord=" + keyWord);
				whereNothing = false;
				whereSection.append(
				   "(word.name like '%" + keyWord + "%'or word.detail like'% " + keyWord + "%' ) and");
				System.out.println(whereSection);
			}

			try {

				if ((compulsory || !keyWord.isEmpty()) && subjectId == null) {
					System.out.println("教科指定なし");
					whereSection.delete(whereSection.lastIndexOf("and"),
					   whereSection.lastIndexOf("and") + 3);

				} else if (subjectId.length >= 2) {
					whereNothing = false;
					System.out.println("複数教科");
					whereSection.append("(");
					for (int i = 0; i < subjectId.length - 1; i++) {
						whereSection.append("subject.id =" + subjectId[i] + " or ");
						appendixtURL.append("&subject=" + subjectId[i]);
					}
					whereSection.append("subject.id =" + (subjectId[subjectId.length - 1] + ")"));
				} else if (subjectId.length == 1) {
					whereNothing = false;
					System.out.println("教科１種類");
					whereSection.append("  subject.id = " + subjectId[0]);
					appendixtURL.append("&subject=" + subjectId[0]);
				}

			} catch (Exception e) {	}




//	クエリ終了
			if (whereNothing) {
				System.out.println("whereNothing認定");
				whereSection.delete(whereSection.lastIndexOf("where"),
				   whereSection.lastIndexOf("where") + 5);
				wordsList = wordDao.findWordsByPage(page, NUM_PER_PAGE);
				totalPages = wordDao.totalWordsPages(NUM_PER_PAGE);
				wordsList = wordDao.findAllWord();
				session.setAttribute("tempWord",wordsList);


//クエリ実行
			} else  {
				//				 fetch は setMaxResults() や setFirstResult() と一緒に使用すべきではありません。通常 eager なコレクションフェッチをすると重複が出てしまうため、あなたが期待するような行数にはならないのです。


				q.append(whereSection);
				System.out.println(q);
				String query = q.toString();
				List<Word> tempWord = wordDao.query(query);
				//				wordsList = wordDao.query(query);
				session.setAttribute("tempWord",tempWord);




				totalPages = (tempWord.size() / NUM_PER_PAGE);
				System.out.println((page - 1));
				int offset = NUM_PER_PAGE * (page - 1);

				if (offset + NUM_PER_PAGE < tempWord.size()) {
					for (int i = 0; i < NUM_PER_PAGE; i++) {
						wordsList.add(tempWord.get(offset + i));
					}
				} else {
					for (int i = offset; i < tempWord.size(); i++) {
						wordsList.add(tempWord.get(i));
					}

				}
			}
		}


		System.out.println("totalPages"+totalPages);
		System.out.println(wordsList.size());
		List<Subject> subjects = typesDao.findAllSubjects();
		List<Study> studiesList = studyDao.findALL();
		model.addAttribute("studiesList", studiesList);
		model.addAttribute("wordsList", wordsList);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", page);
		model.addAttribute("subjects", subjects);
		model.addAttribute("appendixtURL", appendixtURL);
		return "word/listWords";

	}

	@RequestMapping(value = "/addWord", method = RequestMethod.GET)
	public String getAddWord(Model model, HttpSession session) throws Exception {
		Word word = new Word();
		User loginUser = userDao.findById((Integer) session.getAttribute("loginUserId"));
		model.addAttribute("word", word);

		// ユーザータイプ
		model.addAttribute("loginUser", loginUser);

		List<Subject> subjects = typesDao.findAllSubjects();

		model.addAttribute("subjects", subjects);

			return "word/addWord";
	}

	@RequestMapping(value = "/addWord", method = RequestMethod.POST)
	public String postAddWord(
		@Valid Word word,
		Errors errors,
	   @RequestParam(name = "newSubject") String newSubject,
	    Model model, HttpSession session)
	   throws Exception {



System.out.println("ADDWORDerror"+ errors.hasErrors());

		if (!newSubject.isEmpty()) {
			Subject subject = new Subject();
			subject.setName(newSubject);
			typesDao.insertSubject(subject);
			word.setSubject(subject);
		}

		if (!errors.hasErrors()) {
			User user = new User();
			user.setId((Integer) session.getAttribute("loginUserId"));
			word.setUser(user);
			wordDao.insert(word);
			model.addAttribute("word", word);

			return "word/addWordDone";
		} else {

			errors.reject("error.addWord");
			return "word/addWord";
		}
	}

	@RequestMapping(value = "/editWord/{id}", method = RequestMethod.GET)
	public String editWordGet(@PathVariable("id") Integer wordId, Model model, HttpSession session)
	   throws Exception {
		List<Subject> subjects = typesDao.findAllSubjects();
		Word word = wordDao.findById(wordId);
		User loginUser = userDao.findById((Integer) session.getAttribute("loginUserId"));

		model.addAttribute("subjects", subjects);
		model.addAttribute("word", word);
		model.addAttribute("loginUser", loginUser);
		return "word/editWord";

	}


	@RequestMapping(value = "/editWord/{id}", method = RequestMethod.POST)
	public String editWordPOST(@RequestParam(name = "newSubject") String newSubject,
	   Word word, Errors errors, Model model)
	   throws Exception {

		if (!newSubject.isEmpty()) {
			Subject subject = new Subject();
			subject.setName(newSubject);
			typesDao.insertSubject(subject);
			word.setSubject(subject);
		}
		if (!errors.hasErrors()) {
			model.addAttribute("wordName" , word.getName());
			model.addAttribute("wordId" , word.getId());
			wordDao.update(word);

			return "word/editWordDone";
		} else {
			return "word/editWord";
		}
	}

	@RequestMapping(value = "/deleteWord/{id}")
	public String deleteWord(@PathVariable("id") Integer wordId, Model model) throws Exception {
		Word deleteWord = wordDao.findById(wordId);

		List<Study> studyingUserDelete = studyDao.findByWordId(wordId);
		for (Study study : studyingUserDelete) {
			studyDao.delete(study);
		}
		wordDao.delete(deleteWord);
		return "word/deleteWordDone";
	}



	@RequestMapping(value = "/listStudyingWords")
	public String listStudyingWords(@RequestParam(name = "p", defaultValue = "1") Integer page,
	   Model model, HttpSession session) throws Exception {
		List<Study> studiesList = studyDao
		   .findStudiesByPage((Integer) session.getAttribute("loginUserId"), page, NUM_PER_PAGE);
		int totalPages = studyDao.totalStudiesPages((Integer) session.getAttribute("loginUserId"),
		   NUM_PER_PAGE);

		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", page);
		model.addAttribute("studiesList", studiesList);

		return "word/listStudyingWords";

	}

}
