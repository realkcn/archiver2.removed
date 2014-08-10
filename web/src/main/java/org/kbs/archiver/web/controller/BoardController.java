package org.kbs.archiver.web.controller;

/**
 * Created by kcn on 14-6-17.
 */

import org.kbs.archiver.cache.BoardCache;
import org.kbs.archiver.dao.BoardDAO;
import org.kbs.archiver.model.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("boardController")
public class BoardController {
    @Autowired
    private BoardDAO boardDAO;

    /**
     * Get list of boards.
     *
     * @param model unuse
     * @return "listboard" for result
     */
    @RequestMapping("/listboard.do")
    public String getAll(Model model) {
        model.addAttribute("boards", boardDAO.findAll());
        return "listboard";
    }

    /**
     * Reload Board cache.
     *
     * @param model unuse
     * @return
     */
    @RequestMapping("/admin/reloadboardcache.do")
    @ResponseBody
    public String reloadCache(Model model) {
        if (boardDAO instanceof BoardCache) {
            ((BoardCache) boardDAO).expireAll();
            return "重新读入版面缓存";
        }
        return "未启用版面缓存";
    }
}
