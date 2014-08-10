package org.kbs.archiver.dao;

import com.mongodb.WriteResult;
import org.kbs.archiver.model.Board;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kcn on 14-6-17.
 */

@Repository
@NoRepositoryBean
public interface BoardDAO {
    //    @Lang(RawLanguageDriver.class)
//    @Select("SELECT * FROM board where ishidden=false order by boardid asc")
    public java.lang.Iterable<Board> findAll();

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
    public Board findById(String boardid);

    //
//    @Lang(RawLanguageDriver.class)
//    @Update("update board set name=#{name},cname=#{cname},threads=#{threads},articles=#{articles},ishidden=#{ishidden},groupid=#{groupid},section=#{section},ignored=#{ignored} where boardid=#{boardid}")
    public void save(Board board);

    public int count();
}
