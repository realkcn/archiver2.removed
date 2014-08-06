package org.kbs.archiver.dao;

import org.kbs.archiver.model.Board;

import java.util.List;

/**
 * Created by kcn on 14-6-17.
 */

public interface BoardDAO {
    //    @Lang(RawLanguageDriver.class)
//    @Select("SELECT * FROM board where ishidden=false order by boardid asc")
    public List<Board> selectAll();

    //
//    @Lang(RawLanguageDriver.class)
//    @Select("SELECT * FROM board where ishidden=false order by upper(name) asc")
    public List<Board> selectAllVisible();

    //
//    @Lang(RawLanguageDriver.class)
//    @Select("Select * From board Where name=#{name} and ishidden=false")
    public Board selectByName(String name);

    //
//    @Lang(RawLanguageDriver.class)
//    @Select("Select * From board Where boardid=#{boardid} and ishidden=false")
    public Board selectById(long boardid);

    //
//    @Lang(RawLanguageDriver.class)
//    @Update("update board set name=#{name},cname=#{cname},threads=#{threads},articles=#{articles},ishidden=#{ishidden},groupid=#{groupid},section=#{section},ignored=#{ignored} where boardid=#{boardid}")
    public void update(Board board);
}
