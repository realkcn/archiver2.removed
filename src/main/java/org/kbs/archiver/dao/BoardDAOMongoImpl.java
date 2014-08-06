package org.kbs.archiver.dao;/**
 * User: kcn
 * Date: 14-8-6
 * Time: 下午5:26
 */

import org.kbs.archiver.model.Board;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BoardDAOMongoImpl implements BoardDAO {
    private static final Logger LOG = LoggerFactory.getLogger(BoardDAOMongoImpl.class);

    @Override
    public List<Board> selectAll() {
        return null;  //TODO
    }

    @Override
    public List<Board> selectAllVisible() {
        return null;  //TODO
    }

    @Override
    public Board selectByName(String name) {
        return null;  //TODO
    }

    @Override
    public Board selectById(long boardid) {
        return null;  //TODO
    }

    @Override
    public void update(Board board) {
        //TODO
    }
}
