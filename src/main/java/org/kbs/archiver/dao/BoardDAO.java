package org.kbs.archiver.dao;

import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.kbs.archiver.model.Board;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.EnableCaching;

import java.util.List;

/**
 * Created by kcn on 14-6-17.
 */

@EnableCaching
public interface BoardDAO {
  @Lang(RawLanguageDriver.class)
  @Select("SELECT * FROM board order by boardid asc")
  @Cacheable("boardCache")
  public List<Board> selectAll();

  @Lang(RawLanguageDriver.class)
  @Select("SELECT * FROM board where ishidden=false order by upper(name) asc")
  @Cacheable("boardCache")
  public List<Board> selectAllVisible();

  @Lang(RawLanguageDriver.class)
  @Cacheable(value="boardCache",key="#p0")
  @Select("Select * From board Where name=#{name}")
  public Board selectByName(@Param("name")String name);

  @Cacheable(value="boardCache",key="#p0")
  @Select("Select * From board Where boardid=#{boardid}")
  public Board selectById(@Param("boardid")long boardid);

  @Caching(evict = { @CacheEvict(value="boardCache",key="#p0.boardid"),@CacheEvict(value="boardCache",key="#p0.name")})
  @Update("update board set name=#{name},cname=#{cname},threads=#{threads},articles=#{articles},ishidden=#{ishidden},groupid=#{groupid},section=#{section},ignored=#{ignored} where boardid=#{boardid}")
  public void update(Board board);

  @CacheEvict(value = "boardCache",allEntries = true)
  public void clearCache();
}
