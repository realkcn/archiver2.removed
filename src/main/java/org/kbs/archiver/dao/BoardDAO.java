package org.kbs.archiver.dao;

import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.kbs.archiver.model.Board;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;

import java.util.List;

/**
 * Created by kcn on 14-6-17.
 */

@EnableCaching
public interface BoardDAO {
  @Lang(RawLanguageDriver.class)
  @Select("SELECT * FROM Board")
  @Cacheable("boardCache")
  public List<Board> selectAll();

  @Cacheable(value="boardCache",key="#p0")
  @Select("Select * From board Where name=#{name}")
  public Board select(@Param("name")String name);

  @Cacheable(value="boardCache",key="#p0")
  @Select("Select * From board Where boardid=#{boardid}")
  public Board selectById(@Param("boardid")long boardid);
}
