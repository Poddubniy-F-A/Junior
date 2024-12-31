package HW4.model;

import javax.persistence.*;

@Entity
@Table(name = "Courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;
    @Column
    private int duration;

    public Course() {}

    public Course(String name, int duration) {
        this.title = name;
        this.duration = duration;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + title + '\'' +
                ", duration=" + duration +
                '}';
    }
}
