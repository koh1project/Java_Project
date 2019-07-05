package com.example.javacluborm.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.javacluborm.dao.StudyDao;
import com.example.javacluborm.dao.TestDao;
import com.example.javacluborm.dao.TypesDao;
import com.example.javacluborm.dao.UserDao;
import com.example.javacluborm.dao.WordDao;
import com.example.javacluborm.domain.Interval;
import com.example.javacluborm.domain.Study;
import com.example.javacluborm.domain.Subject;

@Controller
public class TestController {

	private String	boilerPlate	= " Select study From Study as study  left  join fetch study.user as user  left join fetch study.word as word ";
	private String	orderAsc		= "order by study.priority asc";

	@Autowired
	private UserDao	userDao;
	@Autowired
	private WordDao	wordDao;
	@Autowired
	private TestDao	testDao;
	@Autowired
	private TypesDao	typesDao;
	@Autowired
	private StudyDao	studyDao;

	//サンプルデータ量産
	//	@RequestMapping(value = "/sample", method = RequestMethod.GET)
	//	public String addSample(HttpSession session) throws Exception {
	//		System.out.println("きた");
	//		Random ran = new Random();
	//		Integer con, sub, date, user;
	//		Integer index = 1;
	//		Calendar calendar = Calendar.getInstance();
	//		List<Subject> subjects = typesDao.findAllSubjects();
	//		User loginUser = userDao.findById((Integer) session.getAttribute("loginUserId"));
	//		while (index < 10000) {
	//			Word word = new Word();
	//			Study study = new Study();
	//			word.setName(index.toString());
	//			word.setDetail(index.toString());
	//			word.setUser(loginUser);
	//			System.out.println("-----------------------------------------------------------");
	//			user = ran.nextInt(6);
	//			while (user <= 0) {
	//				user = ran.nextInt(6);
	//			}
	//			User setUser = userDao.findById(user);
	//			System.out.println(setUser.getId());
	//			System.out.println(setUser.getName());
	//			study.setUser(setUser);
	//			System.out.println("-----------------------------------------------------------");
	//
	//			sub = ran.nextInt(3);
	//			word.setSubject(subjects.get(sub));
	//
	//			con = ran.nextInt(2);
	//			if (con > 0) {
	//				word.setCompulsory(con);
	//			}
	//			wordDao.insert(word);
	//
	//			study.setWord(word);
	//
	//			date = ran.nextInt(1000);
	//			if (date % 2 == 0) {
	//				calendar.add(Calendar.DAY_OF_MONTH, date);
	//			} else {
	//				date = date * -1;
	//				calendar.add(Calendar.DAY_OF_MONTH, date);
	//			}
	//			Date date2 = new Date();
	//			date2 = calendar.getTime();
	//
	//			study.setNextStudyDate(date2);
	//
	//			studyDao.insert(study);
	//			index++;
	//		}
	//		System.out.println("きた２");
	//		return "/listWordsToStudyToday";
	//	}

	@RequestMapping(value = "/addStudy/{id}")
	public String addStudy(@PathVariable(value = "id") Integer wordId, HttpSession session,
	   Model model)
	   throws Exception {
		Study study = new Study();
		study.setUser(userDao.findById((Integer) session.getAttribute("loginUserId")));
		study.setWord(wordDao.findById(wordId));
		study.setNextStudyDate(new Date());
		Study checkExsit = studyDao.findById(study.getUser().getId(), study.getWord().getId());
		if (checkExsit == null) {
			studyDao.insert(study);
		} else {
			model.addAttribute("exsit", true);
		}
		model.addAttribute("wordName", study.getWord().getName());
		String url = "redirect:/detailWord/" + study.getWord().getId();
		return url;
		//		return "test/addStudyDone";

	}

	@RequestMapping(value = "/quitStudy/{id}")
	public String quitStudy(@PathVariable(value = "id") Integer wordId, HttpSession session,
	   Model model)
	   throws Exception {
		Integer loginUserId = (Integer) session.getAttribute("loginUserId");
		Study study = studyDao.findById(loginUserId, wordId);
		model.addAttribute("wordName", study.getWord().getName());
		studyDao.delete(study);
		String url = "redirect:/detailWord/" + study.getWord().getId();
		return url;

	}

