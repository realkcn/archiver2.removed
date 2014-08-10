package org.kbs.archiver.dao;

import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;

import java.util.List;

/**
 * Created by kcn on 14-6-17.
 */
@SuppressWarnings("UnusedDeclaration")
public interface ThreadDAO {
    public Thread get(String threadid);
//    public Thread getByOriginId(@Param("boardid")long boardid,@Param("originid")long originid);
//    public Thread getByEncodingUrl(String encodingurl);
//
//    public void insert(Thread thread);
//    public int update(Thread thread);
//    public void resetOriginidByBoard(long boardid);
//    public void deleteByBoard(long boardid);
//

//    @Lang(RawLanguageDriver.class)
//    @Select("SELECT * FROM thread where boardid=#{boardid} and isvisible=true ORDER BY lastposttime desc limit #{offset},#{limit}")
//    List<Thread> getByBoardPerPage(@Param("boardid")long boardid,@Param("offset") int offset, @Param("limit") int limit);
//    public void delete(long threadid);
//
//    @Lang(RawLanguageDriver.class)
//    @Select("select * from thread where encodingurl=#{encodingurl} and isvisible=true")
//    public Thread getByEncodingURL(@Param("encodingurl")String encodingurl);
}
