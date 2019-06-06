package com.momole.codegen;

import cn.hutool.core.io.resource.ClassPathResource;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * <p>
 * 代码生成器
 * <p>
 *
 * @author ouyangzhiming
 * @since 2018-08-09
 */
public class CodeGenApplication {

	public static void main(String[] args) {
		generatorCode();
	}

	/**
	 * 生成代码
	 */
	private static void generatorCode() {
		Properties config = getConfig(); //配置信息
		AutoGenerator mpg =  new AutoGenerator();

		mpg.setTemplateEngine(new FreemarkerTemplateEngine()); // 选择 freemarker 引擎，默认 Veloctiy
		mpg.setTemplate(new TemplateConfig().setXml(null)); // 关闭默认 xml 生成，调整生成 至 根目录

		mpg.setGlobalConfig(buildGlobalConfig(config)); // 1. 全局配置
		mpg.setDataSource(buildDataSource(config)); //  2. 数据源配置
		mpg.setStrategy(buildStrategy(config)); //  3. 策略配置
		mpg.setPackageInfo(buildPackage(config)); // 4. 包名策略配置
		mpg.setCfg(buildInjectionConfig(config)); // 5.注入自定义配置
		mpg.execute(); // 6. 执行生成
	}

	/**
	 * 获取配置信息
	 */
	private static Properties getConfig() {
		ClassPathResource resource = new ClassPathResource("generator.properties");
		Properties properties = new Properties();
		try {
			properties.load(resource.getStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

	/**
	 * 全局配置
	 *
	 * @param properties
	 */
	private static GlobalConfig buildGlobalConfig(Properties properties) {
		return new GlobalConfig()
				.setIdType(getIdType(properties)) // 主键类别
				.setDateType(getDateType(properties)) //日期类别
				.setSwagger2(false) //生成Swagger信息
				// .setActiveRecord(true)// 开启 activeRecord 模式
				.setOutputDir(properties.getProperty("baseProjectPath") + "/src/main/java")//输出目录
				.setFileOverride(true)// 是否覆盖文件
				.setEnableCache(false)// XML 二级缓存
				.setBaseResultMap(true)// XML ResultMap
				.setBaseColumnList(true)// XML columList
				.setOpen(false)//生成后打开文件夹
				.setAuthor(properties.getProperty("author"))
				.setMapperName("%sMapper")
				.setXmlName("%sMapper")
				.setServiceName("%sService")
				.setServiceImplName("%sServiceImpl")
				.setFileOverride(false)
				.setControllerName("%sController");

	}

	/**
	 * 数据源配置
	 *
	 * @param properties
	 */
	private static DataSourceConfig buildDataSource(Properties properties) {
		return new DataSourceConfig()
				.setDbType(DbType.MYSQL)
				.setDriverName(properties.getProperty("jdbc.driverClassName"))
				.setUrl(properties.getProperty("jdbc.url"))
				.setUsername(properties.getProperty("jdbc.username"))
				.setPassword(properties.getProperty("jdbc.password"));
	}

	/**
	 * 策略配置
	 *
	 * @param properties
	 */
	private static StrategyConfig buildStrategy(Properties properties) {

		// 自定义需要填充的字段
		List<TableFill> tableFillList = new ArrayList<>();
		tableFillList.add(new TableFill("gmt_create", FieldFill.INSERT));
		tableFillList.add(new TableFill("gmt_modified", FieldFill.INSERT_UPDATE));

		return new StrategyConfig()
				.setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
				.setColumnNaming(NamingStrategy.underline_to_camel)
				.setCapitalMode(true) // 是否大写命名
				.setEntityLombokModel(true) //是否生成Lombok风格
				.setEntityColumnConstant(true)  // 【实体】是否生成字段常量（默认 false）
				.entityTableFieldAnnotationEnable(true) // 是否生成实体时，生成字段注解
				.setLogicDeleteFieldName("is_deleted") // 逻辑删除属性
				.setVersionFieldName("version") //乐观锁属性
				.setEntityBooleanColumnRemoveIsPrefix(true)  //实体】Boolean类型的属性是否去除is前缀
				.setInclude(properties.getProperty("tables").split(",")) // 需要生成的表
				.setTablePrefix(properties.getProperty("tablePrefix").split(","))// 此处可以修改为您的表前缀
				.setRestControllerStyle(true) //是否生成RestController风格
				.setControllerMappingHyphenStyle(true) // 驼峰转连字符
				// .setSuperEntityClass("com.momole.chat.extension.SuperEntity") // 自定义实体父类
				// .setSuperEntityColumns("gmt_create", "gmt_modified") // 继承自父类的字段
				.setSuperMapperClass("com.momole.chat.extension.SuperMapper") // 自定义 mapper 父类
				.setSuperServiceClass("com.momole.chat.extension.SuperService") // 自定义 service 父类
				.setSuperServiceImplClass("com.momole.chat.extension.SuperServiceImpl") // 自定义 serviceImpl 父类
				// .setSuperControllerClass("com.momole.common.web.extension.SuperController") //自定义 controller 父类
				.setTableFillList(tableFillList); //表填充字段
	}

	/**
	 * 包名策略配置
	 *
	 * @param properties
	 */
	private static PackageConfig buildPackage(Properties properties) {
		return new PackageConfig()
				.setParent(properties.getProperty("basePackage"))// 自定义包路径
				.setController("controller")// 这里是控制器包名，默认 controller
				.setEntity("entity")
				.setMapper("mapper")
				.setService("service")
				.setServiceImpl("service.impl")
				.setXml("mapper");
	}

	/**
	 * 自定义配置
	 *
	 * @param properties
	 */
	private static InjectionConfig buildInjectionConfig(Properties properties) {

		//  注入自定义配置,可以在 VM 中使用 cfg.abc 设置的值
		InjectionConfig injectionConfig = new InjectionConfig() {
			@Override
			public void initMap() {
				// to do nothing
			}
		};

		//自定义文件输出位置
		List<FileOutConfig> focList = new ArrayList<>();
		focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
			@Override
			public String outputFile(TableInfo tableInfo) {
				// 自定义输入文件名称
				return properties.getProperty("baseProjectPath") + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper.xml";
			}
		});
		injectionConfig.setFileOutConfigList(focList);

		return injectionConfig;
	}

	/**
	 * 获取数据表主键类型
	 *
	 * @param properties
	 */
	private static IdType getIdType(Properties properties) {
		IdType idType;
		switch (properties.getProperty("idType").toLowerCase()) {
			case "uuid":
				idType = IdType.UUID;
				break;
			case "input":
				idType = IdType.INPUT;
				break;
			case "id_worker":
				idType = IdType.ID_WORKER;
				break;
			case "id_worker_str":
				idType = IdType.ID_WORKER_STR;
				break;
			case "none":
				idType = IdType.NONE;
				break;
			default:
				idType = IdType.AUTO;
				break;
		}
		return idType;
	}

	/**
	 * 获取日期类别
	 *
	 * @param properties
	 */
	private static DateType getDateType(Properties properties) {
		DateType dateType;
		switch (properties.getProperty("dateType").toLowerCase()) {
			case "sql_pack":
				dateType = DateType.SQL_PACK;
				break;
			case "time_pack":
				dateType = DateType.TIME_PACK;
				break;
			default:
				dateType = DateType.ONLY_DATE;
				break;
		}
		return dateType;
	}
}
