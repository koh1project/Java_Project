package com.example.javacluborm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
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
import com.example.javacluborm.domain.Login;
import com.example.javacluborm.domain.Study;
import com.example.javacluborm.domain.User;
import com.example.javacluborm.domain.UserType;

@Controller
public class UserController {
	private static final int NUM_PER_PAGE = 10;

	@Autowired
	private UserDao	userDao;
	@Autowired
	private TypesDao	typesDao;

	@Autowired
	private StudyDao studyDao;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		format.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}

	@RequestMapping(value = "listUsers", method = RequestMethod.GET)
	public String listUsers(@RequestParam(name = "p", defaultValue = "1") Integer page, Model model)
	   throws Exception {
		int totalPages = userDao.totalPages(NUM_PER_PAGE);
		List<User> usersList = userDao.findByPage(page, NUM_PER_PAGE);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", page);
		model.addAttribute("usersList", usersList);
		return "user/listUser";
	}

	@RequestMapping(value = "addUser", method = RequestMethod.GET)
	public String addUserGet(Model model) throws Exception {
		User user = new User();
		Login login = new Login();
		List<UserType> userTypes = typesDao.findAllUserTypes();

		model.addAttribute("user", user);
		model.addAttribute("login", login);
		model.addAttribute("userTypes", userTypes);
		return "user/addUser";
	}

	@RequestMapping(value = "addUser", method = RequestMethod.POST)
	public String addUserPost(User user, Login login, Model model, Errors errors) throws Exception {
		if (!errors.hasErrors()) {

			String hashed = BCrypt.hashpw(login.getPass(), BCrypt.gensalt());
			login.setPass(hashed);
			userDao.insert(login);
			user.setLogin(login);
			userDao.insert(user);

			return "user/addUserDone";
		} else {
			return "/user/addUser/";
		}
	}

	@RequestMapping(value = "detailUser/{id}")
	public String detailUser(@PathVariable("id") Integer userId, Model model) throws Exception {
		User user = userDao.findById(userId);
		model.addAttribute("user", user);
		return "user/detailUser";
	}

	@RequestMapping(value = "/editUser/{id}", method = RequestMethod.GET)
	public String editUserGet(@PathVariable("id") Integer userId, Model model) throws Exception {
		List<UserType> userTypes = typesDao.findAllUserTypes();
		User user = userDao.findById(userId);
		Login login = new Login();
		model.addAttribute("login", login);
		model.addAttribute("userTypes", userTypes);
		model.addAttribute("user", user);

		return "user/editUser";
	}

	@RequestMapping(value = "/editUser/{id}", method = RequestMethod.POST)
	public String editUserPOST(User user, Login login, Errors errors, Model model) throws Exception {
		if (!errors.hasErrors()) {

			User previousUser = userDao.findByIdWithLogin(user.getId());

			String hashed = BCrypt.hashpw(login.getPass(), BCrypt.gensalt());
			login.setPass(hashed);
			login.setId(previousUser.getLogin().getId());
//			userDao.insert(login);

//			userDao.delete(previousUser.getLogin()); //以前のログイン情報を削除
			user.setLogin(login);
			userDao.update(user);

			return "user/editUserDone";
		} else {
			return "user/editUser";
		}
	}

	@RequestMapping(value = "deleteUser/{id}")
	public String deleteUser(@PathVariable("id") Integer userId, Model model) throws Exception {
		User user = userDao.findByIdWithLogin(userId);
		userDao.delete(user);
		return "user/deleteUserDone";
	}

	@RequestMapping(value = "userProgress/{id}")
	public String userProgress(@PathVariable("id") Integer userId,
	   @RequestParam(name = "p", defaultValue = "1") Integer page, Model model) throws Exception {
		int totalPages = userDao.userProgressTotalPages(userId, NUM_PER_PAGE);
		List<Study> studyList = userDao.userProgressFindByPage(userId, page, NUM_PER_PAGE);
		int countTotalStudy = studyDao.countStudiesByLoginUser(userId);
		model.addAttribute("countTotalStudy" , countTotalStudy);
		model.addAttribute("user", userDao.findById(userId));
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", page);
		model.addAttribute("studyList", studyList);

		return "user/userProgress";

	}

	@RequestMapping(value = "/editOwn/{loginUserId}", method = RequestMethod.GET)
	public String editOwnGet(@PathVariable("loginUserId") Integer loginuserId, Model model)
	   throws Exception {

		List<UserType> userTypes = typesDao.findAllUserTypes();
		User user = userDao.findById(loginuserId);
		Login login = new Login();
		model.addAttribute("login", login);
		model.addAttribute("userTypes", userTypes);
		model.addAttribute("user", user);

		return "user/editOwn";
	}

	@RequestMapping(value = "/editOwn/{loginUserId}", method = RequestMethod.POST)
	public String editOwnPOST(

	   @RequestParam(name = "currentPass") String currentPass,
	   @RequestParam(name = "checkPass") String newPass,
	   User newUser, Login login , Errors errors, Model model, HttpSession session) throws Exception {
		Boolean currentIsError = true;
		boolean checkIsError = true;

		User currentUser = userDao.findByIdWithLogin((Integer) session.getAttribute("loginUserId"));
		if (BCrypt.checkpw(currentPass, currentUser.getLogin().getPass()) && !currentPass.isEmpty()) {
		currentIsError = false;
		}else {
			System.out.println("currentIsError");
			model.addAttribute("currentIsError", currentIsError);
		}

		//ユーザが入力したLoginとの比較のため、どちらも平文
		if (newPass.equals(login.getPass()) && !newPass.isEmpty()) {
			checkIsError = false;
		}else {
			System.out.println("checkIsError");
			model.addAttribute("checkIsError", checkIsError);
		}

//エラーがあれば
		if (errors.hasErrors() || currentIsError || checkIsError){
			List<UserType> userTypes = typesDao.findAllUserTypes();
		model.addAttribute("userTypes", userTypes);
		return "user/editOwn";

		}else {
			System.out.println("update前");
			String hashed = BCrypt.hashpw(login.getPass(), BCrypt.gensalt());
			login.setPass(hashed);
//			userDao.insert(login);

//			User previousUser = userDao.findByIdWithLogin(newUser.getId());
//			userDao.delete(previousUser.getLogin());
			newUser.setLogin(login);
			userDao.update(newUser);

			return "user/editOwnDone";
		}
		}
	}


