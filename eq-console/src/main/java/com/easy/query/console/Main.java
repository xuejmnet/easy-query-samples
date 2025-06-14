package com.easy.query.console;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.console.dto.CompanyDTO;
import com.easy.query.console.dto.GroupVO;
import com.easy.query.console.dto.SysUserDTO;
import com.easy.query.console.dto.proxy.GroupVOProxy;
import com.easy.query.console.entity.Company;
import com.easy.query.console.entity.SysUser;
import com.easy.query.console.entity.ValidUser;
import com.easy.query.console.vo.CompanyNameAndUserNameVO;
import com.easy.query.console.vo.proxy.CompanyNameAndUserNameVOProxy;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.mysql.config.MySQLDatabaseConfiguration;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Main {
    private static EasyEntityQuery entityQuery;

    public static void main(String[] args) {
        LogFactory.useStdOutLogging();
        DataSource dataSource = getDataSource();
        EasyQueryClient client = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .optionConfigure(op -> {
                    //进行一系列可以选择的配置
                    //op.setPrintSql(true);
                })
                .useDatabaseConfigure(new MySQLDatabaseConfiguration())
                .build();
        entityQuery = new DefaultEasyEntityQuery(client);
        entityQuery.setMigrationParser(new MyMyMigrationEntityParser());
        DatabaseCodeFirst databaseCodeFirst = entityQuery.getDatabaseCodeFirst();
        //如果不存在数据库则创建
        databaseCodeFirst.createDatabaseIfNotExists();
        //自动同步数据库表
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(Company.class, SysUser.class, ValidUser.class));
        //执行命令
        codeFirstCommand.executeWithTransaction(arg -> {
//            System.out.println(arg.sql);
            arg.commit();
        });
//        init(entityQuery);

        List<SysUser> users = entityQuery.queryable(SysUser.class)
                .where(s -> {
                    s.company().name().contains("xx有限公司");
                }).toList();



        String sql = entityQuery.queryable(SysUser.class)
                .where(s -> {
                    s.or(() -> {
                        s.name().like("123");
                        s.name().like("456");
                    });
                }).toSQL();
        System.out.println(sql);

        List<SysUser> list2 = entityQuery.queryable(SysUser.class)
                .where(s -> {
                    s.or(() -> {
                        s.name().like("123");
                        s.name().like("456");
                    });
                }).toList();


        List<SysUserDTO> list = entityQuery.queryable(SysUser.class)
                .where(s -> {
                    s.name().like("123");
                }).selectAutoInclude(SysUserDTO.class)
                .toList();


        CompanyDTO companyDTO = entityQuery.queryable(Company.class)
                .where(c -> c.name().like("公司1"))
                .selectAutoInclude(CompanyDTO.class)
                .firstNotNull();
        System.out.println(companyDTO);

        List<GroupVO> list1 = entityQuery.queryable(SysUser.class)
                .where(s -> {
                    s.name().like("123");
                })
                .groupBy(s -> GroupKeys.of(s.name()))
                .select(group -> new GroupVOProxy()
                        .userName().set(group.key1())
                        .before2020Count().set(
                                group.expression().caseWhen(() -> group.groupTable().birthday().le(LocalDateTime.of(2020, 1, 1, 0, 0, 0)))
                                        .then(1)
                                        .elseEnd(0).sum()
                        )
                        .userCount().set(group.count())
                ).toList();


//        test1();
//        test2();
//        test3(();
    }

    private static void init(EasyEntityQuery entityQuery) {
        boolean any = entityQuery.queryable(Company.class).any();
        if (!any) {
            ArrayList<Company> companies = new ArrayList<>();
            ArrayList<SysUser> sysUsers = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Company company = new Company();
                company.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                company.setName("公司" + i);
                company.setCreateTime(LocalDateTime.now());
                company.setRegisterMoney(new BigDecimal(i));
                companies.add(company);
                for (int j = 0; j < 10; j++) {
                    SysUser sysUser = new SysUser();
                    sysUser.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                    sysUser.setName("公司" + i + "姓名" + j);
                    sysUser.setBirthday(LocalDateTime.now());
                    sysUser.setCompanyId(company.getId());
                    sysUsers.add(sysUser);
                }
            }
            try (Transaction transaction = entityQuery.beginTransaction()) {
                entityQuery.insertable(companies).executeRows();
                entityQuery.insertable(sysUsers).executeRows();
                transaction.commit();
            }
        }
    }

    /**
     * 初始化数据源
     *
     * @return
     */
    private static DataSource getDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/eq_db?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setMaximumPoolSize(20);

        return dataSource;
    }


    public static void test1() {
        {
            Company company = entityQuery.queryable(Company.class).firstNotNull();
            System.out.println(company);
        }
        {
            Company company = entityQuery.queryable(Company.class).firstOrNull();
            System.out.println(company);
        }
        {
            Company company = entityQuery.queryable(Company.class).singleNotNull();
            System.out.println(company);
        }
        {
            Company company = entityQuery.queryable(Company.class).singleOrNull();
            System.out.println(company);
        }
        {
            List<Company> companies = entityQuery.queryable(Company.class).toList();
            System.out.println(companies);
        }
        {
            List<Company> companies = entityQuery.queryable(Company.class).select(c -> c.FETCHER.name().createTime()).toList();
            System.out.println(companies);
        }

        {
            List<Company> companies = entityQuery.queryable(Company.class)
                    .where(c -> {
                        c.id().eq("1");
                    }).toList();
            System.out.println(companies);
        }
        {
//多个条件and组合
            List<Company> companies = entityQuery.queryable(Company.class)
                    .where(c -> {
                        c.id().eq("1");
                        c.name().like("公司");
                    }).toList();
            System.out.println(companies);
        }
        {
//多个条件or组合 具体请看or章节
            List<Company> companies = entityQuery.queryable(Company.class)
                    .where(c -> {
                        c.or(() -> {
                            c.id().eq("1");
                            c.name().like("公司");
                        });
                    }).toList();
            System.out.println(companies);
        }

        {
            //限制返回条数
            List<Company> companies = entityQuery.queryable(Company.class)
                    .where(c -> {
                        c.id().eq("1");
                        c.name().like("公司");
                    }).limit(10).toList();
            System.out.println(companies);
        }
        {
            //跳过10条然后返回20条数
            List<Company> companies = entityQuery.queryable(Company.class)
                    .where(c -> {
                        c.id().eq("1");
                        c.name().like("公司");
                    }).limit(10, 20).toList();
            System.out.println(companies);
        }

        {

//查询分页
            EasyPageResult<Company> Company = entityQuery.queryable(Company.class).where(c -> {
                c.id().eq("1");
                c.name().like("公司");
            }).toPageResult(1, 20);
        }
    }


    public static void test2() {
        {

            List<Company> list = entityQuery.queryable(Company.class)
                    .leftJoin(SysUser.class, (c, u) -> c.id().eq(u.companyId()))
                    .where((c, u) -> {
                        c.id().eq("1");
                        u.name().like("小明");
                    }).toList();
        }
        {

            List<Draft2<String, String>> list = entityQuery.queryable(Company.class)
                    .leftJoin(SysUser.class, (c, u) -> c.id().eq(u.companyId()))
                    .where((c, u) -> {
                        c.id().eq("1");
                        u.name().like("小明");
                    }).select((c, u) -> Select.DRAFT.of(
                            c.name(),
                            u.name()
                    )).toList();
            for (Draft2<String, String> draft2 : list) {
                String companyName = draft2.getValue1();
                String userName = draft2.getValue2();
            }
        }

        {

            List<CompanyNameAndUserNameVO> xm = entityQuery.queryable(Company.class)
                    .leftJoin(SysUser.class, (c, u) -> c.id().eq(u.companyId()))
                    .where((c, u) -> {
                        c.id().eq("1");
                        u.name().like("小明");
                    }).select(CompanyNameAndUserNameVO.class, (c1, s2) -> Select.of(
                            s2.FETCHER.allFields(),
                            s2.name().as(CompanyNameAndUserNameVO.Fields.userName),
                            c1.name().as(CompanyNameAndUserNameVO.Fields.companyName)
                    )).toList();
        }
        {

            List<CompanyNameAndUserNameVO> xm = entityQuery.queryable(Company.class)
                    .leftJoin(SysUser.class, (c, u) -> c.id().eq(u.companyId()))
                    .where((c, u) -> {
                        c.id().eq("1");
                        u.name().like("小明");
                    }).select((c1, s2) -> new CompanyNameAndUserNameVOProxy()
                            .companyName().set(c1.name()) // 企业名称
                            .userName().set(s2.name()) // 用户姓名
                            .birthday().set(s2.birthday()) // 用户出生日期
                            .companyId().set(s2.companyId()) // 用户所属企业id
                    ).toList();
        }
    }

    public void test3() {

        List<SysUser> userInHz = entityQuery.queryable(SysUser.class)
                .where(u -> {
                    //隐式子查询会自动join用户表
                    //根据条件是否生效自动添加企业表的join
                    u.company().name().eq("杭州公司");
                }).toList();


//筛选企业条件是企业内有至少一个用户是小明
        List<Company> companyHasXiaoMing = entityQuery.queryable(Company.class)
                .where(c -> {
                    //筛选条件为企业所属用户里面有一个叫做小明的
                    c.users().any(user -> {
                        user.name().eq("小明");
                    });
                }).toList();
    }

}