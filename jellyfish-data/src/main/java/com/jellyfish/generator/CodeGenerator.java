package com.jellyfish.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CodeGenerator {
    private String outputDir;
    private String outputXmlDir;
    private String[]  tableNames;
    private String   parent;
    private String   url;
    private String   username;
    private String  password;

    private String driver;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }

    public String getOutputXmlDir() {
        return outputXmlDir;
    }

    public void setOutputXmlDir(String outputXmlDir) {
        this.outputXmlDir = outputXmlDir;
    }



    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getTableNames() {
        return tableNames;
    }

    public void setTableNames(String[] tableNames) {
        this.tableNames = tableNames;
    }

    public  void execute() throws FileNotFoundException {
        Yaml yaml=new Yaml();
        URL url=CodeGenerator.class.getClassLoader().getResource("generator.yml");
        if(url!=null) {
            CodeGenerator generator = yaml.loadAs(new FileInputStream(url.getFile()),CodeGenerator.class);
            AutoGenerator autoGenerator = new AutoGenerator();
            // 全局配置
            GlobalConfig gc = new GlobalConfig();
            gc.setOutputDir(generator.getOutputDir());
            gc.setOpen(false);

            gc.setFileOverride(true);
            gc.setActiveRecord(false);
            gc.setEnableCache(false);
            gc.setBaseResultMap(true);
            gc.setBaseColumnList(true);
            gc.setAuthor("will");
            gc.setMapperName("%sMapper");
            gc.setXmlName("%sService");
            gc.setServiceImplName("%sServiceImpl");
            gc.setControllerName("%sController");
            autoGenerator.setGlobalConfig(gc);


            DataSourceConfig dsc = new DataSourceConfig();
            dsc.setDbType(DbType.MYSQL);
            dsc.setDriverName(generator.getDriver());
            dsc.setUrl(generator.getUrl());
            dsc.setUsername(generator.getUsername());
            dsc.setPassword(generator.getPassword());
            autoGenerator.setDataSource(dsc);

            PackageConfig pkc = new PackageConfig();
            pkc.setParent(generator.getParent());
            pkc.setEntity("entity");
            pkc.setMapper("mapper");
            pkc.setController("controller");
            pkc.setService("service");
            pkc.setServiceImpl("service.impl");
            autoGenerator.setPackageInfo(pkc);

            InjectionConfig cfg = new InjectionConfig() {
                @Override
                public void initMap() {
                    // to do nothing
                }
            };
            List<FileOutConfig> focList = new ArrayList<>();
            cfg.setFileOutConfigList(focList);
            focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
                @Override
                public String outputFile(TableInfo tableInfo) {
                    // 自定义输入文件名称
                    return generator.getOutputXmlDir()+"/mapper/"+ tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
                }
            });
            autoGenerator.setCfg(cfg);
            autoGenerator.setTemplate(new TemplateConfig().setXml(null));

            StrategyConfig strategy = new StrategyConfig();
            strategy.setRestControllerStyle(true);
            strategy.setEntityLombokModel(true);
            strategy.setEntitySerialVersionUID(false);
            strategy.setEntityLombokModel(true);
            strategy.setNaming(NamingStrategy.underline_to_camel);
            strategy.setColumnNaming(NamingStrategy.underline_to_camel);
            strategy.setEntityLombokModel(true);
            strategy.setInclude(generator.getTableNames());
            autoGenerator.setStrategy(strategy);
            autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());
            autoGenerator.execute();
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        CodeGenerator codeGenerator=new CodeGenerator();
        codeGenerator.execute();
    }
}
