package ru.springcourse.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.springcourse.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Person> index(){
       return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }
    public Person show(int id){
        return (Person) jdbcTemplate.query("SELECT * FROM person WHERE id = ?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void createPerson(Person person) {
        jdbcTemplate.update("INSERT INTO person (name, year) VALUES (?,?)", person.getName(), person.getYear());
    }

    public void updatePerson(int id, Person person) {
        jdbcTemplate.update("UPDATE person SET name=?, year=? WHERE id=?", person.getName(), person.getYear(), person.getId());
    }

    public void deletePerson(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }

    public Optional<Person> show(String name) {
        return jdbcTemplate.query("SELECT * FROM person WHERE name = ?", new BeanPropertyRowMapper<>(Person.class), name).stream().findAny();
    }
}
