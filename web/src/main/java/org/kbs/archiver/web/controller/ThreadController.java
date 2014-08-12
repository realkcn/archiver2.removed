package org.kbs.archiver.web.controller;

import org.kbs.archiver.cache.BoardCache;
import org.kbs.archiver.repositories.ThreadRepository;
import org.kbs.archiver.repositories.ThreadRepositoryCustom;
import org.kbs.archiver.service.ThreadService;
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
    private ThreadService threadService;

    @Autowired
    private BoardCache boardCache;

    /**
     * Get list of boards.
     *
     * @param boardid board id
     * @param pageno  page number
     * @param model   unuse
     * @return "listboard" for result
     */
    @RequestMapping(value = "/listthread.do", method = RequestMethod.GET)
    public String getByBoard(@RequestParam("boardid") String boardid,
                             @RequestParam(value = "pageno", required = false) long pageno,
                             Model model) {
        return "listthread";
    }

}
