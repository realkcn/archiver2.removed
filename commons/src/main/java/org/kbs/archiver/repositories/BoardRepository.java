package org.kbs.archiver.repositories;

import org.kbs.archiver.model.Board;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kcn on 14-8-10.
 */

@Repository
public interface BoardRepository extends CrudRepository<Board, String> {
    //    @Lang(RawLanguageDriver.class)
//    @Select("SELECT * FROM board where ishidden=false order by boardid asc")
    public List<Board> findAll();

    //
//    @Lang(RawLanguageDriver.class)
//    @Select("SELECT * FROM board where ishidden=false order by upper(name) asc")
//    public List<Board> findAllVisible();

    //
//    @Lang(RawLanguageDriver.class)
//    @Select("Select * From board Where name=#{name} and ishidden=false")
    public Board findByName(String name);

    //
//    @Lang(RawLanguageDriver.class)
//    @Select("Select * From board Where boardid=#{boardid} and ishidden=false")
    public Board findByBoardid(String boardid);

    //
//    @Lang(RawLanguageDriver.class)
//    @Update("update board set name=#{name},cname=#{cname},threads=#{threads},articles=#{articles},ishidden=#{ishidden},groupid=#{groupid},section=#{section},ignored=#{ignored} where boardid=#{boardid}")
    public Board save(Board board);

//    int countVisible();

//    public List<Board> findAllVisible();
}
