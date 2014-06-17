package org.kbs.archiver.dao;

import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.scripting.defaults.RawLanguageDriver;
import org.kbs.archiver.model.Board;

import java.util.List;

/**
 * Created by kcn on 14-6-17.
 */
public interface BoardDAO {
  @Lang(RawLanguageDriver.class)
  @Select("SELECT * FROM Board")
  List<Board> selectAll();

  @Select("Select * From board Where boardid=#{boardid}")
  Board select(@Param("boardid")String id);
}