	@RequestMapping(value = "/resetStudy/{id}")
	public StringBuilder resetAchievement(@PathVariable("id") Integer studyId, Model model)
	   throws Exception {
		Study study = studyDao.findById(studyId);
		study.setAchievement((Integer) 0);
		study.setPriority(1);
		study.setNextStudyDate(new Date());
		studyDao.update(study);

		StringBuilder url = new StringBuilder();
		url.append("redirect:/detailWord/");
		url.append(study.getWord().getId());
		model.addAttribute("study", study);
		return url;
	}

	@RequestMapping(value = "/studySelect", method = RequestMethod.GET)
	public String getStudySelect(Model model) throws Exception {
		List<Subject> subjects = typesDao.findAllSubjects();
		model.addAttribute("subjects", subjects);
		return "test/studySelect";
	}

	@RequestMapping(value = "/study", method = RequestMethod.GET)
	public String startStudy(@RequestParam(name = "compulsory") Boolean compulsory,
	   @RequestParam(name = "today") Boolean today,
	   @RequestParam(name = "subject", required = false) Integer[] subjectId, Model model,
	   HttpSession session)

	   throws Exception {
		Integer loginUserId = (Integer) session.getAttribute("loginUserId");
		StringBuilder whereSection = new StringBuilder();

		whereSection.append("where user.id =" + loginUserId + "and");
		boolean whereNothing = false;

		if (compulsory && today) {
			whereSection.append(" word.compulsory = 1 and   next_Study_Date < current_time()");

		} else if (compulsory && !today) {
			whereSection.append(" word.compulsory = 1 ");
		} else if (!compulsory && today) {
			whereSection.append(" next_Study_Date <  current_time() ");
		} else {
			whereNothing = true;

		}

		try {
			if ((compulsory || today) && subjectId.length > 0)
				whereSection.append("and ");
			whereNothing = false;
		} catch (NullPointerException e) {
			//			全教科を選択＝条件なしで"
		}
		try {
			if (subjectId.length >= 2) {
				whereSection.append("(");
				for (int i = 0; i < subjectId.length - 1; i++) {
					whereSection.append("word.subject.id =" + subjectId[i] + " or ");
				}
				whereSection.append("word.subject.id =" + (subjectId[subjectId.length - 1] + ")"));
			} else if (subjectId.length == 1) {
				whereSection.append("  word.subject.id = " + subjectId[0]);
			}

		} catch (NullPointerException e) {
		}

		if (whereNothing) {
			whereSection.delete(whereSection.lastIndexOf("and"), whereSection.lastIndexOf("and") + 3);
		}
		// HQL作成
		String query = boilerPlate + whereSection + orderAsc;

		System.out.println(query);

		/**
		 *
		 * ここからリストへ格納作業
		 *
		 */
		List<Study> objectQuestions = testDao.makeQuestion(query); // 一時的な格納
		if (objectQuestions.size() <= 0) {
			return "/test/nothing";
		}

		List<List<Study>> questionContainer = new ArrayList<>(); // 最終的な格納物
		int index = 0;
		int random = 0;
		Random ran = new Random();

		while (index < objectQuestions.size()) {
			List<Study> oneQuestion = new ArrayList<>(); // １つずつ再配置

			oneQuestion.add(objectQuestions.get(index)); // oneQuetionの先頭に追加
			System.out.println("最初に取得したoneQuestionリスト要素" + index + "番目");
			System.out
			   .println("最初に取得したoneQuestionリスト要素" + objectQuestions.get(index).getWord().getName());
			// 追加されたものの教科とIDを格納
			Integer thisSubjectId = objectQuestions.get(index).getWord().getSubject().getId();
			Integer thisStudyId = objectQuestions.get(index).getId();
			Integer countStudiesBySubject = studyDao.countStudiesBySubject(thisSubjectId, loginUserId);
			System.out.println("	countStudiesBySubject" + countStudiesBySubject);
			int countUserStudy = studyDao.countStudiesByLoginUser(loginUserId);
			int condition = 3;
			if (countStudiesBySubject >= 4) { // 教科だけで問題が作れるなら
				condition = 1;
			} else if (countUserStudy >= 4) { // ユーザーが学習中の単語が４つ以上あれば
				condition = 2;
			}
			System.out.println("該当単語" + objectQuestions.get(index).getWord().getName());
			System.out.println("condition" + condition);

			List<Study> temp = new ArrayList<>(); // 該当データを一時的にすべて格納
			switch (condition) {
				case 1:
					System.out.println("ランダム生成");
					temp = studyDao.findBySubject(thisSubjectId, loginUserId, thisStudyId);
					while (oneQuestion.size() < 4) { // 残りの3つランダムに入れる

						if (temp.size() > 1) {
							random = ran.nextInt(temp.size() - 1);
							oneQuestion.add(temp.get(random));
						} else {
							oneQuestion.add(temp.get(0));
						}

						System.out.println("ランダム" + temp.get(random).getWord().getName());
						temp.remove(random);
					}
					break;

				case 2:
					System.out.println("ランダム生成");
					temp = studyDao.findStudyiesRandom(loginUserId, thisStudyId);
					while (oneQuestion.size() < 4) { // 残りの3つランダムに入れる

						System.out.println("ランダム" + oneQuestion.size());
						if (temp.size() > 1) {
							random = ran.nextInt(temp.size() - 1);
							oneQuestion.add(temp.get(random));
						} else {
							oneQuestion.add(temp.get(0));
						}
						System.out.println("ランダム" + temp.get(random).getWord().getName());
						temp.remove(random);

					}
					break;

				case 3:
					System.out.println("ランダム生成");
					temp = studyDao.findCompulsoryStudies(thisStudyId);
					random = ran.nextInt(studyDao.totalStudy() - 1);
					while (oneQuestion.size() < 4) { // 残りの3つランダムに入れる
						System.out.println("ランダム" + oneQuestion.size());
						if (temp.size() > 1) {
							random = ran.nextInt(temp.size() - 1);
							oneQuestion.add(temp.get(random));
						} else {
							oneQuestion.add(temp.get(0));
						}
						System.out.println("ランダム" + temp.get(random).getWord().getName());
						temp.remove(random);
					}
				default:
					break;
			}

			index++;
			questionContainer.add(oneQuestion);
			// System.out.println(oneQuestion.get(0).getWord().getName());
			// System.out.println(oneQuestion.get(1).getWord().getName());
			// System.out.println(oneQuestion.get(2).getWord().getName());
			// System.out.println(oneQuestion.get(3).getWord().getName());
			// System.out.println(questionContainer.size());
			// temp.clear();
		}
		System.out.println("コンテナに渡すところ");

		int i = 0;
		while (i < questionContainer.size()) {
			int j = 0;
			while (j < questionContainer.get(i).size()) {

				System.out.println("コンテナ" + i + "番名" + j + "個目："
				   + questionContainer.get(i).get(j).getWord().getName());
				j++;
			}
			System.out.println("----------------------------------------------------------");
			i++;
		}

		List<Study> firstQuestion = questionContainer.get(0); // 先頭の4択を渡す

		// System.out.println(firstQuestion.get(0).getWord().getDetail());
		// System.out.println(firstQuestion.get(1).getWord().getDetail());
		// System.out.println(firstQuestion.get(2).getWord().getDetail());
		// System.out.println(firstQuestion.get(3).getWord().getDetail());

		model.addAttribute("correctWordName", firstQuestion.get(0).getWord().getName());
		model.addAttribute("correctId", firstQuestion.get(0).getId());
		session.setAttribute("correctId", firstQuestion.get(0).getId());

		Collections.shuffle(firstQuestion); // 問題をランダム配置
		model.addAttribute("question", firstQuestion);

		session.setAttribute("questionContainer", questionContainer);
		System.out.println("全問題コンテナのサイズ" + questionContainer.size());

		session.setAttribute("countTotal", questionContainer.size());
		session.setAttribute("countNow", 1);

		return "test/study";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/answerSubmit")
	public String acceptAnswer(@RequestParam(name = "selectId", required = false) Integer selectId,
	   Model model, HttpSession session)
	   throws Exception {
		Study study = new Study();

		Integer correctStudyId = (Integer) session.getAttribute("correctId");

		study = studyDao.findById(correctStudyId);
		study.setLastStudyDate(new Date());
		Interval interval = study.getNextInterval();
		Integer achievement = study.getAchievement();
		Integer addDate = 1;
		Integer priority = study.getPriority();
		Date nextStudyDate = new Date();
		Date today = new Date();
		Calendar dateConverter = Calendar.getInstance();
		dateConverter.setTime(nextStudyDate);
		if (selectId == null) {
			selectId = -1;
		}

		boolean isCorrect = false;
		if (selectId.equals(correctStudyId)) {
			isCorrect = true;
			model.addAttribute("isCorrect", isCorrect);

			// 今後、数値を調整する可能性があるため、１～４もそれぞれ記述する

			switch (interval.getId()) {
				case 1:
					addDate = interval.getInterval();
					achievement = achievement + 20;
					interval.setId(interval.getId() + 1);
					priority = priority + 1;
					break;

				case 2:
					addDate = interval.getInterval();
					achievement = achievement + 20;
					interval.setId(interval.getId() + 1);
					priority = priority + 1;

					break;
				case 3:
					addDate = interval.getInterval();
					achievement = achievement + 20;
					interval.setId(interval.getId() + 1);
					priority = priority + 1;

				case 4:
					addDate = interval.getInterval();
					achievement = achievement + 20;
					interval.setId(interval.getId() + 1);
					priority = priority + 1;

				case 5:
					addDate = null;
					achievement = achievement + 20;

					break;
			}

			// 不正解時の処理
		} else {
			model.addAttribute("isCorrect", "");
			System.out.println("不正解" + isCorrect);
			switch (interval.getId()) {
				//addDateは初期値１のまま更新しない。すべてのケースで１日後に設定される。
				case 1:
					achievement = achievement + 5;
					if (priority > 1) {
						priority = priority - 1;
					}

					break;

				case 2:
					interval.setId(interval.getId() + 1);
					if (priority > 1) {
						priority = priority - 1;
					}
					break;
				case 3:
					achievement = achievement - 5;
					if (priority > 1) {
						priority = priority - 1;
					}
					interval.setId(interval.getId() - 1);
					break;
				case 4:
					achievement = achievement - 10;
					if (priority > 1) {
						priority = priority - 1;
					}
					interval.setId(interval.getId() - 1);
					break;
				case 5:
					achievement = achievement - 15;
					if (priority > 1) {
						priority = priority - 1;
					}

					interval.setId(interval.getId() - 1);
					if (achievement <= 70) {
						achievement = 70;
						break;
					}
			}
		}
		if (achievement >= 100) {
			achievement = 100;
			addDate = null;
		}
		study.setAchievement(achievement);
		study.setNextInterval(interval);
		study.setPriority(priority);
		study.setLastStudyDate(today);
		if (addDate != null) {
			dateConverter.add(Calendar.DATE, addDate);
			nextStudyDate = dateConverter.getTime();
			study.setNextStudyDate(nextStudyDate);
		} else {
			study.setNextStudyDate(null);
		}

		studyDao.update(study);

		List<List<Study>> questionContainer = new ArrayList<>();
		questionContainer = (List<List<Study>>) session.getAttribute("questionContainer");
		model.addAttribute("wordName", questionContainer.get(0).get(0).getWord().getName());
		model.addAttribute("wordDetail", questionContainer.get(0).get(0).getWord().getDetail());
		questionContainer.remove(0);

		return "test/answerSubmit";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/nextStudy")
	public String nextStudy(Model model, HttpSession session) {
		List<List<Study>> questionContainer = new ArrayList<>(); // 最終的な格納物
		questionContainer = (List<List<Study>>) session.getAttribute("questionContainer");

		if (questionContainer.isEmpty()) {
			return "test/nothing";
		}

		List<Study> firstQuestion = questionContainer.get(0); // 先頭の4択を渡す
		model.addAttribute("correctWordName", firstQuestion.get(0).getWord().getName());
		model.addAttribute("correctId", firstQuestion.get(0).getId());
		session.setAttribute("correctId", firstQuestion.get(0).getId());
		Collections.shuffle(firstQuestion); // 問題をランダム配置
		model.addAttribute("question", firstQuestion);
		session.setAttribute("questionContainer", questionContainer);
		session.setAttribute("countNow", (Integer) session.getAttribute("countNow") + 1);

		return "test/study";
	}

	// ここからテスト

	@RequestMapping(value = "/testSelect", method = RequestMethod.GET)
	public String getTestSelect(Model model) throws Exception {

		List<Subject> subjects = typesDao.findAllSubjects();
		model.addAttribute("subjects", subjects);

		return "test/testSelect";
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String startTest(@RequestParam(name = "compulsory") Boolean compulsory,
	   @RequestParam(name = "maxNumber") Integer maxNumber,
	   @RequestParam(name = "subject", required = false) Integer[] subjectId, Model model,
	   HttpSession session)

	   throws Exception {
		Integer loginUserId = (Integer) session.getAttribute("loginUserId");
		StringBuilder whereSection = new StringBuilder();

		whereSection.append("where user.id =" + loginUserId + "and");
		boolean whereNothing = false;

		if (compulsory) {
			whereSection.append(" word.compulsory = 1 ");

		} else {
			whereNothing = true;
		}

		try {
			if ((compulsory) && subjectId.length > 0)
				whereSection.append("and ");
		} catch (NullPointerException e) {
			//			全教科を選択＝条件なしで
		}
		try {
			if (subjectId.length >= 2) {
				whereNothing = false;
				whereSection.append("(");
				for (int i = 0; i < subjectId.length - 1; i++) {
					whereSection.append("word.subject.id =" + subjectId[i] + " or ");
				}
				whereSection.append("word.subject.id =" + (subjectId[subjectId.length - 1] + ")"));
			} else if (subjectId.length == 1) {
				whereNothing = false;
				whereSection.append("  word.subject.id = " + subjectId[0] + ")");
			}
			if (whereNothing) {
				whereSection.delete(whereSection.lastIndexOf("and"),
				   whereSection.lastIndexOf("and") + 3);
			}
		} catch (NullPointerException e) {
		}
		// HQL作成
		String query = boilerPlate + whereSection;

		System.out.println(query);

		/**
		 *
		 * ここからリストへ格納作業
		 *
		 */
		List<Study> objectQuestions = testDao.makeQuestion(query); // 一時的な格納
		System.out.println("問題数" + objectQuestions.size());

		List<List<Study>> questionContainer = new ArrayList<>(); // 最終的な格納物
		int index = 0;
		int random = 0;
		Random ran = new Random();

		if (objectQuestions.isEmpty()) {
			return "/test/nothing";
		} else {

			Collections.shuffle(objectQuestions); // 該当の順序もランダムにする
			while (objectQuestions.size() > maxNumber) { //問題数を最大数になるまで減らす
				objectQuestions.remove(ran.nextInt(objectQuestions.size() - 1));
			}
		}

		int i2 = 0;
		while (i2 < objectQuestions.size()) {

			System.out.println("ObjectionQuestions" + i2 + "個目："
			   + objectQuestions.get(i2).getWord().getName());
			System.out.println("--------------------------------------");
			i2++;

		}

		while (index < objectQuestions.size()) {
			List<Study> oneQuestion = new ArrayList<>(); // １つずつ再配置
			System.out.println("挿入作業ーーーーーーーーーーーーーーーーーーーーー");
			System.out.println("objectQuestions:" + objectQuestions.get(index).getWord().getName());
			oneQuestion.clear();
			oneQuestion.add(objectQuestions.get(index)); // oneQuetionの先頭に追加
			System.out.println("oneQuestion:" + oneQuestion.get(0).getWord().getName());

			// 追加されたものの教科とIDを格納
			Integer thisSubjectId = oneQuestion.get(0).getWord().getSubject().getId();
			Integer thisStudyId = oneQuestion.get(0).getId();
			Integer countStudiesBySubject = studyDao.countStudiesBySubject(thisSubjectId, loginUserId);
			int countUserStudy = studyDao.countStudiesByLoginUser(loginUserId);
			int condition = 3;
			if (countStudiesBySubject >= 4) { // 教科だけで問題が作れるなら
				condition = 1;
			} else if (countUserStudy >= 4) { // ユーザーが学習中の単語が４つ以上あれば
				condition = 2;
			}

			System.out.println("該当単語" + oneQuestion.get(0).getWord().getName());
			System.out.println("condition" + condition);

			List<Study> temp = new ArrayList<>(); // 該当データを一時的にすべて格納
			switch (condition) {
				case 1:
					System.out.println("ランダム生成");
					temp = studyDao.findBySubject(thisSubjectId, loginUserId, thisStudyId);
					while (oneQuestion.size() < 4) { // 残りの3つランダムに入れる
						if (temp.size() > 1) {
							random = ran.nextInt(temp.size() - 1);
							oneQuestion.add(temp.get(random));
						} else {
							oneQuestion.add(temp.get(0));
						}
						System.out.println("ランダム" + temp.get(random).getWord().getName());

						temp.remove(random);
					}
					break;

				case 2:
					System.out.println("ランダム生成");
					temp = studyDao.findStudyiesRandom(loginUserId, thisStudyId);
					while (oneQuestion.size() < 4) { // 残りの3つランダムに入れる
						System.out.println("ランダム" + oneQuestion.size());

						if (temp.size() > 1) {
							random = ran.nextInt(temp.size() - 1);
							oneQuestion.add(temp.get(random));
						} else {
							oneQuestion.add(temp.get(0));
						}
						System.out.println("ランダム" + temp.get(random).getWord().getName());

						temp.remove(random);
					}
					break;

				case 3:
					System.out.println("ランダム生成");
					temp = studyDao.findCompulsoryStudies(thisStudyId);
					random = ran.nextInt(studyDao.totalStudy() - 1);
					System.out.println("ランダム" + oneQuestion.size());

					while (oneQuestion.size() < 4) { // 残りの3つランダムに入れる
						if (temp.size() > 1) {
							random = ran.nextInt(temp.size() - 1);
							oneQuestion.add(temp.get(random));
						} else {
							System.out.println("ランダム" + temp.get(random).getWord().getName());
							oneQuestion.add(temp.get(0));
						}
						temp.remove(random);
					}
				default:
					break;
			}
			System.out.println("Onequestionsの完成後：" + oneQuestion.get(0).getWord().getName());
			System.out.println("Onequestionsの完成後：" + oneQuestion.get(1).getWord().getName());
			System.out.println("Onequestionsの完成後：" + oneQuestion.get(2).getWord().getName());
			System.out.println("Onequestionsの完成後：" + oneQuestion.get(3).getWord().getName());

			System.out.println(index);

			questionContainer.add(oneQuestion);

			int i = 1;
			while (i < questionContainer.size()) {
				int j = 0;
				while (j < questionContainer.get(i).size()) {

					System.out.println("コンテナ" + i + "番名" + j + "個目："
					   + questionContainer.get(i).get(j).getWord().getName());
					j++;
				}
				System.out.println("--------------------------------------");
				i++;

			}

			index++;
			temp.clear();
		}

		int i = 1;
		while (i < questionContainer.size()) {
			int j = 0;
			while (j < questionContainer.get(i).size()) {
				System.out.println("2コンテナ" + i + "番名" + j + "個目："
				   + questionContainer.get(i).get(j).getWord().getName());
				j++;
			}
			System.out.println("--------------------------------------");
			i++;
		}

		System.out.println("/test コンテナに渡すところ");
		List<Study> firstQuestion = questionContainer.get(0); // 先頭の4択を渡す
		System.out.println(firstQuestion.get(0).getWord().getDetail());
		System.out.println(firstQuestion.get(1).getWord().getDetail());
		System.out.println(firstQuestion.get(2).getWord().getDetail());
		System.out.println(firstQuestion.get(3).getWord().getDetail());

		List<Study> correctStudies = new ArrayList<>();
		List<Integer> correctAnswerId = new ArrayList<>();
		Study correctStudy = firstQuestion.get(0);
		Integer correctId = firstQuestion.get(0).getId();

		model.addAttribute("correctWordName", firstQuestion.get(0).getWord().getName()); //表示用
		model.addAttribute("correctId", correctId); //スコア用パラメータ

		correctAnswerId.add((Integer) correctId);
		session.setAttribute("correctStudies", correctStudies);
		session.setAttribute("correctAnswerId", correctAnswerId);

		// 正誤表示用
		correctStudies.add(correctStudy);
		Collections.shuffle(firstQuestion); // 問題をランダム配置
		model.addAttribute("question", firstQuestion);
		// 何問目かを表示
		session.setAttribute("countTotal", questionContainer.size());
		session.setAttribute("countNow", 1);

		questionContainer.remove(0);
		session.setAttribute("questionContainer", questionContainer);

		List<Integer> userAnswerId = new ArrayList<>();
		session.setAttribute("userAnswerId", userAnswerId);

		return "test/test";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/nextTest")
	public String nextTest(
	   @RequestParam(name = "selectId", required = false) Integer selectId,

	   Model model, HttpSession session) throws Exception {
		Integer correctId;
		if (selectId == null) {
			selectId = -1;
		}
		List<Integer> userAnswerId = new ArrayList<>();

		if (session.getAttribute("userAnswerId") == null) {
			userAnswerId.add(selectId);
		} else {
			userAnswerId = (List<Integer>) session.getAttribute("userAnswerId");
			userAnswerId.add(selectId);
		}

		session.setAttribute("userAnswerId", userAnswerId);

		//次の問題作成

		List<List<Study>> questionContainer = new ArrayList<>();
		questionContainer = (List<List<Study>>) session.getAttribute("questionContainer");

		if (questionContainer.isEmpty()) {
			model.addAttribute("test", "test");
			return "test/nothing";

		} else {
			model.addAttribute("wordName", questionContainer.get(0).get(0).getWord().getName());
			model.addAttribute("wordDetail", questionContainer.get(0).get(0).getWord().getDetail());

			List<Study> firstQuestion = questionContainer.get(0); // 先頭の4択を渡す
			List<Study> correctStudies = new ArrayList<>();
			List<Integer> correctAnswerId = new ArrayList<>();

			Study correctStudy = firstQuestion.get(0);
			correctId = firstQuestion.get(0).getId();

			correctStudies = (List<Study>) session.getAttribute("correctStudies");
			correctAnswerId = (List<Integer>) session.getAttribute("correctAnswerId");

			correctStudies.add(correctStudy);

			correctAnswerId.add(correctId);
			System.out.println("正解IDサイズ" + correctAnswerId.size());
			model.addAttribute("correctWordName", firstQuestion.get(0).getWord().getName());
			model.addAttribute("correctId", correctId);
			session.setAttribute("correctStudies", correctStudies);
			session.setAttribute("correctAnswerId", correctAnswerId);

			Collections.shuffle(firstQuestion); // 問題をランダム配置
			model.addAttribute("question", firstQuestion);
			questionContainer.remove(0);
			if (questionContainer.isEmpty()) {
				session.removeAttribute("questionContainer");
			}
			session.setAttribute("questionContainer", questionContainer);

		}

		session.setAttribute("countNow", (Integer) session.getAttribute("countNow") + 1);
		return "test/test";

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/result", method = RequestMethod.GET)
	public String testResult(
	   Model model, HttpSession session) throws Exception {
		System.out.println("/result");

		List<Study> correctStudies = new ArrayList<>();
		List<Integer> correctAnswerId = new ArrayList<>();
		List<String> correctAnswerName = new ArrayList<>();
		List<Integer> userAnswerId = new ArrayList<>();
		List<String> tfSheet = new ArrayList<>();
		Integer score = 0;

		correctAnswerId = (List<Integer>) session.getAttribute("correctAnswerId");
		correctAnswerName = (List<String>) session.getAttribute("correctAnswerName");
		userAnswerId = (List<Integer>) session.getAttribute("userAnswerId");

		correctStudies = (List<Study>) session.getAttribute("correctStudies");

		int index = 0;
		while (index < userAnswerId.size()) {

			if (correctAnswerId.get(index).equals(userAnswerId.get(index))) {

				tfSheet.add("〇");
				score++;

			} else {
				tfSheet.add("×");
			}
			index++;
		}

		model.addAttribute("correctAnswerId", correctAnswerId);
		model.addAttribute("correctAnswerName", correctAnswerName);
		model.addAttribute("userAnswerId", userAnswerId);
		model.addAttribute("score", score);
		model.addAttribute("tfSheet", tfSheet);
		model.addAttribute("correctStudies", correctStudies);
		model.addAttribute("countNow", session.getAttribute("countNow"));

		return "test/result";

	}

}
