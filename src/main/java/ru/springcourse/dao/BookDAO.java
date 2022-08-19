package ru.springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.springcourse.models.Book;
import ru.springcourse.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;
    private final PersonDAO personDAO;
    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate, PersonDAO personDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.personDAO = personDAO;
    }
    public List<Book> listBooks(){
        System.out.println("Start list book");
        return jdbcTemplate.query("SELECT * FROM book", new BookRowMapper());
    }

    public Book show(int id) {
         return (Book) jdbcTemplate.query("SELECT * FROM book WHERE id = ?", new Object[]{id}, new BookRowMapper())
                 .stream().findAny().orElse(null);
    }

    public void add(Book book) {
        jdbcTemplate.update("INSERT INTO book(title, author, year) VALUES(?,?,?)",book.getTitle(),
                book.getAuthor(), book.getYear());
    }
    public void edit(int id, Book book){
        jdbcTemplate.update("UPDATE book SET title=?, author=?, year=? WHERE id=?",book.getTitle(),
                book.getAuthor(), book.getYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id=?", id);
    }
//    public Person getAssignedToPerson(int bookId){
//        int personId = show(bookId).getAssignedto();
//        if (personId != 0) {
//            Person person = personDAO.show(personId);
//            System.out.println("Book with id " + bookId + " is assigned to " + person.getId() + " " + person.getName());
//            return person;
//        }
//        return new Person();
//    }
    public Person getAssignedToPerson(int id){
        return jdbcTemplate.query("SELECT person.* FROM book JOIN person ON book.assignedto=person.id WHERE book.id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(new Person());
    }

    public List<Book> getBooksByPersonId(int personId){
        return jdbcTemplate.query("SELECT * FROM book WHERE assignedTo = ?", new Object[]{personId}, new BookRowMapper());
    }

    public void assignBook(int bookId, int personId) {
        jdbcTemplate.update("UPDATE book SET assignedto=? WHERE id=?", personId, bookId);
    }

    public void releaseBook(int id) {
        jdbcTemplate.update("UPDATE book SET assignedto=null WHERE id=?", id);
    }
}
