package yongfa365.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;
import yongfa365.entity.Person;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceTest {

    @Autowired
    PersonService service;

    @Test
    public void findAll() {
        log.info("清空缓存");
        service.deleteAllCache();

        log.info("以下调用findAll方法，首次慢要等数据加载，之后都是从缓存读，很快");
        log.info("findAll" + service.findAll());
        log.info("findAll" + service.findAll());
        log.info("findAll" + service.findAll());
    }

    @Test
    public void findPersonById() {
        log.info("清空缓存");
        service.deleteAllCache();

        log.info("findPersonById(1) 首次慢，有缓存后快");
        log.info("find 1" + service.findPersonById(1L));
        log.info("find 1" + service.findPersonById(1L));
        log.info("find 1" + service.findPersonById(1L));

        log.info("findPersonById(3) 首次慢，有缓存后快");
        log.info("find 3" + service.findPersonById(3L));
        log.info("find 3" + service.findPersonById(3L));
        log.info("find 3" + service.findPersonById(3L));
    }

    @Test
    public void update() {
        log.info("清空缓存");
        service.deleteAllCache();

        log.info("@CachePut 每次都会执行方法体更新缓存");
        log.info("update 3到缓存" + service.update(Person.builder().id(3L).name("我的名字是3").build()));
        log.info("findPersonById(3L) 就是从缓存拿了，快  " + service.findPersonById(3L));

    }

    @Test
    public void deleteOneCache() {
        log.info("清空缓存");
        service.deleteAllCache();

        log.info("find 3" + service.findPersonById(3L));
        log.info("find 3" + service.findPersonById(3L));
        log.info("find 3" + service.findPersonById(3L));

        log.info("@CacheEvict 特定缓存");
        service.deleteOneCache(3L);

        log.info("@CacheEvict 特定缓存后，第一次访问慢");
        log.info("find 3" + service.findPersonById(3L));
        log.info("find 3" + service.findPersonById(3L));
        log.info("find 3" + service.findPersonById(3L));
    }

    @Test
    public void deleteAllCache() {
        service.deleteAllCache();
    }
}