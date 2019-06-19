package yongfa365.service;


import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import yongfa365.entity.Person;

import java.util.List;

@Service
public class PersonService {
    /**
     * 从缓存拿，缓存没有则调用方法主体拿并缓存结果（缓存穿透）
     */
    @Cacheable(cacheNames = "PERSON", sync = true)
    public List<Person> findAll() {
        return Person.getAllPersons();
    }

    /**
     * 从缓存拿，缓存没有则调用方法主体拿并缓存结果（缓存穿透），指定key表达式
     */
    @Cacheable(cacheNames = "PERSON", key = "'PERSON'.concat(#id.toString())", sync = true)
    public Person findPersonById(Long id) {
        return Person.getAllPersons().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * 方法总是被执行，它的结果被放入缓存
     */
    @CachePut(cacheNames = "PERSON", key = "'PERSON'.concat(#person.id.toString())")
    public Person update(Person person) {
        var persons = Person.getAllPersons();
        persons.stream()
                .filter(p -> p.getId().equals(person.getId()))
                .findFirst()
                .ifPresent(persons::remove);

        persons.add(person);
        return person;
    }

    /**
     * 删除指定cacheNames里的指定key的缓存
     */
    @CacheEvict(cacheNames = "PERSON", key = "'PERSON'.concat(#id.toString())")
    public void deleteOneCache(Long id) {
        //没有方法体因为只是删除缓存
    }


    /**
     * 删除指定cacheNames里的所有缓存
     */
    @CacheEvict(cacheNames = "PERSON", allEntries = true)
    public void deleteAllCache() {
        //没有方法体因为只是删除缓存
    }
}
