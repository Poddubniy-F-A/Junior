package HW4;

import HW4.queryHandlers.CoursesQueriesHandler;
import HW4.model.Course;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        //предполагается, что на той стороне порта 3306 доступна база данных mysql SchoolDB по связке root + password
        try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/SchoolDB",
                "root",
                "password")
        ) {
            connection.createStatement().execute("create table if not exists Courses (" +
                    "id integer primary key, " +
                    "title varchar(63) not null, " +
                    "duration integer not null" +
                    ");");
        } catch (SQLException e) {
            System.err.println("Check connection parameters");
            throw new RuntimeException(e);
        }

        ArrayList<Course> courses = new ArrayList<>(Arrays.asList(
                new Course("french", 45),
                new Course("PE", 90),
                new Course("math", 180)
        ));

        try (SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Course.class)
                .buildSessionFactory()) {
            try (Session session = sessionFactory.openSession()) {
                CoursesQueriesHandler queriesHandler = new CoursesQueriesHandler();

                for (Course course : courses) {
                    queriesHandler.insert(session, course);
                }

                Course updatingCourse = courses.getFirst();
                updatingCourse.setDuration(90);
                queriesHandler.update(session, updatingCourse);

                for (Course course : courses) {
                    Course receivedCourse = queriesHandler.get(session, course.getId());
                    System.out.println(receivedCourse);
                    queriesHandler.delete(session, receivedCourse);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            System.err.printf("Check hibernate configuration file and if %s is annotated correctly\n",
                    Course.class.getName());
            throw new RuntimeException(e);
        }
    }
}
