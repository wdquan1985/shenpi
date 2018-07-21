package shenpi.shenpi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import shenpi.shenpi.model.ShenPi;

@Mapper
public interface ShenPiMyBatisRepository {
	@Select("select * from shenpi")
	public List<ShenPi> findAll();

	@Select("SELECT * FROM shenpi WHERE id = #{id}")
	public ShenPi findById(long id);

	@Delete("DELETE FROM shenpi WHERE id = #{id}")
	public int deleteById(long id);

	@Insert("INSERT INTO shenpi(name, product, address, phoneNumber, productType) VALUES (#{name}, #{product}, #{address}, #{phoneNumber}, #{productType})")
	public int insert(ShenPi shenpi);

	@Update("Update shenpi set name=#{name}, product=#{product} where id=#{id}")
	public int update(ShenPi shenpi);

}
