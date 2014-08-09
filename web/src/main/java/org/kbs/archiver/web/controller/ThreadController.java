package org.kbs.archiver.web.controller;

import org.kbs.archiver.dao.BoardDAO;
import org.kbs.archiver.dao.ThreadDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by kcn on 14-6-17.
 */

@Controller("threadController")
public class ThreadController {

    @Autowired
    private ThreadDAO threadDAO;

    @Autowired
    private BoardDAO boardDAO;

    /**
     * Get list of boards.
     * @param boardid board id
     * @param pageno page number
     * @param model unuse
     * @return "listboard" for result
     */
    @RequestMapping(value="/listthread.do",method = RequestMethod.GET)
    public String getByBoard(@RequestParam("boardid")long boardid,
            @RequestParam(value="pageno",required = false)long pageno,
            Model model) {
        return "listthread";
    }

}
