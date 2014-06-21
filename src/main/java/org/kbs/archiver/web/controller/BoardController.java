package org.kbs.archiver.web.controller;

/**
 * Created by kcn on 14-6-17.
 */

import org.kbs.archiver.dao.BoardDAO;
import org.kbs.archiver.model.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("boardController")
public class BoardController {
  @Autowired
  private BoardDAO boardDAO;


  @RequestMapping("/a/listboard.do")
  public String getAll(Model model) {

    model.addAttribute("boards",boardDAO.selectAll());
    return "listboard";
  }
//  @Resource
//  private
}
