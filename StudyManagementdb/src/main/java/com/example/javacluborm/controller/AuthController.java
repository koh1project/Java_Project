package com.example.javacluborm.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.javacluborm.dao.UserDao;
import com.example.javacluborm.domain.User;

@Controller
public class AuthController {

	@Autowired
	private UserDao userDao;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginGet(Model model) {
		User user = new User();
		model.addAttribute("user", user);

		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginPost(User loginUser, HttpSession session, Model model) throws Exception {

		Boolean nameIdError = false;
		Boolean passError = false;
		if (loginUser.getLoginNameId().isEmpty()) {
			model.addAttribute("nameIdError", "※ログインIDを入力してください");
			nameIdError = true;
		}
		if (loginUser.getLogin().getPass().isEmpty()) {
			model.addAttribute("passError", "※パスワードを入力してください");
			passError = true;
		}


//		未入力
		if (nameIdError || passError) {
			return "login";
		}

		loginUser = userDao.findByLoginIdAndLoginPass(loginUser.getLoginNameId(),
		   loginUser.getLogin().getPass());
		if (loginUser != null) {
			// １意のユーザ情報IDを入れる  userオブジェクトだけで事足りるかも
			session.setAttribute("loginUserId", loginUser.getId());
			session.setAttribute("loginNameId", loginUser.getLoginNameId());
			session.setAttribute("loginUserName", loginUser.getName());
			session.setAttribute("loginUserTypeId", loginUser.getUserType().getId());
			session.setAttribute("loginUser", loginUser);
			return "redirect:/listWordsToStudyToday";
		}
		model.addAttribute("errorEither" , "※ログインIDかパスワードが間違っています。" );
			return "login";

	}

	@RequestMapping(value = "/logout")
	public String getLogut(HttpSession session) {
		session.invalidate();
		return "logoutDone";

	}

}
